package net.forum.util;

import java.security.MessageDigest;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-12 15:36
 */
public class CommonUtil {

  // 给密码加密
  public static String MD5(String data) {
    try {
      java.security.MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] array = md.digest(data.getBytes("UTF-8"));
      StringBuilder sb = new StringBuilder();
      for (byte item : array) {
        sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
      }
      return sb.toString().toUpperCase();
    } catch (Exception exception) {
    }
    return null;
  }
}
