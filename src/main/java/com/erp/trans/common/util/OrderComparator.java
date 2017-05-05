package com.erp.trans.common.util;

import java.util.Comparator;
import java.util.Map;

public class OrderComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		Map orderMap1 = (Map)o1;
		Map orderMap2 = (Map)o2;
		int flag = 0;
		if((Integer)orderMap1.get("order") > (Integer)orderMap2.get("order")){
			flag = 1;
		}
		if((Integer)orderMap1.get("order") == (Integer)orderMap2.get("order")){
			flag = 0;
		}
		if((Integer)orderMap1.get("order") < (Integer)orderMap2.get("order")){
			flag = -1;
		}
		return flag;
	}
	
}
