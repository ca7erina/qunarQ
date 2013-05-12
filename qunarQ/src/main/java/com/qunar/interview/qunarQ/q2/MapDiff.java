package com.qunar.interview.qunarQ.q2;



import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;



public class MapDiff {
	

	public static Map<String, String> diffMap(Map<String, String> map1,Map<String, Object> map2) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		if (map1 == null || map1.size() == 0 || map2 == null) {
			return result;
		}
		
		for (Entry<String, String> entry : map1.entrySet()) {
			String key = entry.getKey();
			String value1 = entry.getValue();
			
			if (StringUtils.isEmpty(value1)) {
				continue;
			}
			
			Object value2 = map2.get(key);
			
			if (value2 == null) {
				continue;
			}
			
			if (value2 instanceof String) {
				if (value1.equals(value2)) {
					result.put(key, value1);
				}
			} else if (value2 instanceof String[]) {
				// 拆分value2再与value1进行对比，符合要求的加入result
			} else {
				continue;
			}
		}
		return result;
	}

	
}
