package com.qunar.interview.qunarQ.q4;

import java.util.HashMap;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestMyHashMap {

	
	@Test 
	public void TestHashmap(){
		HashMap<String,String> a = new HashMap<String,String>();
		a.put("", "");
		a.put("", "123");
		assertThat(a.size(),equalTo(1));
	}
	
	//@Test
	public void happyTest(){
		MyHashMap<String,String> a= new MyHashMap<String,String>();
		a.put("abc", "123");
		a.put("cde", "123");
	
		assertThat(a.get("abc"),equalTo("123"));
		assertThat(a.get("cde"),equalTo("123"));
		
	}
	//@Test
	public void TestNullinMyHashMapStringType(){
		MyHashMap<String,String> a= new MyHashMap<String,String>();
		a.put("", "");
		a.put("", "123");
		a.put("123", "");
		a.put("","");
		a.put("123", "");
		a.put("", "");
		a.put("nihao","zaijian");
		a.put("中文", "汉字");

		assertThat(a.size(),equalTo(4));
		assertThat(a.get(""),equalTo(""));
		assertThat(a.get("123"),equalTo(""));
		assertThat(a.get("nihao"),equalTo("zaijian"));
	assertThat(a.get("中文"),equalTo("汉字"));
	
		
		
	
	}
	
	@Test
	public void TestNullinMyHashMapIntegerType(){
		MyHashMap<Integer,Integer> a= new MyHashMap<Integer,Integer>();
		a.put(122, 1123);
		a.put(0,0);
		a.put(0,1);
		a.put(1,0);
		a.put(28,99);
	
		assertThat(a.size(),equalTo(4));
		assertThat(a.get(122),equalTo(1123));
		assertThat(a.get(0),equalTo(1));
		assertThat(a.get(1),equalTo(0));
		
	}
	
}
