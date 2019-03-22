package util;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

public class MD5Util {
	// MD5加密
	public static String encodingPasswordByMD5(String password) throws Exception {
		// 获取信息摘要MD5算法
		MessageDigest md = MessageDigest.getInstance("MD5");

		// 通过算法获得加密后的哈希值
		byte[] hashValue = md.digest(password.getBytes("utf-8"));

		// 获取base64编码器
		Encoder encoder = Base64.getEncoder();

		// 将哈希值编码（并非加密）为64位的可见字符串
		String md5Str = encoder.encodeToString(hashValue);

		return md5Str;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(MD5Util.encodingPasswordByMD5("123456"));
	}
}
