package wl.comparator;

import java.util.Comparator;

import wl.model.Tmenu;

/**
 * 菜单排序
 * 
 * 
 */
public class MenuComparator implements Comparator<Tmenu>
{

	public int compare(Tmenu o1, Tmenu o2)
	{

		int i1 = Integer.valueOf(o1.getSeq()) != null ? o1.getSeq() : -1;

		int i2 = Integer.valueOf(o2.getSeq()) != null ? o2.getSeq() : -1;
		return i1 - i2;
	}
}
