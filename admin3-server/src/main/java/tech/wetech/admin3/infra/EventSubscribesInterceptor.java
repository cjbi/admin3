package tech.wetech.admin3.infra;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.wetech.admin3.common.DomainEvent;
import tech.wetech.admin3.common.DomainEventPublisher;
import tech.wetech.admin3.common.EventStore;
import tech.wetech.admin3.sys.event.ResourceDeleted;
import tech.wetech.admin3.sys.event.ResourceUpdated;
import tech.wetech.admin3.sys.event.RoleDeleted;
import tech.wetech.admin3.sys.event.RoleUpdated;
import tech.wetech.admin3.sys.service.SessionService;

/**
 * 通用事件处理拦截器，
 *
 * @author cjbi
 */
public class EventSubscribesInterceptor implements HandlerInterceptor {
  private final EventStore eventStore;
  private final SessionService sessionService;

  public EventSubscribesInterceptor(EventStore eventStore, SessionService sessionService) {
    this.eventStore = eventStore;
    this.sessionService = sessionService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    DomainEventPublisher.instance().reset();
    DomainEventPublisher.instance().subscribe(DomainEvent.class, eventStore::append);
    //发生以下事件, 刷新会话
    DomainEventPublisher.instance().subscribe(RoleUpdated.class, event -> sessionService.refresh());
    DomainEventPublisher.instance().subscribe(RoleDeleted.class, event -> sessionService.refresh());
    DomainEventPublisher.instance().subscribe(ResourceUpdated.class, event -> sessionService.refresh());
    DomainEventPublisher.instance().subscribe(ResourceDeleted.class, event -> sessionService.refresh());
    return true;
  }


}
