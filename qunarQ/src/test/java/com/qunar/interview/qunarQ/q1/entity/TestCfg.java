package com.qunar.interview.qunarQ.q1.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class TestCfg {
	
	private static Integer howManyCityPair;
	private static Integer howManyMonths;
	private static Map<Integer,SingleCItySearchData> cityPairs = new HashMap<Integer,SingleCItySearchData>();
	private static List<String> cities= new ArrayList<String>();
	
	
	
	public TestCfg() {
		
	}
	static{
		
		try{
			setTestCfg();
			cities=getCities();
			setCityPairs();
			
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
	}
	
	private static List getCities() throws FileNotFoundException {
		List cities= new ArrayList();
	    Yaml yaml = new Yaml();
		cities=  (ArrayList<String>) yaml.load(new FileInputStream(new File("src/test/java/specification/china_cities.yaml")));
	    return cities;
	}
	
//	public void DataDrivenTest() throws Exception{
//		for(int i=0;i<cityPairs.size();i++){
//			SingleCItySearchData d=citypairs.get(i);
//			System.out.println(new Yaml().dump(d));
//		}
//	}

	

	public static void setTestCfg() throws IOException{
		Yaml yaml=new Yaml();
		Map<String, Integer> cfg = (Map<String, Integer>)yaml.load(new FileInputStream(new File("src/test/java/specification/test_cfg.yaml")));
		//System.out.println(cfg);  
		howManyCityPair=cfg.get("HowManyCityPair") ;
		howManyMonths=cfg.get("timePeriodByMonth");
	}

	
	public static void setCityPairs() throws Exception{
		Representer representer = new Representer();
        representer.addClassTag(SingleCItySearchData.class, new Tag("!testData"));
		Yaml yaml = new Yaml(representer);
		OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream("src/test/java/specification/cityPairs.yaml"),"utf-8");
	
		for(int i=0;i<howManyCityPair;i++){
			Random rd = new Random();
			
			SingleCItySearchData object= new SingleCItySearchData();
			String fromCity=cities.get(rd.nextInt(cities.size()));
			String toCity=cities.get(rd.nextInt(cities.size()));
			while(toCity.equals(fromCity)){
				toCity=cities.get(rd.nextInt(cities.size()));
			}
			
			object.setDate(GetfromDate(0,rd.nextInt(howManyMonths-1),rd.nextInt(30)));
			object.setFromCity(fromCity);
			object.setToCity(toCity);
		
			osw.write(yaml.dump(object));
			cityPairs.put(i, object);	
		}
		
		osw.close();
	 
	}
	
	private static String GetfromDate(int years, int months, int days) {
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());

	cal.add(Calendar.YEAR, years);// out of date after 3 year
	cal.add(Calendar.MONTH, months);// before 1 month;
	cal.add(Calendar.DATE, days);
	//System.out.println(ft.format(cal.getTime()));
	return ft.format(cal.getTime());
}


	public Integer getHowManyCityPair() {
		return howManyCityPair;
	}


	public void setHowManyCityPair(Integer howManyCityPair) {
		this.howManyCityPair = howManyCityPair;
	}


	public Integer getHowManyMonths() {
		return howManyMonths;
	}


	public void setHowManyMonths(Integer howManyMonths) {
		this.howManyMonths = howManyMonths;
	}


	public Map<Integer, SingleCItySearchData> getCityPairs() {
		return cityPairs;
	}


	public void setCityPairs(Map<Integer, SingleCItySearchData> cityPairs) {
	
		this.cityPairs = cityPairs;
	}


	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	

	
}
