package wl.pageModel;

import wl.model.Tauth;
import wl.model.Trole;

public class RoleAuth
{

	private String id;
	private Tauth tauth;
	private Trole trole;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Tauth getTauth()
	{
		return tauth;
	}

	public void setTauth(Tauth tauth)
	{
		this.tauth = tauth;
	}

	public Trole getTrole()
	{
		return trole;
	}

	public void setTrole(Trole trole)
	{
		this.trole = trole;
	}
}
