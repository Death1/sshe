package wl.action;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.util.FileCopyUtils;

import wl.pageModel.Bug;
import wl.pageModel.Json;
import wl.service.BugServiceI;
import wl.util.ResourceUtil;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action("bugAction")
public class BugAction extends BaseAction implements ModelDriven<Bug> {

	private Bug bug = new Bug();
	private BugServiceI bugService;

	@Override
	public Bug getModel() {
		return bug;
	}

	@Resource
	public void setBugService(BugServiceI bugService) {
		this.bugService = bugService;
	}

	public void datagrid() {
		writeJson(bugService.datagrid(bug));
	}

	public void upload() {
		// 文件保存路径
		String savePath = ServletActionContext.getServletContext().getRealPath("/") + ResourceUtil.getUploadDirectory() + "/";
		String saveUrl = "/" + ResourceUtil.getUploadDirectory() + "/";
		// HTML5 拖拽上传文件
		String contentDisposition = ServletActionContext.getRequest().getHeader("Content-Disposition");
		if (contentDisposition != null) {
			// 上传文件大小
			Long fileSize = Long.valueOf(ServletActionContext.getRequest().getHeader("Content-Length"));
			String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("filename=\""));
			fileName = fileName.substring(fileName.indexOf("\"") + 1);
			fileName = fileName.substring(0, fileName.indexOf("\""));

			ServletInputStream inputStream;

			try {
				inputStream = ServletActionContext.getRequest().getInputStream();
			} catch (Exception e) {
				uploadError("上传文件失败");
				e.printStackTrace();
				return;
			}
			if (inputStream == null) {
				uploadError("你没有上传任何文件");
				return;
			}
			if (fileSize > ResourceUtil.getUploadFileMaxSize()) {

				uploadError("上传文件超出限制大小");
				return;
			}
			// 检查文件的扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				uploadError("上传文件扩展名是不允许的扩展名.\n 只允许" + ResourceUtil.getUploadFileExts() + "格式");
				return;

			}
			savePath += fileExt + "/";
			saveUrl += fileExt + "/";
			SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthDf = new SimpleDateFormat("MM");
			SimpleDateFormat dateDf = new SimpleDateFormat("dd");
			Date date = new Date();
			String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
			savePath += ymd;
			saveUrl += ymd;
			File uploadDir = new File(savePath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			try {

				File uploadedFile = new File(savePath, URLDecoder.decode(fileName, "utf-8"));
				FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile));

				Map<String, Object> m = new HashMap<String, Object>();
				m.put("err", "");
				Map<String, Object> nm = new HashMap<String, Object>();
				nm.put("url", ServletActionContext.getRequest().getContextPath() + saveUrl + URLDecoder.decode(fileName, "utf-8"));
				nm.put("localfile", fileName);
				nm.put("id", "");
				m.put("msg", nm);
				writeJson(m);
			} catch (Exception e) {
				uploadError("上传文件出错！");
				e.printStackTrace();
				return;
			}
		}
	}

	private void uploadError(String err) {
		uploadError(err, "");
	}

	private void uploadError(String err, String msg) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", err);
		m.put("msg", msg);
		writeJson(m);

	}

	public void saveOrUpdate() {
		try {
			bugService.saveOrUpdate(bug);
			super.writeJson(new Json(true, "添加成功"));
		} catch (Exception e) {
			e.printStackTrace();
			super.writeJson(new Json(false, "添加失败"));
		}
	}

	public void showBugDescribeDialog() {
		super.writeJson(bugService.get(bug));
	}

	public void delete() {
		try {
			bugService.delete(bug.getIds());
			super.writeJson(new Json(true, "删除成功"));
		} catch (Exception e) {
			e.printStackTrace();
			super.writeJson(new Json(false, "删除失败"));
		}
	}
}
