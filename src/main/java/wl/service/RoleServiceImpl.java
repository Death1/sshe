package wl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import wl.dao.AuthDaoI;
import wl.dao.RoleAuthDaoI;
import wl.dao.RoleDaoI;
import wl.dao.UserRoleDaoI;
import wl.model.Tauth;
import wl.model.Trole;
import wl.model.Troletauth;
import wl.pageModel.DataGrid;
import wl.pageModel.Role;

@Component("roleService")
public class RoleServiceImpl implements RoleServiceI {

	private RoleDaoI roleDao;
	private AuthDaoI authDao;
	private RoleAuthDaoI roleAuthDao;
	private UserRoleDaoI userRoleDao;

	@Resource
	public void setUserRoleDao(UserRoleDaoI userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Resource
	public void setRoleAuthDao(RoleAuthDaoI roleAuthDao) {
		this.roleAuthDao = roleAuthDao;
	}

	@Resource
	public void setAuthDao(AuthDaoI authDao) {
		this.authDao = authDao;
	}

	@Resource
	public void setRoleDao(RoleDaoI roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public DataGrid datagrid(Role role) {
		DataGrid dg = new DataGrid();
		String hql = "from Trole t where 1=1";
		List<Trole> lt = roleDao.find(hql, role.getPage(), role.getRows());
		List<Role> lr = new ArrayList<Role>();
		if (lt != null && lt.size() > 0) {
			for (Trole t : lt) {
				Role r = new Role();
				BeanUtils.copyProperties(t, r);
				Set<Troletauth> troletauth = t.getTroletauths();
				String authIds = "";
				String authNames = "";
				if (troletauth != null & troletauth.size() > 0) {

					for (Troletauth tr : troletauth) {
						if (tr.getTauth() != null) {
							authIds += "," + tr.getTauth().getId();
							authNames += "," + tr.getTauth().getName();
						}
					}
				}
				if (authIds.equals("")) {
					r.setAuthIds("");
					r.setAuthNames("");
				} else {
					r.setAuthIds(authIds.substring(1));
					r.setAuthNames(authNames.substring(1));
				}
				lr.add(r);
			}
		}
		dg.setRows(lr);
		dg.setTotal((int) roleDao.count("select count(*)" + hql));
		return dg;
	}

	@Override
	public void addOrEdit(Role role) {
		Trole t = new Trole();
		BeanUtils.copyProperties(role, t);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", role.getId());
		roleAuthDao.executeHql("delete Troletauth t where t.trole.id = :id", params);

		if (role.getAuthIds() != null) {
			for (String id : role.getAuthIds().split(",")) {
				Troletauth troletauths = new Troletauth();
				troletauths.setId(UUID.randomUUID().toString());
				troletauths.setTrole(t);
				troletauths.setTauth(authDao.get(Tauth.class, id.trim()));
				roleAuthDao.save(troletauths);
			}
		}
		roleDao.saveOrUpdate(t);
	}

	@Override
	public void delete(String ids) {
		if (ids != null) {
			// 传过来 是角色ID
			for (String id : ids.split(",")) {
				Map<String, Object> params = new HashMap<String, Object>();
				Trole t = roleDao.get(Trole.class, id.trim());
				if (t != null) {
					params.put("id", id);
					roleAuthDao.executeHql("delete Troletauth t where t.trole.id = :id ", params);
					userRoleDao.executeHql("delete Tusertrole t where t.trole.id = :id ", params);
					roleDao.delete(t);
				}

			}
		}
	}

	@Override
	public List<Role> combobox() {
		List<Trole> lt = roleDao.find("from Trole t");
		List<Role> lr = new ArrayList<Role>();
		if (lt != null && lt.size() > 0) {
			for (Trole t : lt) {
				Role role = new Role();
				BeanUtils.copyProperties(t, role);
				lr.add(role);
			}
		}
		return lr;
	}
}
