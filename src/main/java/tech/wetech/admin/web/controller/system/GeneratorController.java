package tech.wetech.admin.web.controller.system;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tech.wetech.admin.common.utils.Constants;
import tech.wetech.admin.common.utils.JsonUtil;
import tech.wetech.admin.common.utils.ZipUtils;
import tech.wetech.admin.generator.bridge.MybatisGeneratorBridge;
import tech.wetech.admin.generator.model.GeneratorConfig;
import tech.wetech.admin.generator.util.JdbcConfigHelper;
import tech.wetech.admin.model.system.User;
import tech.wetech.admin.web.controller.base.BaseController;
import tech.wetech.admin.web.dto.system.GeneratorDto;

/**
 * 代码生成器控制层
 * <p>
 * Created by cjbi on 2018/1/8.
 */
@Controller
@RequestMapping("/generator")
public class GeneratorController extends BaseController{

    @Autowired
    private MybatisGeneratorBridge mybatisGeneratorBridge;

    @RequestMapping(method = RequestMethod.GET)
    public String toPage(Model model) throws SQLException {
        model.addAttribute("tableNames", JdbcConfigHelper.getTableNames());
        return "system/generator";
    }

    private String base64DecodeToString(String text) throws UnsupportedEncodingException {
        byte[] textByte = text.getBytes("UTF-8");
        Base64.Decoder decoder = Base64.getDecoder();
        decoder.decode(textByte);
        return new String(decoder.decode(textByte), "UTF-8");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/code.zip")
    public ResponseEntity<byte[]> downloadCode(HttpServletRequest request, HttpServletResponse response, String p) {
        ResponseEntity<byte[]> responseEntity = null;
        String tmpPath = request.getSession().getServletContext().getRealPath("/WEB-INF/tmp");
        User user = (User) request.getAttribute(Constants.CURRENT_USER);
        String srcPath = tmpPath + File.separator + user.getUsername() + File.separator + "code";
        String destPath = tmpPath + File.separator + user.getUsername() + File.separator + "code" + ZipUtils.EXT;
        try {
            if (StringUtils.isEmpty(p)) {
                throw new RuntimeException("参数p不能为空");
            }
            String json = base64DecodeToString(p);
            GeneratorDto dto = (GeneratorDto) JsonUtil.getInstance().json2obj(json, GeneratorDto.class);
            dto.setProjectFolder(srcPath);
            mybatisGeneratorBridge.setGeneratorConfig(getConfig(dto));
            mybatisGeneratorBridge.generate();
            ZipUtils.compress(srcPath, destPath);
            File file = new File(destPath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", file.getName());
            responseEntity = new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            try {
                String errMsg = String.format("生成代码失败，错误原因：%s", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                OutputStream out = response.getOutputStream();
                logger.error(errMsg);
                out.write(errMsg.getBytes("UTF-8"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            File file = new File(tmpPath + File.separator + user.getUsername());
            if (file.exists()) {
                FileUtils.deleteQuietly(file);
            }
        }
        return responseEntity;
    }

    private GeneratorConfig getConfig(GeneratorDto generatorDto) throws RuntimeException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        GeneratorConfig generatorConfig = new GeneratorConfig();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GeneratorDto>> violations = validator.validate(generatorDto);
        if (!CollectionUtils.isEmpty(violations)) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> {
                String property = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                builder.append(property);
                builder.append(message);
                builder.append(",");
            });
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            logger.error(String.format("参数校验失败:%s"),builder.toString());
            throw new RuntimeException(builder.toString());
        }
        BeanUtils.copyProperties(generatorDto, generatorConfig);
        if (!checkDirs(generatorConfig)) {
            return null;
        }
        return generatorConfig;
    }

    /**
     * 检查并创建不存在的文件夹
     *
     * @return
     */
    private boolean checkDirs(GeneratorConfig config) {
        List<String> dirs = new ArrayList<>();
        dirs.add(config.getProjectFolder());
        dirs.add(FilenameUtils
                .normalize(config.getProjectFolder().concat("/").concat(config.getModelPackageTargetFolder())));
        dirs.add(FilenameUtils.normalize(config.getProjectFolder().concat("/").concat(config.getDaoTargetFolder())));
        dirs.add(FilenameUtils
                .normalize(config.getProjectFolder().concat("/").concat(config.getMappingXMLTargetFolder())));
        dirs.add(FilenameUtils.normalize(config.getProjectFolder().concat("/").concat(config.getJspTargetFolder())));
        boolean haveNotExistFolder = false;
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.exists()) {
                haveNotExistFolder = true;
            }
        }
        if (haveNotExistFolder) {

            try {
                for (String dir : dirs) {
                    FileUtils.forceMkdir(new File(dir));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
