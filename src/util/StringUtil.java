package util;

import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str != null && !str.trim().equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 浮点型判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {
		if (str == null || "".equals(str))
			return false;
		// *表示匹配零个或多个，\\.表示转义的小数点，?表示匹配零个或一个
		return str.matches("[0-9]*(\\.?)[0-9]*");
	}

	/**
	 * 整型判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (str == null)
			return false;
		return str.matches("[0-9]+");
	}

	/**
	 * 判断字符串数组里是否存在str字符串
	 * 
	 * @param strArr
	 * @param str
	 * @return
	 */
	public static boolean existStrArr(String[] strArr, String str) {
		for (int i = 0; i < strArr.length; i++) {
			if (strArr[i].equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static String htmlToText(String inputString) {
		// 过滤html标签
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		java.util.regex.Matcher m_script;
		Pattern p_style;
		java.util.regex.Matcher m_style;
		Pattern p_html;
		java.util.regex.Matcher m_html;
		Pattern p_cont1;
		java.util.regex.Matcher m_cont1;
		Pattern p_cont2;
		java.util.regex.Matcher m_cont2;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_cont1 = "[\\d+\\s*`~!@#$%^&*\\(?~！@#￥%……&*（）——+|{}【】‘：”“’_]"; // 定义HTML标签的正则表达式
			String regEx_cont2 = "[\\w[^\\W]*]"; // 定义HTML标签的正则表达式[a-zA-Z]
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			p_cont1 = Pattern.compile(regEx_cont1, Pattern.CASE_INSENSITIVE);
			m_cont1 = p_cont1.matcher(htmlStr);
			htmlStr = m_cont1.replaceAll(""); // 过滤其它标签
			p_cont2 = Pattern.compile(regEx_cont2, Pattern.CASE_INSENSITIVE);
			m_cont2 = p_cont2.matcher(htmlStr);
			htmlStr = m_cont2.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}
}
