package wl.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import wl.pageModel.Online;
import wl.service.OnlineServiceI;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action("onlineAction")
public class OnlineAction extends BaseAction implements ModelDriven<Online>
{

	private OnlineServiceI onlineService;

	private Online online = new Online();

	@Override
	public Online getModel()
	{
		// TODO Auto-generated method stub
		return online;
	}

	@Resource
	public void setOnlineService(OnlineServiceI onlineService)
	{
		this.onlineService = onlineService;
	}

	public void onlineDatagrid()
	{
		writeJson(onlineService.datagrid(online));
	}
}
