package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class F1_LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();

		String path = request.getServletPath();

		// 防止游客登录后台需要一个一个禁掉，很麻烦，这里只做游客禁止访问后台主页示例，用了框架就会方便很多
		if (session.getAttribute("currentUser") == null
				&& path.indexOf(request.getContextPath()+"/background/mainTemplate") > 0) {
			System.out.println(" 请求失败：" + getIP(request) + "-->" + path);
			response.sendRedirect(request.getContextPath()+"/background/login.jsp");
		} else if (session.getAttribute("currentUser") != null && path.indexOf("login.jsp") > 0) {
			System.out.println(" 请求成功：" + getIP(request) + "-->" + path);
			response.sendRedirect(request.getContextPath()+"/background/mainTemplate.jsp");
		} else {
			System.out.println(" 请求成功：" + getIP(request) + "-->" + path);
			chain.doFilter(request, response);
		}
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (!checkIP(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private static boolean checkIP(String ip) {
		if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)
				|| ip.split(".").length != 4) {
			return false;
		}
		return true;
	}

}
