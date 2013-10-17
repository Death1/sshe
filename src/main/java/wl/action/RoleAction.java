package wl.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import wl.pageModel.Json;
import wl.pageModel.Role;
import wl.service.RoleServiceI;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action("roleAction")
public class RoleAction extends BaseAction implements ModelDriven<Role>
{

	private Role role = new Role();
	private RoleServiceI roleService;

	@Override
	public Role getModel()
	{
		return role;
	}

	@Resource
	public void setRoleService(RoleServiceI roleService)
	{
		this.roleService = roleService;
	}

	public void datagrid()
	{
		writeJson(roleService.datagrid(role));
	}

	public void addOrEdit()
	{
		try
		{
			roleService.addOrEdit(role);
			writeJson(new Json(true, "添加成功！", role.getId()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			writeJson(new Json(false, "添加失败"));
		}
	}

	public void delete() throws Exception
	{
		try
		{
			roleService.delete(role.getIds());
			writeJson(new Json(true, "删除成功！"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			writeJson(new Json(false, "删除失败"));
		}
	}

	/**
	 * 获得角色下拉列表
	 */
	public void roleCombobox()
	{
		writeJson(roleService.combobox());
	}
}
