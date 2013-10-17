package wl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import wl.dao.MenuDaoI;
import wl.model.Tmenu;
import wl.pageModel.Menu;

@Component("menuService")
public class MenuServiceImpl implements MenuServiceI {

	private MenuDaoI menuDao;

	@Resource
	public void setMenuDao(MenuDaoI menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<Menu> getTree(String id) {
		List<Menu> ljm = new ArrayList<Menu>();
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (id == null) {
			// 第一次加载，查询所有根节点
			hql = "from Tmenu m where m.menu is null order by m.seq";
		} else {
			// 异步加载id 的子节点
			hql = "from Tmenu m where m.menu.id = :id order by m.seq";
			params.put("id", id);
		}
		List<Tmenu> lm = menuDao.find(hql, params);
		if (lm != null && lm.size() > 0) {
			for (Tmenu t : lm) {
				Menu jm = new Menu();
				BeanUtils.copyProperties(t, jm);
				jm.setIconCls(t.getIconcls());

				Map<String, Object> attributes = new HashMap<String, Object>();// api要求的这样的格式
				attributes.put("url", t.getUrl());
				jm.setAttributes(attributes);

				if (t.getMenus() != null && !t.getMenus().isEmpty()) {
					jm.setState("closed");// 节点以文件夹的形式体现
				} else {
					jm.setState("open");// 节点以文件的形式体现
				}
				ljm.add(jm);
			}
		}
		return ljm;
	}

	@Override
	public List<Menu> treegrid(String id) {

		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "";
		if (id == null) {
			hql = "from Tmenu m where m.menu is null order by m.seq";
		} else {
			hql = "from Tmenu m where m.menu.id = :id order by m.seq";
			params.put("id", id);
		}
		List<Tmenu> lm = menuDao.find(hql, params);
		List<Menu> ljm = new ArrayList<Menu>();
		if (lm.size() > 0 && lm != null) {
			for (Tmenu t : lm) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				if (t.getMenu() != null) {
					m.setPid(t.getMenu().getId());
					m.setPtext(t.getMenu().getText());
					m.setIconCls(t.getIconcls());
				}
				if (t.getMenus() != null && !t.getMenus().isEmpty()) {
					m.setState("closed");// 节点以文件夹的形式体现
				} else {
					m.setState("open");// 节点以文件的形式体现
				}
				ljm.add(m);
			}
		}

		return ljm;
	}

	@Override
	public List<Menu> menuTreeRecursive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void edit(Menu menu) {
		Tmenu t = new Tmenu();
		BeanUtils.copyProperties(menu, t);
		if (menu.getIconCls() != null) {
			t.setIconcls(menu.getIconCls());
		}
		if (menu.getPid() != null && !menu.getPid().equals(menu.getId())) {
			t.setMenu(menuDao.get(Tmenu.class, menu.getPid()));
		}
		menuDao.saveOrUpdate(t);
	}

	@Override
	public void delete(Menu menu) {
		del(menu.getId());
	}

	private void del(String id) {
		Tmenu t = menuDao.get(Tmenu.class, id);
		if (t != null) {
			Set<Tmenu> menus = t.getMenus();
			// 说明菜单下面还有子菜单
			if (menus != null && !menus.isEmpty()) {
				for (Tmenu tm : menus) {
					del(tm.getId());
				}
			}
			menuDao.delete(t);
		}

	}

	@Override
	public void add(Menu menu) {
		Tmenu t = new Tmenu();
		BeanUtils.copyProperties(menu, t);
		if (menu.getIconCls() != null) {
			t.setIconcls(menu.getIconCls());
		}
		if (menu.getPid() != null && !menu.getPid().equals(menu.getId())) {
			t.setMenu(menuDao.get(Tmenu.class, menu.getPid()));
		}
		menuDao.saveOrUpdate(t);
	}

}
