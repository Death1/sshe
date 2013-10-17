package wl.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tauth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tauth")
public class Tauth implements java.io.Serializable
{

	// Fields

	private String id;
	private Tauth tauth;
	private String authDescribe;
	private String name;
	private String seq;
	private String url;
	private Set<Tauth> tauths = new HashSet<Tauth>(0);
	private Set<Troletauth> troletauths = new HashSet<Troletauth>(0);

	// Constructors

	/** default constructor */
	public Tauth()
	{
	}

	/** minimal constructor */
	public Tauth(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Tauth(String id, Tauth tauth, String authDescribe, String name, String seq, String url, Set<Tauth> tauths, Set<Troletauth> troletauths)
	{
		this.id = id;
		this.tauth = tauth;
		this.authDescribe = authDescribe;
		this.name = name;
		this.seq = seq;
		this.url = url;
		this.tauths = tauths;
		this.troletauths = troletauths;
	}

	public Tauth(String id, Tauth tauth, String authDescribe, String name, String seq, String url)
	{
		super();
		this.id = id;
		this.tauth = tauth;
		this.authDescribe = authDescribe;
		this.name = name;
		this.seq = seq;
		this.url = url;
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
	@JoinColumn(name = "pid")
	public Tauth getTauth()
	{
		return this.tauth;
	}

	public void setTauth(Tauth tauth)
	{
		this.tauth = tauth;
	}

	@Column(name = "authDescribe", length = 200)
	public String getAuthDescribe()
	{
		return this.authDescribe;
	}

	public void setAuthDescribe(String authDescribe)
	{
		this.authDescribe = authDescribe;
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

	@Column(name = "seq", length = 22)
	public String getSeq()
	{
		return this.seq;
	}

	public void setSeq(String seq)
	{
		this.seq = seq;
	}

	@Column(name = "url", length = 200)
	public String getUrl()
	{
		return this.url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tauth")
	public Set<Tauth> getTauths()
	{
		return this.tauths;
	}

	public void setTauths(Set<Tauth> tauths)
	{
		this.tauths = tauths;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tauth")
	public Set<Troletauth> getTroletauths()
	{
		return this.troletauths;
	}

	public void setTroletauths(Set<Troletauth> troletauths)
	{
		this.troletauths = troletauths;
	}

}