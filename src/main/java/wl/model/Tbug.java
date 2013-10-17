package wl.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tbug entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbug")
public class Tbug implements java.io.Serializable
{

	// Fields

	private String id;
	private Date createDateTime;
	private String bugDescribe;
	private String name;

	// Constructors

	/** default constructor */
	public Tbug()
	{
	}

	/** minimal constructor */
	public Tbug(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Tbug(String id, Date createDateTime, String bugDescribe, String name)
	{
		this.id = id;
		this.createDateTime = createDateTime;
		this.bugDescribe = bugDescribe;
		this.name = name;
	}

	// Property accessors
	@Id
	@Column(name = "id", nullable = false, length = 36)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "createDateTime", length = 19)
	public Date getCreateDateTime()
	{
		return this.createDateTime;
	}

	public void setCreateDateTime(Date createDateTime)
	{
		this.createDateTime = createDateTime;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "bugDescribe")
	public String getBugDescribe()
	{
		return bugDescribe;
	}

	public void setBugDescribe(String bugDescribe)
	{
		this.bugDescribe = bugDescribe;
	}

}