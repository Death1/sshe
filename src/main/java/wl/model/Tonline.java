package wl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tonline entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tonline")
public class Tonline implements java.io.Serializable
{

	// Fields

	private String id;
	private Date datetime;
	private String name;
	private String ip;

	// Constructors

	/** default constructor */
	public Tonline()
	{
	}

	/** full constructor */
	public Tonline(String id, Date datetime, String name, String ip)
	{
		this.id = id;
		this.datetime = datetime;
		this.name = name;
		this.ip = ip;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "datetime", nullable = false, length = 19)
	public Date getDatetime()
	{
		return this.datetime;
	}

	public void setDatetime(Date datetime)
	{
		this.datetime = datetime;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "ip", nullable = false, length = 100)
	public String getIp()
	{
		return this.ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

}