package wl.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import wl.pageModel.Json;
import wl.pageModel.Menu;
import wl.service.MenuServiceI;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action("menuAction")
public class MenuAction extends BaseAction implements ModelDriven<Menu> {

	private MenuServiceI menuService;

	@Resource
	public void setMenuService(MenuServiceI menuService) {
		this.menuService = menuService;
	}

	private Menu menu = new Menu();

	@Override
	public Menu getModel() {
		return menu;
	}

	public void getTree() {
		super.writeJson(menuService.getTree(menu.getId()));
	}

	/**
	 * 获得菜单treegrid
	 */
	public void treegrid() {
		writeJson(menuService.treegrid(menu.getId()));
	}

	/**
	 * 修改的时候 获得所有的树
	 */
	public void menuTreeRecursive() {
		writeJson(menuService.getTree(menu.getId()));
	}

	/**
	 * 修改 树
	 */
	public void edit() throws Exception {
		try {
			menuService.edit(menu);
			writeJson(new Json(true, "编辑成功，请手动刷新左侧菜单树"));
		} catch (Exception e) {
			e.printStackTrace();
			writeJson(new Json(false, "编辑失败"));
		}
	}

	/**
	 * 删除一个菜单
	 */
	public void delete() throws Exception {
		try {
			menuService.delete(menu);
			writeJson(new Json(true, "删除成功！请手动刷新左侧功能菜单树", menu.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			writeJson(new Json(false, "删除失败"));
		}
	}

	/**
	 * 增加一个菜单
	 */
	public void add() throws Exception {
		try {
			menuService.add(menu);
			writeJson(new Json(true, "添加成功！请手动刷新左侧功能菜单树", menu.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			writeJson(new Json(false, "添加失败"));
		}
	}
}
