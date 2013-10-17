package wl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import wl.dao.OnlineDaoI;
import wl.model.Tonline;
import wl.pageModel.DataGrid;
import wl.pageModel.Online;

@Component("onlineService")
public class OnlineServiceImpl implements OnlineServiceI
{

	private OnlineDaoI onlineDao;

	@Resource
	public void setOnlineDao(OnlineDaoI onlineDao)
	{
		this.onlineDao = onlineDao;
	}

	@Override
	public DataGrid datagrid(Online online)
	{
		DataGrid dg = new DataGrid();

		dg.setRows(find(online));
		dg.setTotal((int) onlineDao.count("select count(*) from Tonline where 1=1 "));
		return dg;
	}

	private List<Tonline> find(Online online)
	{
		String hql = "from Tonline t where 1=1";
		if (online.getSort() != null && online.getOrder() != null)
		{
			hql += " order by " + online.getSort() + " " + online.getOrder();
		}
		return onlineDao.find(hql, online.getPage(), online.getRows());
	}

	public String addOrder(Online online, String hql)
	{
		if (online.getSort() != null)
		{
			hql += " order by " + online.getSort() + " " + online.getOrder();
		}
		return hql;
	}

	@Override
	public void updateOnline(Tonline online)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", online.getName());
		params.put("ip", online.getId());
		String hql = "from Tonline t where t.name = :name and t.ip = :ip";
		List<Tonline> t = onlineDao.find(hql, params);
		if (t != null && t.size() > 0)
		{
			t.get(0).setDatetime(new Date());
		}
		else
		{
			online.setId(UUID.randomUUID().toString());
			onlineDao.save(online);
		}
	}

	@Override
	public void delete(String loginName, String ip)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		params.put("ip", ip);
		String hql = "delete Tonline t where t.name=:loginName and t.ip=:ip ";
		onlineDao.executeHql(hql, params);
	}
}
