package wl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import wl.dao.BugDaoI;
import wl.model.Tbug;
import wl.pageModel.Bug;
import wl.pageModel.DataGrid;

@Service("bugService")
public class BugServiceImpl implements BugServiceI
{

	private BugDaoI bugDao;

	@Resource
	public void setBugDao(BugDaoI bugDao)
	{
		this.bugDao = bugDao;
	}

	@Override
	public DataGrid datagrid(Bug bug)
	{
		DataGrid dg = new DataGrid();
		String hql = "from Tbug t";

		String totalHql = "select count(*)" + hql;
		hql = addOrder(bug, hql);
		List<Tbug> lt = bugDao.find(hql, bug.getPage(), bug.getRows());
		List<Bug> lb = new ArrayList<Bug>();
		if (lt != null && lt.size() > 0)
		{
			for (Tbug t : lt)
			{
				Bug b = new Bug();
				BeanUtils.copyProperties(t, b);
				lb.add(b);
			}
		}
		dg.setTotal((int) bugDao.count(totalHql));
		dg.setRows(lb);
		return dg;
	}

	public String addOrder(Bug bug, String hql)
	{
		if (bug.getSort() != null)
		{
			hql += " order by " + bug.getSort() + " " + bug.getOrder();
		}
		return hql;
	}

	@Override
	public void saveOrUpdate(Bug bug)
	{
		if (bug.getId() == null || bug.getId().trim().equals(""))
		{
			bug.setId(UUID.randomUUID().toString());
		}
		if (bug.getCreateDateTime() == null)
		{
			bug.setCreateDateTime(new Date());
		}
		Tbug t = new Tbug();
		BeanUtils.copyProperties(bug, t);
		bugDao.saveOrUpdate(t);
	}

	@Override
	public Tbug get(Bug bug)
	{
		return bugDao.get(Tbug.class, bug.getId());
	}

	@Override
	public void delete(String ids)
	{
		if (ids != null)
		{
			for (String id : ids.split(","))
			{
				bugDao.delete(bugDao.get(Tbug.class, id));
			}

		}
	}
}
