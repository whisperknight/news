package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 获取当前时间字符串，作为上传后图片文件名
	 * @return
	 */
	public static String getCurrentDateStrForFileName() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		return sf.format(date);
	}
	
	/**
	 * 将日期转化为字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatToString(Date date, String pattern) {
		String result = "";
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		if(date != null)
			result = sf.format(date);
		return result;
	}

	/**
	 * 将字符串转化为日期
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date formatToDate(String str, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		if(StringUtil.isNotEmpty(str))
			try {
				return sf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(formatToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(formatToDate("2012-1-2 22:22:22", "yyyy-MM-dd HH:mm:ss"));
	}
}
