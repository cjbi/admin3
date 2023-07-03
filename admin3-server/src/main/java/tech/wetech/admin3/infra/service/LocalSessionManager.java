package tech.wetech.admin3.infra.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin3.common.JsonUtils;
import tech.wetech.admin3.sys.model.Session;
import tech.wetech.admin3.sys.model.User;
import tech.wetech.admin3.sys.model.UserCredential;
import tech.wetech.admin3.sys.repository.SessionRepository;
import tech.wetech.admin3.sys.service.dto.UserinfoDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cjbi
 */
@Component
public class LocalSessionManager implements SessionManager {

  private static final long EXPIRE_DAYS = 7;

  private final SessionRepository sessionRepository;

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final Cache<String, Session> cache = Caffeine.newBuilder()
    .initialCapacity(100)
    .expireAfterAccess(EXPIRE_DAYS, TimeUnit.DAYS)
    .build();

  private final ScheduledExecutorService sessionCheckExecutor = new ScheduledThreadPoolExecutor(1);

  public LocalSessionManager(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
    checkSession();
  }

  private void checkSession() {
    sessionCheckExecutor.scheduleWithFixedDelay(() -> {
      try {
        ConcurrentMap<String, Session> map = cache.asMap();
        sessionRepository.deleteExpiredSession();
        map.forEach((key, value) -> {
          if (value.isActive()) {
            value.setActive(false);
            sessionRepository.updateExpireTime(key, value.getExpireTime());
          }
        });
      } catch (Exception e) {
        log.error("{}", e.getMessage(), e);
      }
    }, 5, 5, TimeUnit.SECONDS);
  }

  @Override
  public void store(String key, UserCredential credential, Serializable value) {
    Session currentSession = cache.getIfPresent(key);
    Session newSession = Session.of(currentSession != null ? currentSession.getId() : null, key, credential, value, getLatestExpireTime());
    cache.put(key, sessionRepository.save(newSession));
  }

  private static LocalDateTime getLatestExpireTime() {
    return LocalDateTime.now().plus(EXPIRE_DAYS, ChronoUnit.DAYS);
  }


  @Override
  public void invalidate(String key) {
    Session session = cache.getIfPresent(key);
    cache.invalidate(key);
    assert session != null;
    sessionRepository.delete(session);
  }

  @Override
  public Object get(String key) {
    try {
      Session session;
      if ((session = cache.getIfPresent(key)) == null && (session = sessionRepository.findByToken(key).orElse(null)) == null) {
        return null;
      }
      session.setExpireTime(getLatestExpireTime());
      session.setActive(true);
      cache.put(key, session);
      return JsonUtils.parseToObject(session.getData(), UserinfoDTO.class);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Override
  @Transactional
  public void refresh() {
    sessionRepository.findAllStream().forEach(session -> {
      UserCredential credential = session.getCredential();
      User user = credential.getUser();
      UserinfoDTO userinfo = new UserinfoDTO(session.getToken(), user.getId(), user.getUsername(), user.getAvatar(), new UserinfoDTO.Credential(credential.getIdentifier(), credential.getIdentityType()), user.findPermissions());
      session.setData(JsonUtils.stringify(userinfo));
      sessionRepository.saveAndFlush(session);
    });
    cache.invalidateAll();
  }
}
