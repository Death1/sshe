package wl.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tuser")
public class Tuser implements java.io.Serializable
{

	// Fields

	private String id;
	private Date createDateTime;
	private Date modifyDateTime;
	
	private String name;
	private String pwd;
	private Set<Tusertrole> tusertroles = new HashSet<Tusertrole>(0);
	/** default constructor */
	public Tuser()
	{
	}

	// Constructors

	/** minimal constructor */
	public Tuser(String id)
	{
		this.id = id;
	}

	public Tuser(String id, String name, String pwd)
	{
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	/** full constructor */
	public Tuser(String id, Date createDateTime, Date modifyDateTime, String name, String pwd, Set<Tusertrole> tusertroles)
	{
		this.id = id;
		this.createDateTime = createDateTime;
		this.modifyDateTime = modifyDateTime;
		this.name = name;
		this.pwd = pwd;
		this.tusertroles = tusertroles;
	}

	@Column(name = "createDateTime", length = 19)
	public Date getCreateDateTime()
	{
		return this.createDateTime;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId()
	{
		return this.id;
	}

	@Column(name = "modifyDateTime", length = 19)
	public Date getModifyDateTime()
	{
		return this.modifyDateTime;
	}

	@Column(name = "name")
	public String getName()
	{
		return this.name;
	}

	@Column(name = "pwd")
	public String getPwd()
	{
		return this.pwd;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tuser")
	public Set<Tusertrole> getTusertroles()
	{
		return this.tusertroles;
	}

	public void setCreateDateTime(Date createDateTime)
	{
		this.createDateTime = createDateTime;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setModifyDateTime(Date modifyDateTime)
	{
		this.modifyDateTime = modifyDateTime;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public void setTusertroles(Set<Tusertrole> tusertroles)
	{
		this.tusertroles = tusertroles;
	}

}