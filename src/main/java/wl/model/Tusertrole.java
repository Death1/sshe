package wl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Tusertrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tusertrole")
public class Tusertrole implements java.io.Serializable
{

	// Fields

	private String id;
	private Tuser tuser;
	private Trole trole;

	// Constructors

	/** default constructor */
	public Tusertrole()
	{
	}

	/** minimal constructor */
	public Tusertrole(String id)
	{
		this.id = id;
	}

	/** full constructor */
	public Tusertrole(String id, Tuser tuser, Trole trole)
	{
		this.id = id;
		this.tuser = tuser;
		this.trole = trole;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tuserId")
	public Tuser getTuser()
	{
		return this.tuser;
	}

	public void setTuser(Tuser tuser)
	{
		this.tuser = tuser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "troleId")
	public Trole getTrole()
	{
		return this.trole;
	}

	public void setTrole(Trole trole)
	{
		this.trole = trole;
	}

}