package com.qunar.interview.qunarQ.q4;



import java.util.Arrays;





public class MyHashMap<K,V> {
	private K k;
	private V v;
	private LinkedData table[];
	private int size=0;
	private int TABLE_LENGTH=1000;
	
	MyHashMap(){
		table= new LinkedData[TABLE_LENGTH];
	}
	
	
	
    int GetIndex(K k,int length) {
    	int hashk=k.hashCode();
    		hashk^= (hashk>>> 20) ^ (hashk >>> 12);
    	int h= hashk ^ (hashk >>> 7) ^ (hashk >>> 4);
    	
    	
        return h & length-1;
    }
	
   

    public void put(K k,V v){
    	
    	
    	//如果key是空值修改直接给value值
    	if((k==null||k.equals(""))&&table[0]==null){
    		LinkedData<K,V> linkedData =new LinkedData<K,V>();
    		linkedData.setK(k);
    		linkedData.setV(v);
    		table[0]=linkedData;
    		size++;
    		
    		return;
    	}
    	
    	//如果key是空值覆盖value值
    	
    	if((k==null||k.equals(""))&&table[0]!=null){
    		LinkedData<K,V> linkedData =new LinkedData<K,V>();
    		linkedData.setK(k);
    		linkedData.setV(v);
    		table[0]=linkedData;
    	
    		return;
    	}
    	
    	LinkedData<K,V> linkedData =new LinkedData<K,V>();
		linkedData.setK(k);
		linkedData.setV(v);
		int index=GetIndex(k, table.length);
		System.out.println("put   key: "+k+"    value:"+v + "     index "+index);
		
		//当index>tablength时数组需要扩充
//		while(index>=table.length){
//			System.out.println("table longer ");
//			  LinkedData bigerTable[] = new LinkedData[table.length+10000];
//			  System.arraycopy(table, 0, bigerTable, 0, table.length);
//			  table = bigerTable;//数组的动态扩增
//		}
	
		//new key 直接添加 value
		LinkedData<K,V> tableData =table[index];
    	if(tableData==null){
    		table[index]=linkedData;
    		size++;
    		return;
    	}
    	
    	//key value 都相同,什么也不做
    	
    	if((table[index].getK()==k||table[index].getK().equals(k))&&(v.equals(table[index].getV())||table[index].getV()==v)){
    		System.out.println(table);
    		return;
    		
    		
    	}
    	//key 相同
//    	if(table[index].getK()==k||table[index].getK().equals(k)){
//    		table[index]=linkedData;
//    		return;
//    	}
    	
    	//index相同,key不同
    	
    	if(table[index].getK()!=null&&table[index].getK()!=k){
    		//index值重复,联接新值
    		for(linkedData = tableData; linkedData != null; linkedData = linkedData.getNextLinkedData()){
    	
    		
    		linkedData.setNextLinkedData(tableData);
    		table[index]=linkedData;
    		size++;
    	    }
    		
    	}

    	
    }

    public V get(K k){
    	//如果key为null,有没有value.
    	if(k==null||k.equals("")){
    		LinkedData<K,V> linkedData=table[0];
    		if(linkedData==null){
    			return null;
    		}
    		System.out.println("key 为null 找table0" );
    		return linkedData.getV();
    	}
    
    	int index=GetIndex(k, table.length);
     	
    	LinkedData<K,V> linkedData =new LinkedData<K,V>();
    	LinkedData<K,V> tableData =table[index];
    	//如果没有这个key值时
    	if(tableData==null){
    		return null;
    	}
    	
    	//在index指定的数组单元中寻找key对应的value
    	for(linkedData = tableData; linkedData != null; linkedData = linkedData.getNextLinkedData()){
    		if (linkedData.getK()==k) {
    			System.out.println("同 index下: get   key: "+k+"    value:"+linkedData.getV() + "     index "+index);
    			return linkedData.getV();
    		}
    		
    	}
    
		return null;
		
	}
    
    public  int size(){
    
    	
		return size;
    	
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
