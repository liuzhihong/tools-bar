package com.liu.utils;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientUtil {
	
	
	   private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	   /**
	    * @throws IOException 
	    * @throws ClientProtocolException 
	    * @author chejy 
	    * @param reqURL
	    * @param decodeCharset
	    * @return String    返回类型
	    * @throws
	    */
	   public static String sendGetRequest(String reqURL, String decodeCharset) throws ClientProtocolException, IOException{
	       String responseContent = null; //响应内容
	       RequestConfig defaultRequestConfig = RequestConfig.custom()
	    		    .setSocketTimeout(1000)
	    		    .setConnectTimeout(1000)
	    		    .setConnectionRequestTimeout(1000)
	    		    .setStaleConnectionCheckEnabled(true)
	    		    .build();
	       CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
	       HttpGet httpGet = new HttpGet(reqURL);   
	       try{
	           HttpResponse response = httpClient.execute(httpGet); 
	           HttpEntity entity = response.getEntity();          
	           if(null != entity){
	               responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
	           }
	       }finally{
	    	   try {
	    		   httpClient.close();
				} catch (IOException e) {
					httpClient=null;
					logger.error("httpclient get资源关闭异常信息:{},异常栈{}",e.getMessage(),e);
					e.printStackTrace();
				}//关闭连接,释放资源
	       }
	       return responseContent;
	   }
	   
	   /**
	    * 发送HTTP_POST请求
	    * @see 该方法会自动关闭连接,释放资源
	    * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	    * @param reqURL        请求地址
	    * @param sendData      请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,传入该参数中
	    * @param isEncoder     请求数据是否需要encodeCharset编码,true为需要
	    * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	    * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	    * @return 远程主机响应正文
	 * @throws IOException 
	 * @throws ClientProtocolException 
	    */
	   public static String sendPostRequest(String reqURL, String sendData) throws ClientProtocolException, IOException{
	       String responseContent = null;
	       RequestConfig defaultRequestConfig = RequestConfig.custom()
	    		    .setSocketTimeout(1000)
	    		    .setConnectTimeout(1000)
	    		    .setConnectionRequestTimeout(1000)
	    		    .setStaleConnectionCheckEnabled(true)
	    		    .build();
	       CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
	       HttpPost httpPost = new HttpPost(reqURL);
	       //httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
	       try{
	           httpPost.setEntity(new StringEntity(sendData));
	           HttpResponse response = httpClient.execute(httpPost);
	           HttpEntity entity = response.getEntity();
	           if (null != entity) {
	               responseContent = EntityUtils.toString(entity,"UTF-8");
	           }
	       }finally{
	    	   try {
				httpClient.close();
			} catch (IOException e) {
				httpClient=null;
				logger.error("httpclient post资源关闭异常信息:{},异常栈{}",e.getMessage(),e);
				e.printStackTrace();
			}
	       }
	       return responseContent;
	   }
	   
	   
	   
	   
	   
	   /**
	   * @author chejy 
	   * @Description: 
	   * @param reqURL
	   * @param params
	   * @param encodeCharset
	   * @param decodeCharset
	   * @return String    返回类型
	   * @throws
	    */
	   public static String sendPostSSLRequest(String reqURL,String sendData){
	       String responseContent = "";
	       //无论其证书是否经权威机构的验证，只要实现了接口X509TrustManager的类MyX509TrustManager信任该证书。
	       X509TrustManager xtm = new X509TrustManager(){
	           //该方法检查客户端的证书，若不信任该证书则抛出异常。由于我们不需要对客户端进行认证，因此我们只需要执行默认的信任管理器的这个方法。JSSE中，默认的信任管理器类为TrustManager。
	           public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	           //　该方法检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
	           public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	           //返回受信任的X509证书数组。
	           public X509Certificate[] getAcceptedIssuers() {return null;}
	       };
	       
	       CloseableHttpClient httpClient = null;
  	       try {
  	    	 //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext 
  		       SSLContext ctx = SSLContext.getInstance("TLS");
  		       //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
  		       ctx.init(null, new TrustManager[]{xtm}, new SecureRandom());
  		       //创建SSLSocketFactory 
  		       SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx);
  		       //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上 
  		       httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
	           HttpPost httpPost = new HttpPost(reqURL);
	           httpPost.setEntity(new StringEntity(sendData));
	           HttpResponse response = httpClient.execute(httpPost);
	           HttpEntity entity = response.getEntity();
	           if (null != entity) {
	               responseContent = EntityUtils.toString(entity,"UTF-8");
	           }
	       } catch (Exception e) {
	           logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
	       } finally {
	    	   try {
				httpClient.close();
			} catch (IOException e) {
				httpClient=null;
				logger.error("httpclient post资源关闭异常信息:{},异常栈{}",e.getMessage(),e);
				e.printStackTrace();
			}
	       }
	       return responseContent;
	   }
	   
	   
	   
	   
	   /**
	   * @author chejy 
	   * @Description: TODO(这里用一句话描述这个方法的作用)
	   * @param reqURL
	   * @param params
	   * @param encodeCharset
	   * @param decodeCharset
	   * @return String    返回类型
	   * @throws
	    */
	   public static String sendPostSSLRequest2(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset){
	       String responseContent = "";
	       CloseableHttpClient httpClient = null;
	       try {
		       SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
		           //信任所有
		           public boolean isTrusted(X509Certificate[] chain,
		                   String authType) throws CertificateException {
		               return true;
		           }
		       }).build();
		       
		       //创建SSLSocketFactory 
		       SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
		       //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上 
		       httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
	           HttpPost httpPost = new HttpPost(reqURL);
	           List<NameValuePair> formParams = new ArrayList<NameValuePair>();
	           for(Map.Entry<String,String> entry : params.entrySet()){
	               formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	           }
	           httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
	           HttpResponse response = httpClient.execute(httpPost);
	           HttpEntity entity = response.getEntity();
	           if (null != entity) {
	               responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
	           }
	       } catch (Exception e) {
	           logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
	       } finally {
	    	   try {
				httpClient.close();
			} catch (IOException e) {
				httpClient=null;
				logger.error("httpclient post资源关闭异常信息:{},异常栈{}",e.getMessage(),e);
			}
	       }
	       return responseContent;
	   }
	
	
}