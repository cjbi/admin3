package tech.wetech.admin.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import tech.wetech.admin.utils.JsonUtil;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 自定义登录过滤器返回json，默认是返回视图
 *
 * @author cjbi
 */
@Slf4j
public class JsonAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String json = getJsonString((HttpServletRequest) request);
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("参数异常");
        }
        Map<String, Object> map = JsonUtil.toObject(json, Map.class);
        if (!map.containsKey(getUsernameParam())) {
            throw new IllegalArgumentException("username不存在");
        }
        if (!map.containsKey(getPasswordParam())) {
            throw new IllegalArgumentException("password不存在");
        }
        String username = (String) map.get(getUsernameParam());
        String password = (String) map.get(getPasswordParam());
        return createToken(username, password, request, response);
    }

    public String getJsonString(HttpServletRequest request) {
        try {
            String submitMehtod = request.getMethod();
            // GET
            if (submitMehtod.equals(GET_METHOD)) {
                return new String(request.getQueryString().getBytes("iso-8859-1"), "utf-8").replaceAll("%22", "\"");
                // POST
            } else {
                return getRequestPostStr(request);
            }
        } catch (Exception e) {
            return "";
        }
    }

    public String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }


    public byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

}
