package wl.comparator;

import java.util.Comparator;

import wl.model.Tauth;

/**
 * Auth排序
 * 
 * @author 孙宇
 * 
 */
public class AuthComparator implements Comparator<Tauth> {

	public int compare(Tauth o1, Tauth o2) {
		int i1 = Integer.valueOf(o1.getSeq()) != null ? Integer.valueOf(o1.getSeq()) : -1;
		int i2 = Integer.valueOf(o2.getSeq()) != null ? Integer.valueOf(o2.getSeq()) : -1;
		return i1 - i2;
	}
}
