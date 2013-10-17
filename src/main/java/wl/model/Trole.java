package wl.model;

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
 * Trole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "trole")
public class Trole implements java.io.Serializable
{

	// Fields

	private String id;
	private String roleRescribe;
	private String name;
	private Set<Troletauth> troletauths = new HashSet<Troletauth>(0);
	private Set<Tusertrole> tusertroles = new HashSet<Tusertrole>(0);

	// Constructors

	/** default constructor */
	public Trole()
	{
	}

	/** minimal constructor */
	public Trole(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Trole(String id, String roleRescribe, String name, Set<Troletauth> troletauths, Set<Tusertrole> tusertroles)
	{
		this.id = id;
		this.roleRescribe = roleRescribe;
		this.name = name;
		this.troletauths = troletauths;
		this.tusertroles = tusertroles;
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

	@Column(name = "roleRescribe", length = 200)
	public String getRoleRescribe()
	{
		return this.roleRescribe;
	}

	public void setRoleRescribe(String roleRescribe)
	{
		this.roleRescribe = roleRescribe;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trole")
	public Set<Troletauth> getTroletauths()
	{
		return this.troletauths;
	}

	public void setTroletauths(Set<Troletauth> troletauths)
	{
		this.troletauths = troletauths;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trole")
	public Set<Tusertrole> getTusertroles()
	{
		return this.tusertroles;
	}

	public void setTusertroles(Set<Tusertrole> tusertroles)
	{
		this.tusertroles = tusertroles;
	}

}