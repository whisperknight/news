package util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
	/**
	 * 获取属性文件里的值
	 * @param key 键
	 * @return 值
	 */
	public static String getValue(String key) {
		Properties p = new Properties();
		
		try {
			p.load(new PropertiesUtil().getClass().getResourceAsStream("/news.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return p.getProperty(key);
	}
}
