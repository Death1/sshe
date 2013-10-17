package wl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Troletauth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "troletauth")
public class Troletauth implements java.io.Serializable
{

	// Fields

	private String id;
	private Tauth tauth;
	private Trole trole;

	// Constructors

	/** default constructor */
	public Troletauth()
	{
	}

	/** minimal constructor */
	public Troletauth(String id)
	{
		this.id = id;
	}

	/** full constructor */
	public Troletauth(String id, Tauth tauth, Trole trole)
	{
		this.id = id;
		this.tauth = tauth;
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
	@JoinColumn(name = "authId")
	public Tauth getTauth()
	{
		return this.tauth;
	}

	public void setTauth(Tauth tauth)
	{
		this.tauth = tauth;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId")
	public Trole getTrole()
	{
		return this.trole;
	}

	public void setTrole(Trole trole)
	{
		this.trole = trole;
	}

}