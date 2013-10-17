package wl.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import wl.pageModel.Auth;
import wl.pageModel.Json;
import wl.service.AuthServiceI;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action("authAction")
public class AuthAction extends BaseAction implements ModelDriven<Auth> {

	private Auth auth = new Auth();
	private AuthServiceI authService;

	@Override
	public Auth getModel() {
		return auth;
	}

	@Resource
	public void setAuthService(AuthServiceI authService) {
		this.authService = authService;
	}

	public void treegrid() {
		super.writeJson(authService.treegrid(auth));
	}

	/**
	 * 删除一个权限
	 */
	public void delete() throws Exception {
		try {
			authService.delete(auth);
			writeJson(new Json(true, "删除成功！请手动刷新左侧功能菜单树", auth.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			writeJson(new Json(false, "删除失败"));
		}
	}

	public void add() throws Exception {
		try {
			authService.addOrEdit(auth);
			writeJson(new Json(true, "添加成功！请手动刷新左侧功能菜单树", auth.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			writeJson(new Json(false, "添加失败"));
		}
	}

	public void menuTreeRecursive() {
		writeJson(authService.tree(auth));
	}

}
