package tech.wetech.admin3.infra.service;

import tech.wetech.admin3.sys.model.UserCredential;

import java.io.Serializable;

/**
 * @author cjbi
 */
public interface SessionManager {

  void store(String key, UserCredential credential, Serializable value);

  void invalidate(String key);

  Object get(String key);

  void refresh();
}
