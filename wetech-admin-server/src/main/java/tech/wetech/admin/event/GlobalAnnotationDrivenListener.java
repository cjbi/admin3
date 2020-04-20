package tech.wetech.admin.event;

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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @EventListener
    public void handleApplicationStarted(ApplicationStartedEvent ase) {
        log.debug("Handling application {} started event.", ase);
        log.debug(">>>>>>>>>>>>>>> 服务启动完成！");
    }
}