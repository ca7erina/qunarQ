package com.qunar.interview.qunarQ.q2;

import static org.hamcrest.Matchers.*;

import static com.qunar.interview.qunarQ.q2.MapDiff.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;







public class MapDiffTest extends TestCase {
	int i=0;
	int j=0;
	@Before
	public void start(){
		System.out.println("start"+ this.i++);
	}
	@After
	public void end(){
		System.out.println("end"+this.j++);
	}
	
	// Testcase1: 当Map1为empty时

	@Test
	public void testReturnEmptyMapWhenParamIsEmpty() throws Exception {
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("keyA1","valueA1");
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("keyB2","valueB2");
		Map<String,String> emptyMap1=new HashMap<String,String>();
		Map<String,Object> emptyMap2=new HashMap<String,Object>();
		Map<String,String> Result_EmptyMap=new HashMap<String,String>();

		assertThat(Result_EmptyMap,is(diffMap(emptyMap1,map2)));
		assertThat(Result_EmptyMap,is(diffMap(map1,emptyMap2)));
		assertThat(Result_EmptyMap,is(diffMap(emptyMap1,emptyMap2)));
		
		
	}
	// Testcase2: 当Map为null时
	@Test
	public void testReturnEmptyMapWhenParamIsNull(){
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("keyA1","valueA1");	
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("keyB2","valueB2");
		Map<String,String> Result_EmptyMap=new HashMap<String,String>();
		
		assertThat(Result_EmptyMap, is(diffMap(null,map2)));
		assertThat(Result_EmptyMap, is(diffMap(null,null)));
		assertThat(Result_EmptyMap, is(diffMap(map1,null)));
	}
	
	// Testcase3: 当Map中有null值时
	@Test
	public void testReturnEmptyMapWhenMapContainsNullValue() {
		Map<String,String> map1Null=new HashMap<String,String>();
		map1Null.put("keyA1","");
		map1Null.put("","");
		map1Null.put("","valueA1");
		Map<String,Object> map2Null=new HashMap<String,Object>();
		map2Null.put("keyB1","");
		map2Null.put("","");
		map2Null.put("","valueB1");
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("keyA1","valueA1");
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("keyB2","valueB2");
		Map<String,String> Result_EmptyMap=new HashMap<String,String>();
		
		
		assertThat(Result_EmptyMap, is(diffMap(map1,map2Null)));
		assertThat(Result_EmptyMap, is(diffMap(map1Null,map2)));
		assertThat(Result_EmptyMap, is(diffMap(map1Null,map2Null)));
	}
	// Testcase4: 当Map2中的value不包含String 或 String 数组时
	@Test
	public void testReturnEmptyMapWhenMap2DoesNotContainStringOrStringArray() {
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("1","1");
		map1.put("2","2");
		Map<String,Object> map2=new HashMap<String,Object>();
		char noString[]= new char[]{'1','2'};
		map2.put("1",1);
		map2.put("2",noString);
		Map<String,String> Result_EmptyMap=new HashMap<String,String>();
		assertThat(Result_EmptyMap, is(diffMap(map1,map2)));
	
	}
	
	
	// Testcase5: 当Map2中的value包含String 时
	@Test
	public void testReturnMapWhenMap2DoesContainString() {
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("1","1");
		map1.put("2","2");
		Map<String,Object> map2=new HashMap<String,Object>();
		
		map2.put("1","1");
		map2.put("2",2);
		Map<String,String> Result_Map=new HashMap<String,String>();
		Result_Map.put("1","1");
	
		assertThat(Result_Map, is(diffMap(map1,map2)));
	
	}
	
	// Testcase5: 当Map2中的value包含 String[]时
	@Test
	public void testReturnMapWhenMap2ContainStringArray() {
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("1","1");
		map1.put("2","2");
		Map<String,Object> map2=new HashMap<String,Object>();
		String strings[]= new String[]{"2","3"};
		map2.put("1","1");
		map2.put("3",strings);
		Map<String,String> Result_Map=new HashMap<String,String>();
		Result_Map.put("1","1");
		Result_Map.put("3","3");
		assertThat(Result_Map, is(diffMap(map1,map2)));
	
	}
	
	
	// Testcase6: Map1 size 大于 Map2
	//Map1 size 大于 Map2 size;key无交级
	//Map1 size 大于 Map2 size; key有交集,value全不等
	//Map1 size 大于 Map2 size; key有交集,value部分等
	//Map1 size 大于 Map2 size; key有交集,value全等

