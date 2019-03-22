package web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;
import util.DateUtil;
import util.JsonUtil;
import util.PropertiesUtil;

@WebServlet("/imageUploadServlet")
public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置编码
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 构造了一个文件上传处理对象
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 获取提交的所有文件内容
			@SuppressWarnings("unchecked")
			List<FileItem> fileItemList = upload.parseRequest(request);

			// 循环上传
			for (FileItem item : fileItemList) {
				// 如果提交的是文件且文件名不为空串
				if (item.getName() != null && !item.getName().equals("")) {
					String newImageNameWithoutSuffix = DateUtil.getCurrentDateStrForFileName();
					String newFileName = newImageNameWithoutSuffix + item.getName().substring(item.getName().lastIndexOf("."));
					String url = PropertiesUtil.getValue("newsImageRelativePath") + "/" + newFileName;

					// 写入文件
					item.write(new File(PropertiesUtil.getValue("projectAbsolutePath") + url));

					// 设置CKEditor的Json回调
					JSONObject jo = new JSONObject();
					jo.put("uploaded", 1);
					jo.put("fileName", newFileName);
					jo.put("url", url);
					JsonUtil.write(jo, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
