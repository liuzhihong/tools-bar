package com.liu.utils;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
/**
 * md5加密
 * @author xianqun.li  2016年1月15日
 *
 */
public class Md5Util {
	/**
	 * md5运算
	 * @param buf
	 * @return
	 */
	public static byte[] md5(byte []buf) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(buf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * md5运算
	 * @param buf
	 * @return
	 */
	public static String md5String(byte []buf) {
		byte []tmp=md5(buf);
		return Hex.encodeHexString(tmp);
	}
	
}