	@Test
	public void testReturnMapWhenMap2IsASubColection() {
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("moreKeyThanMap2_A", "moreValueThanMap2_A");
		map1.put("moreKeyThanMap2_B", "moreValueThanMap2_B");
		map1.put("moreKeyThanMap2_C", "moreValueThanMap2_C");
		map1.put("samekey_1", "samevalue_1");
		map1.put("samekey_2","samevalue_2");
		map1.put("samekey_3","samevalue_3");
	
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("moreKeyThanMap1_A", "moreKeyThanMap1_A");
		
		Map<String,String> Result_NoIntersection=new HashMap<String,String>();
		
		//Map1 size 大于 Map2 size;key无交集
		assertThat(Result_NoIntersection, is(diffMap(map1,map2)));
		
		
		Map<String,String> Result_Intersection=new HashMap<String,String>();
		map2.put("samekey_1","Nosamevalue_3");
		//Map1 size 大于 Map2 size; key有交集,value全不等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
		
		
		map2.put("samekey_2","samevalue_2");
		Result_Intersection.put("samekey_2","samevalue_2");
		//Map1 size 大于 Map2 size; key有交集,value部分等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
		
		

		map2.put("samekey_1","samevalue_1");
		Result_Intersection.put("samekey_1","samevalue_1");
		//Map1 size 大于 Map2 size; key有交集,value全等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
		
		
		
		
		
		
		
	}

	// Testcase7: Map1 size =map2 size
	//Map1 size 等于 Map2 size;key无交级
	//Map1 size 等于 Map2 size; key有交集,value全不等
	//Map1 size 等于Map2 size; key有交集,value部分等
	//Map1 size 等于 Map2 size; key有交集,value全等
	@Test
	public void testReturnMapWhenMapSameSize() {
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("key_A", "value_A");
			
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("key_B", "value_B");
		
		Map<String,String> Result_NoIntersection=new HashMap<String,String>();
		
		//Map1 size 等于 Map2 size;key无交集
		assertThat(Result_NoIntersection, is(diffMap(map1,map2)));
		
		
		map1.put("samekey_1", "differentvalue_A");
		map2.put("samekey_1", "differentvalue_B");
		Map<String,String> Result_Intersection=new HashMap<String,String>();
		
		
		//Map1 size 等于 Map2 size; key有交集,value全不等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
		
		map1.put("samekey_2", "samevalue_2");
		map2.put("samekey_2", "samevalue_2");
		Result_Intersection.put("samekey_2", "samevalue_2");
		
		//Map1 size 等于 Map2 size; key有交集,value部分等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
		
		
		map1.put("samekey_1", "samevalue_1");
		map2.put("samekey_1", "samevalue_1");
		Result_Intersection.put("samekey_1", "samevalue_1");
		//Map1 size 等于 Map2 size; key有交集,value全等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
		
		
	}
	// Testcase8: map1<map2
	//Map1 size 小于 Map2 size;key无交级
	//Map1 size 小于 Map2 size; key有交集,value全不等
	//Map1 size 小于Map2 size; key有交集,value部分等
	//Map1 size 小于 Map2 size; key有交集,value全等

	//同case6 逻辑 交换 map1 与map2 顺序
	@Test
	public void testReturnMapWhenMap1IsASubColection() {
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("moreKeyThanMap2_A", "moreValueThanMap2_A");
		map2.put("moreKeyThanMap2_B", "moreValueThanMap2_B");
		map2.put("moreKeyThanMap2_C", "moreValueThanMap2_C");
		map2.put("samekey_1", "samevalue_1");
		map2.put("samekey_2","samevalue_2");
		map2.put("samekey_3","samevalue_3");
	
		Map<String,String> map1=new HashMap<String,String>();
		map2.put("moreKeyThanMap1_A", "moreKeyThanMap1_A");
		
		Map<String,String> Result_NoIntersection=new HashMap<String,String>();
		
		//Map1 size 小于 Map2 size;key无交集
		assertThat(Result_NoIntersection, is(diffMap(map1,map2)));
		
		
		Map<String,String> Result_Intersection=new HashMap<String,String>();
		map1.put("samekey_1","Nosamevalue_3");
		//Map1 size 小于 Map2 size; key有交集,value全不等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
		
		
		map1.put("samekey_2","samevalue_2");
		Result_Intersection.put("samekey_2","samevalue_2");
		//Map1 size 小于 Map2 size; key有交集,value部分等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
		
		

		map1.put("samekey_1","samevalue_1");
		Result_Intersection.put("samekey_1","samevalue_1");
		//Map1 size 小于 Map2 size; key有交集,value全等
		assertThat(Result_Intersection, is(diffMap(map1,map2)));
	
		
	}
	
	// Testcase9: map1 与map2 内容完全相同
	@Test
	public void testReturnmap1WhenMap1Map2Same() {
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("key_A", "value_A");
		map1.put("key_B", "value_B");
		map1.put("key_C", "value_C");
		
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("key_A", "value_A");
		map2.put("key_C", "value_C");
		map2.put("key_B", "value_B");
		
		assertThat(map1, is(diffMap(map1,map2)));
		
	}
}
