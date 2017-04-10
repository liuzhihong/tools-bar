package com.liu.utils;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.shield.authc.support.SecuredString;
import org.elasticsearch.shield.authc.support.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kdl
 * @Title
 * @Description elasticsearch搜索引擎
 *              http://es.xiaoleilu.com/010_Intro/05_What_is_it.html
 */
public class ClientBuilder {

    private static Logger logger = LoggerFactory.getLogger(ClientBuilder.class);
	
    private static String es;
    /*
	 * @Value("") private String connectUrl;
	 */

    /**
     * 静态,单例...
     */
    // private static JestClient JestClient;
    private static Client client;

    /**
     * @author chejy
     * @Description: 建立连接
     * @return JestClient 返回类型
     * @throws
     */
    public static Client getJestClient() {
		/*
		 * Settings settings = ImmutableSettings.settingsBuilder()
		 * .put("cluster.name", "elasticsearch").build();
		 */
        // 你可以设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，
        // 把集群中其它机器的ip地址加到客户端中，这样做的好处是一般你不用手动设置集群里所有集群的ip到连接客户端，
        // 它会自动帮你添加，并且自动发现新加入集群的机器
		/*
		 * Settings settings = ImmutableSettings.settingsBuilder()
		 * .put("client.transport.sniff", true).build();
		 */
        Properties properties = new Properties();
        /*StringBuffer path = new StringBuffer();
		path.append(
		        ClientBuilder.class.getClassLoader().getResource("")
		                .getPath()).append("fc_config.properties");
		InputStream inputStream = new FileInputStream(path.toString());
		properties.load(inputStream);
		inputStream.close(); // 关闭流
		String es = properties.getProperty("elasticsearch");*/
		if (client == null) {
		    if (StringUtils.isNotBlank(es) && es.equals("prod")) {
		        Settings settings = ImmutableSettings.settingsBuilder()
		                .put("cluster.name", "elasticsearch")
		                .put("shield.user", "t_admin:v0SPxQzqN^fKV4e&").build();
		        client = new TransportClient(settings)
		                .addTransportAddress(new InetSocketTransportAddress("10.169.11.248", 9300))
		                .addTransportAddress(new InetSocketTransportAddress("10.251.232.163", 9300))
		                .addTransportAddress(new InetSocketTransportAddress("10.117.59.86", 9300));
		        String token = UsernamePasswordToken.basicAuthHeaderValue("t_admin",
		                new SecuredString("v0SPxQzqN^fKV4e&".toCharArray()));
		        client.prepareSearch().putHeader("Authorization", token);
		    } else if (StringUtils.isNotBlank(es) && es.equals("stress")) {
		        // 压测、beta、开发
		        Settings settings = ImmutableSettings.settingsBuilder()
		                .put("cluster.name", "elasticsearch_HA")
		                .put("shield.user", "t_admin:123456").build();
		        client = new TransportClient(settings)
		                .addTransportAddress(new InetSocketTransportAddress("121.41.74.247",9300))
		                .addTransportAddress(new InetSocketTransportAddress("120.26.225.182",9300));
		        String token = UsernamePasswordToken.basicAuthHeaderValue(
		                "t_admin", new SecuredString("123456".toCharArray()));
		        client.prepareSearch().putHeader("Authorization", token);
		    } else {
		        Settings settings = ImmutableSettings.settingsBuilder()
		                .put("cluster.name", "my-application-1").build();
		        client = new TransportClient(settings)
		                .addTransportAddress(new InetSocketTransportAddress("10.16.72.57", 9300));
		    }
		}
		return client;
    }
}
