package wl.model;

// default package

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
 * Menu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tmenu")
public class Tmenu implements java.io.Serializable
{

	// Fields

	private String id;
	private Tmenu menu;
	private String iconCls ;
	private String ptext;
	private String text;
	private String url;
	private int seq;
	private Set<Tmenu> menus = new HashSet<Tmenu>(0);

	// Constructors

	/** default constructor */
	public Tmenu()
	{
	}

	/** minimal constructor */
	public Tmenu(String id)
	{
		this.id = id;
	}

	public Tmenu(String id, String text, String url,int seq)
	{
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.seq = seq;
	}

	public Tmenu(String id, Tmenu menu, String text, String url, int seq)
	{
		super();
		this.id = id;
		this.menu = menu;
		this.text = text;
		this.url = url;
		this.seq = seq;
	}

	/** full constructor */
	public Tmenu(String id, Tmenu menu, String iconCls, String ptext, String text, String url, Set<Tmenu> menus)
	{
		this.id = id;
		this.menu = menu;
		this.iconCls = iconCls;
		this.ptext = ptext;
		this.text = text;
		this.url = url;
		this.menus = menus;
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
	public Tmenu getMenu()
	{
		return this.menu;
	}

	public void setMenu(Tmenu menu)
	{
		this.menu = menu;
	}

	@Column(name = "iconcls")
	public String getIconcls()
	{
		return this.iconCls;
	}

	public void setIconcls(String iconCls)
	{
		this.iconCls = iconCls;
	}

	@Column(name = "ptext")
	public String getPtext()
	{
		return this.ptext;
	}

	public void setPtext(String ptext)
	{
		this.ptext = ptext;
	}

	@Column(name = "text")
	public String getText()
	{
		return this.text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	@Column(name = "url")
	public String getUrl()
	{
		return this.url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
	public Set<Tmenu> getMenus()
	{
		return this.menus;
	}

	public void setMenus(Set<Tmenu> menus)
	{
		this.menus = menus;
	}

	@Column(name = "seq")
	public int getSeq()
	{
		return seq;
	}

	public void setSeq(int seq)
	{
		this.seq = seq;
	}

}