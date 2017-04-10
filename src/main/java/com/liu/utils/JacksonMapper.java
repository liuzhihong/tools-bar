package com.liu.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: JacksonMapper 
* @Description: jackson json工具类
* @author 汪海霖  wanghl15@midea.com.cn 
* @date 2014-5-24 上午1:15:31 
*
 */
public class JacksonMapper{
	/** **/
	private static final Logger logger = LoggerFactory.getLogger(JacksonMapper.class);
	
	private static JacksonMapper jacksonMapper = new JacksonMapper();
	/**实例化工具类 **/
    private ObjectMapper mapper;  
    /**私有化构造方法**/
    private JacksonMapper() {   
    	mapper = new ObjectMapper();  
    	mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }  
    private static final int ARRAY_MAX = 1024;
    /**
     * 恶汉单例模式
     * @return
     */
    public static ObjectMapper getInstance() {  
        return jacksonMapper.mapper;  
    } 
    /**
     * json转化为java bean
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T jsonToBean(String json,  Class<T> valueType){    
    	if(StringUtils.isNotBlank(json)){
    		try {
    			return getInstance().readValue(json, valueType);
    		} catch (JsonParseException e) {
    			logger.warn(e.getMessage(),e);
    		} catch (JsonMappingException e) {
    			logger.warn(e.getMessage(),e);
    		} catch (IOException e) {
    			logger.warn(e.getMessage(),e);
    		}
    	}
    	return null;
    }
    /**
     * java bean转化为json
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean){
    	StringWriter sw = new StringWriter();  
    	JsonGenerator gen = null;
		try {
			gen = new JsonFactory().createJsonGenerator(sw);
	    	getInstance().writeValue(gen, bean);  
	    	gen.close();
		} catch (IOException e) {
			logger.warn(e.getMessage(),e);
		}  
    	return sw.toString();
    }
    
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * @param <T>
     *
     * @param json
     * @param type
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToList(String json,Class<T> clazz) {
    	if(StringUtils.isNotBlank(json)){
    		T[] t =  (T[]) Array.newInstance(clazz, ARRAY_MAX);
            try {
                t =  (T[]) getInstance().readValue(json, t.getClass());
                List<T> list = (List<T>) Arrays.asList(t);
                return list;
            } catch (JsonGenerationException e) {
                logger.error(e.getMessage());
            } catch (JsonMappingException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
    	}
        return null;
    }
    
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
			throws Exception {
		List<Map<String, Object>> list = getInstance().readValue(jsonArrayStr,
				new TypeReference<List<T>>() {
				});
		List<T> result = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			result.add(map2pojo(map, clazz));
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T map2pojo(Map map, Class<T> clazz) {
		return getInstance().convertValue(map, clazz);
	}
    
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param t
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String listToJson(List<?> t){
        try {
            return getInstance().writeValueAsString(t);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage());
        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    
    
}
