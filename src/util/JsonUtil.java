package util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class JsonUtil {
	
	/**
	 * 封装Json输出
	 * @param o
	 * @param response
	 * @throws Exception
	 */
	public static void write(Object o, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}
}
