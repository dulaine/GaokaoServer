package com.baizhou.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.msg.MsgTypeEnum;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static String doGet(String url) {
		return doGet(url, null, null);
	}
	/**
	 * 执行Get方法, 返回JSON
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	public static JSONObject doGetJson(String url, Map<String, String> param) {
		return doGetJson(url, param, null);
	}

	/**
	 * 执行Get方法, 返回JSON
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	public static JSONObject doGetJson(String url, Map<String, String> param, Map<String, String> headers) {
		String json = doGet(url, param, headers);
		return JSON.parseObject(json);
	}

	/**
	 * 执行HTTP GET 返回String
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doGet(String url, Map<String, String> param,Map<String, String> header) {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			if(header != null){
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
					httpGet.addHeader(key, param.get(key));
				}
			}


			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			} else {
				throw new ClientProtocolException("unexpected response status: " + status);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("error: " + e.getMessage());
			}
		}
		return resultString;
	}


	/**
	 * post表格form
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, HTTP.UTF_8);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
		} finally {
			try {
				if (response != null) response.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("error: " + e.getMessage());
			}
		}

		return resultString;
	}

	/**
	 * post 字节数据
	 * @param url
	 * @param msgTypeEnum
	 * @param bytes
	 * @return
	 */
	public static byte[] doPostProto(String url, MsgTypeEnum msgTypeEnum, byte[] bytes) {
//		ByteBuf result = Unpooled.buffer();
//		result.writeInt(msgTypeEnum.getMsgId());
//		result.writeBytes(bytes);

		ByteData data = new ByteData();
		data.WriteInt(msgTypeEnum.getMsgId());
		data.WriteBytes(bytes);


		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		byte[] resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
//			httpPost.setHeader("Authorization", "Basic " + auth);
			//COR SetCORHeader
			httpPost.setHeader(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_METHODS, "POST");
			httpPost.setHeader(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			httpPost.setHeader(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.Names.CONTENT_TYPE);

			// 创建请求内容
			ByteArrayEntity entity = new ByteArrayEntity(data.ToBytes(), ContentType.APPLICATION_OCTET_STREAM);
			httpPost.setEntity(entity);

			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
		} finally {
			try {
				response.close();
				return resultString;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("error: " + e.getMessage());
			}
		}

		return resultString;
	}


	public static String doPostFile(String url, String fileparam, File file, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);

//			MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
//			httpPost.addHeader("Authorization", "11222233333");//头部放文件上传的head可自定义
//			//builder.addTextBody("name", "张三"); 汉字会乱码 需要用下面的方法处理
//			ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
//			StringBody stringBody = new StringBody("李四5",contentType);
//			builder.addPart("name", stringBody);

//			builder.addBinaryBody("photo", file);//其余参数，可自定义


//			builder.addTextBody("subject_type", "1");
//			builder.addTextBody("start_time", "1662691418");
//			builder.addTextBody("end_time", "1662720218");

			// 创建参数列表
			if (param != null) {
				MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
//				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
//					paramList.add(new BasicNameValuePair(key, param.get(key)));
					builder.addTextBody(key, param.get(key));
				}
				// 模拟表单
//				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, HTTP.UTF_8);

				builder.addBinaryBody(fileparam, file);//其余参数，可自定义

				httpPost.setEntity(builder.build());
			}

			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
		} finally {
			try {
				if (response != null) response.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("error: " + e.getMessage());
			}
		}

		return resultString;
	}

	/**
	 * postJason请求
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPost(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);

			// 判断返回状态是否为200
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			} else {
				throw new ClientProtocolException("do post unexpected response status: " + status);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("error: " + e.getMessage());
			}
		}

		return resultString;
	}

	public static String doPost(String url) {
		return doPost(url, "");
	}
	/**
	 * 发送post请求, 返回json格式信息
	 * @param url
	 * @param json
	 * @return
	 */
	public static JSONObject doPostJson(String url, String json) {
		String jason = doPost(url, json);
		return JSON.parseObject(jason);
	}

	/**
	 * @param path
	 * @param postContent
	 * @return String
	 * @throws
	 * @author tianyh
	 * @Description
	 */
	public static String sendSmsByPost(String path, String postContent) {
		URL url = null;
		try {
			url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
			httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");

//			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
//			printWriter.write(postContent);
//			printWriter.flush();

			httpURLConnection.connect();
			OutputStream os = httpURLConnection.getOutputStream();
			os.write(postContent.getBytes("UTF-8"));
			os.flush();

			StringBuilder sb = new StringBuilder();
			int httpRspCode = httpURLConnection.getResponseCode();
			if (httpRspCode == HttpURLConnection.HTTP_OK) {
				// 开始获取数据
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				return sb.toString();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String doPostFiles(String url, List<String> fileparam, List<File> file, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);

//			MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
//			httpPost.addHeader("Authorization", "11222233333");//头部放文件上传的head可自定义
//			//builder.addTextBody("name", "张三"); 汉字会乱码 需要用下面的方法处理
//			ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
//			StringBody stringBody = new StringBody("李四5",contentType);
//			builder.addPart("name", stringBody);

//			builder.addBinaryBody("photo", file);//其余参数，可自定义


//			builder.addTextBody("subject_type", "1");
//			builder.addTextBody("start_time", "1662691418");
//			builder.addTextBody("end_time", "1662720218");

			// 创建参数列表
			if (param != null) {
				MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
//				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
//					paramList.add(new BasicNameValuePair(key, param.get(key)));
					builder.addTextBody(key, param.get(key));
				}
				// 模拟表单
//				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, HTTP.UTF_8);

				for (int i = 0; i < fileparam.size(); i++) {
					builder.addBinaryBody(fileparam.get(i), file.get(i));//其余参数，可自定义
				}


				httpPost.setEntity(builder.build());


				// 设置连接超时和Socket超时
				int connectTimeout = 5000 * 60*60*10; // 连接超时时间5秒 = 15h
				int socketTimeout = 10000; // Socket超时时间10秒
				RequestConfig config = RequestConfig.custom()
						.setConnectTimeout(connectTimeout)
//						.setSocketTimeout(socketTimeout)
						.build();

				httpPost.setConfig(config);
			}


			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		}
		catch (HttpHostConnectException e) {
			resultString = "error";
			e.printStackTrace();
			System.out.println("HttpHostConnectException : " + e.getMessage());
		}
		catch (Exception e) {
			resultString = "error";
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());
		} finally {
			try {
				if (response != null) response.close();
			} catch (IOException e) {
				resultString = "error";
				e.printStackTrace();
				System.out.println("error: " + e.getMessage());
			}
		}

		return resultString;
	}
}
