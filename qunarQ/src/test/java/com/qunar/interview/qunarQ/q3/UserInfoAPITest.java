package com.qunar.interview.qunarQ.q3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/***
 * 下面是某平台的用户信息获取接口【供程序调用】说明，请根据说明的要素，写出测试点以及相应的测试方法。
 * 接口URL地址：http://www.xxx.com/api/userinfo.json 接口返回格式：JSON 接口请求方式：AJAX/POST
 * 接口输入参数： 字段名 类型 是否必须 示例值 描述 userName String 是 lisi 用户名 接口返回结果（所有字段，以下为Json格式）
 * //成功时返回 { "ret":true "errmsg":"success" "data": { userName:ca7erina,//用户名
 * nickName:ca7erina//昵称 address:xxx//地址 headImageUrl:http://xxx.com/1.png } }
 * 
 * //失败时返回 { "ret":false "errmsg":"数据加载错误" }
 * 
 * @author caterina
 * 
 */

public class UserInfoAPITest {

	DefaultHttpClient httpclient = new DefaultHttpClient();
	private String username = "ca7erina";
	private String nickname = "caterina";
	private String address = "tesfs,road,street,city";
	private String headImageUrl = "http://xxx.com/1.png ";

	HttpPost httpPost = new HttpPost(
			"http://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=false");

	/*
	 * testcase1: 当联接简历成功时应返回200;
	 */
	@Test
	public void testStatusCode() throws Exception {
		HttpPost httpPost = new HttpPost(
				"http://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=false");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "ca7erina"));
		HttpResponse response = httpclient.execute(httpPost);

		try {

			assertThat(response.getStatusLine().getStatusCode(),
					equalTo(HttpStatus.SC_OK));

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			httpPost.releaseConnection();
		}

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			Header head[] = httpPost.getAllHeaders();

			for (Header h : head) {

				System.out.println(h.getName() + "  ------->  " + h.getValue());
			}

		}

	}

	/*
	 * testcase2: 测试当联接建立成功时,请求成功返回的json testcase3: 测试当联接建立成功时,请求失败返回的json
	 */
	// @Test
	public void testReturnedJson() throws Exception {

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "ca7erina"));
		nvps.add(new BasicNameValuePair("password", "19870625"));
		HttpResponse response = httpclient.execute(httpPost);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			assertThat(response.getEntity().getContentType().getValue()
					.toString(), containsString("json"));

			try {
				String returnJson = EntityUtils.toString(entity);
				JSONObject jsonObject = JSONObject.fromObject(returnJson);
				String ret = jsonObject.getString("code").toString();
				if (ret.equals("true")) {
					assertThat(jsonObject.get("errmsg").toString(),
							equalTo("success"));

					JSONObject jsonData = JSONObject.fromObject(jsonObject
							.get("data"));

					assertThat(jsonData.getString("username").toString(),
							is(username));
					assertThat(jsonData.getString("nikename").toString(),
							is(nickname));
					assertThat(jsonData.getString("address").toString(),
							is(address));
					assertThat(jsonData.getString("headImageUrl").toString(),
							is(headImageUrl));

				}
				if (ret.equals("false")) {

					assertTrue(jsonObject.get("errmsg").toString(),
							equals("数据加载错误"));

				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				httpPost.releaseConnection();
			}

		}

	}

	/*
	 * testcase4:测试当参数为空时返回的json;
	 */
	public void testRequestWithEmptyParams() throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", ""));
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String returnJson = EntityUtils.toString(entity);
		JSONObject jsonObject = JSONObject.fromObject(returnJson);

		try {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				assertTrue(jsonObject.get("ret").toString(), equals("false"));
				assertTrue(jsonObject.get("errmsg").toString(),
						equals("数据加载错误"));

			}

		} finally {
			httpPost.releaseConnection();
		}

	}

	/*
	 * testcase5:测试SQLinjection; 应该有多个方法来测试sqlinjection.
	 */
	public void testRequestWithSQLInjection() throws Exception {
		HttpPost httpPost = new HttpPost("http://www.xxx.com/api/userinfo.json");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "test'or'1'='1"));
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String returnJson = EntityUtils.toString(entity);
		JSONObject jsonObject = JSONObject.fromObject(returnJson);
		try {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				assertTrue(jsonObject.get("ret").toString(), equals("false"));
				assertTrue(jsonObject.get("errmsg").toString(),
						equals("数据加载错误"));

			}
		} finally {
			httpPost.releaseConnection();
		}

	}

	/*
	 * testcase6:测试接口性能;
	 */
	public void testPreformence() throws Exception {

	}

}
