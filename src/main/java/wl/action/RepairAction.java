package wl.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import wl.service.RepairServiceI;

@Namespace("/")
@Action(value = "repairAction")
public class RepairAction extends BaseAction {
	private RepairServiceI repairService;

	@Resource
	public void setRepairService(RepairServiceI repairService) {
		this.repairService = repairService;
	}

	public void init() {
		repairService.repair();
	}

}
