package tech.wetech.admin3.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author cjbi
 */
public class SecurityUtil {

  public static String md5(String username, String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(username.getBytes());
    md.update(password.getBytes());
    return new BigInteger(1, md.digest()).toString(16);
  }

}
