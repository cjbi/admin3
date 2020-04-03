package tech.wetech.admin.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import tech.wetech.admin.model.entity.Log;
import tech.wetech.admin.service.LogService;
import tech.wetech.admin.utils.SpringUtils;

import java.util.Date;

/**
 * @author cjbi@outlook.com
 */
@Component
public class GlobalAnnotationDrivenListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @EventListener
    public void handleApplicationStarted(ApplicationStartedEvent ase) {
        log.debug("Handling application {} started event.", ase);
        log.debug(">>>>>>>>>>>>>>> 服务启动完成！");
    }
}
