package tech.wetech.admin.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author cjbi@outlook.com
 */
@Component
public class GlobalAnnotationDrivenListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @EventListener
    public void handleApplicationStarted(ApplicationStartedEvent ase) {
        logger.info("Handling application {} started event.", ase);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>服务启动完成！");
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
