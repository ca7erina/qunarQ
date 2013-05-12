package com.qunar.interview.qunarQ.q1;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.*;


import com.qunar.interview.qunarQ.q1.entity.SingleCItySearchData;
import com.qunar.interview.qunarQ.q1.entity.TestCfg;

/*
 * 1、	Qunar机票搜索场景
1)	访问Qunar机票首页http://flight.qunar.com，选择“单程”，输入出发、到达城市，选择today+7日后的日期，点“搜索”，跳转到机票单程搜索列表页。
2)	在列表页停留1分钟，至到页面上出现“搜索结束”。
3)	如果出现航班列表，对于出现“每段航班均需缴纳税费”的行随机点选“订票”按钮，在展开的列表中会出现“第一程”、 “第二程”；对于没有出现“每段航班均需缴纳税费”的行随机点选“订票”按钮，在展开的列表底部中会出现“报价范围”
4)	如果不出现航班列表，则页面会出现“该航线当前无可售航班”

2、	请使用maven创建java工程，引入Selenium框架，编写WebUI代码，实现上述人工操作和验证。要求能随机验证100个城市对的3个月内的任意搜索条件。
 */
public class CitySearchTest {

	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private TestCfg cfg= new TestCfg();
	private long start=0;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://flight.qunar.com/";
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		start=System.currentTimeMillis();
		
		
	}
	@Test
	public void  testCitySearch() throws Exception{
		
		Map<Integer,SingleCItySearchData> cityPairs= cfg.getCityPairs();
		
		for (int i=0;i<cityPairs.size();i++){
			System.out.println("----------------------\ntest : "+i );
			
			SingleCItySearchData data=cityPairs.get(i);
			System.out.println(new Yaml().dump(data));
			testCitySearch(data);
			System.out.println("\n\n");
		}
		
		
	}
	public void testCitySearch(SingleCItySearchData data) throws Exception {
		
		driver.get(baseUrl);
		
		WebElement fromCity=driver.findElement(By.xpath("//*[@class='searchBox flightSearch']/descendant::input[@name='fromCity']"));
		fromCity.clear();
		fromCity.sendKeys(data.getFromCity());
		
		WebElement tocity=driver.findElement(By.xpath("//*[@class='searchBox flightSearch']/descendant::input[@name='toCity']"));
		tocity.clear();
		tocity.sendKeys(data.getToCity());
		
		WebElement fromDate=driver.findElement(By.xpath("//*[@class='searchBox flightSearch']/descendant::input[@name='fromDate']"));
		fromDate.clear();
		fromDate.sendKeys(data.getDate());
		
		WebElement searchButton=driver.findElement(By.xpath("//*[@class='searchBox flightSearch']/descendant::button[@class='searchButton ralignbtn']"));
		searchButton.click();
		
		System.out.println("test1:search over "+printTime());
		
		shouldTicketsListLoad();
		
		if(isTicketsExist()){
			
			shouldTicketsListShow();
			
		}else{
			shouldNoTicketsShow();
		}
		System.out.println("test done 。。。。。。。。。。。。。。！ \n");
	}

	//@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	
	
	public void shouldTicketsListLoad() throws Exception {
		Thread.sleep(35000);
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*搜索结束[\\s\\S]*$"));
	//	System.out.println("test2:ticketslist load over (30s)  "+printTime());
	}

	public void shouldTicketsListShow() throws Exception {
	//	System.out.println("...Test tickets list started.... "+printTime());
		List<WebElement> transferTicketsBookButtons = new ArrayList<WebElement>();
		List<WebElement> directTicketBookButtons = new ArrayList<WebElement>();
		List<WebElement> ticketLines = driver.findElements(By.xpath("//*[@class='b_avt_lst']"));
	
	//	System.out.println("shouldTicketsListShow: before tickets'for  "+printTime() );
		System.out.println("ticketsLines size:  "+ticketLines.size() );
	//	int count=0;
		for (WebElement e : ticketLines) {
			Boolean ifTransder=ifFlightTransfer(e);
			if (ifTransder) {
				
				transferTicketsBookButtons.add(e.findElement(By.className("btn_book")));
			} else {
				directTicketBookButtons.add(e.findElement(By.className("btn_book")));
			}
		//	System.out.println("count:  "+count++ );
		}

	//	System.out.println("transfer tickets: " + transferTicketsBookButtons.size());
	//	System.out.println("direct tickets: " + directTicketBookButtons.size());
	
		int transferTicket=transferTicketsBookButtons.size() ;
		
		if (transferTicket != 0) {
			shouldTransferiTcketsShowAfterClick(transferTicketsBookButtons);
		}else  {
			shouldTransferiTcketsShowAfterClick(directTicketBookButtons);
		}
		System.out.println("shouldTicketsListShow: after tickets'for  "+printTime() );
	}

	public void shouldNoTicketsShow() throws Exception {

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*该航线当前无可售航班[\\s\\S]*"));
		
		System.out.println("Test : No tickets notice loaded  "+printTime());	

	}

	public void shouldNoTransferTicketsShowAfterClick(List<WebElement> directTicketsClicks)throws Exception {
		ClickRandomTicketBookButton(directTicketsClicks);
		WebDriverWait wait = new WebDriverWait(driver, 2);
		System.out.println(By.cssSelector("div.qvt_col_more.qvt_col_more_hover").toString());
		assertTrue(wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("div.qvt_col_more.qvt_col_more_hover"), "报价范围")));
		System.out.println("test: singleTicketPage loaded  "+printTime());
	}

	public void shouldTransferiTcketsShowAfterClick(List<WebElement> transferTicketsClicks) throws Exception {
		ClickRandomTicketBookButton(transferTicketsClicks);
		Thread.sleep(5000);
		assertTrue(driver.findElement(By.cssSelector("body")).getText().matches("^[\\s\\S]*每段航班需分别缴纳税费[\\s\\S]*"));
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*第一程[\\s\\S]*$"));
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*第二程[\\s\\S]*$"));
	//	System.out.println("test: MultiTicketPage loaded"+printTime());
	}
		
	private boolean isTicketsExist() throws Exception {
	//	System.out.println("...does ticket listed?......."+printTime());
		boolean noTicketInfo=isElementPresent(By.xpath("//*[@id='hdivResultPanel']/div/p/span[@class='textRed']"));
		if(noTicketInfo){
			return false;
			
		}
	//	System.out.println("Ticket List is not empty ! "+printTime());
		return true;
	}
	private boolean ifFlightTransfer(WebElement ticketline) throws Exception {
		boolean ifTransfer=isWebElementExist(ticketline,By.cssSelector("div.avt_trans"));

		if (ifTransfer){
			WebElement transferInfo=ticketline.findElement(By.cssSelector("div.avt_trans")).findElement(By.cssSelector("div.avt_column_sp")).findElement(By.cssSelector("p"));
			
			boolean matchTextInfo=transferInfo.getText().matches("^[\\s\\S]*每段航班均需缴纳税费[\\s\\S]*$");
			if(matchTextInfo){
	//			System.out.println(" transfer!");
				return true;
			}
		
		}
	//	System.out.println(" no transfer !");
		return false;
	}
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	private boolean isWebElementExist(WebElement element, By selector) {
		try {
			element.findElement(selector);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	

	
	private void ClickRandomTicketBookButton(List<WebElement> ticketLinks) {
		Random random = new Random();
		int rd = random.nextInt(ticketLinks.size());
		ticketLinks.get(rd).click();
	//	System.out.println("No." + rd + " item clicked \n wait for page loaded  "+printTime());

	}
	


	private String printTime(){
		
		SimpleDateFormat sdf= new SimpleDateFormat("mm:ss"); 
		Date d= new Date(System.currentTimeMillis()-start);
		return sdf.format(d);
	}
	


//private String closeAlertAndGetItsText() {
//	try {
//		Alert alert = driver.switchTo().alert();
//		if (acceptNextAlert) {
//			alert.accept();
//		} else {
//			alert.dismiss();
//		}
//		return alert.getText();
//	} finally {
//		acceptNextAlert = true;
//	}
//}
	
	 @Rule
	  public TestWatcher testWatcher = new TestWatcher() {
	    protected void failed(Throwable e, Description description) {
	      System.out.println("" + description.getDisplayName() + " failed " + e.getMessage());
	      super.failed(e, description);
	    }

	  };

}
