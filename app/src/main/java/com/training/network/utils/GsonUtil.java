package com.training.network.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author yanglei
 * 
 */
public class GsonUtil {
	/** Gson*/
	private static final Gson GSON_BUILDER = createGson();
	/**
	 * 创建gson对象
	 * @return
	 */
	private static Gson createGson(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		DateDeserializer ds = new DateDeserializer();
		gsonBuilder.registerTypeAdapter(Date.class, ds);
		return gsonBuilder.create();
	}
	/**
	 * 将对象转换为json串
	 * @param src 待转换对象
	 * @return
	 */
	public static String toJson(Object src){
		 return GSON_BUILDER.toJson(src);
	}
	/**
	 * 将对象转换为json串
	 * @param src 待转换对象
	 * @param typeOfSrc 对象的type类型
	 * @return
	 */
	public static String toJson(Object src, Type typeOfSrc){
		return GSON_BUILDER.toJson(src,typeOfSrc);
	}
	/**
	 * 将json串转换为对象
	 * @param json 待转换json串
	 * @param classOfT 对象的class类型
	 * @param <T> T
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static <T> T fromJson(String json, Class<T> classOfT)
			throws JsonSyntaxException {
		try {
			return GSON_BUILDER.fromJson(json, classOfT);
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 将json穿转换为对象
	 * @param json 待转换json串
	 * @param typeOfT 对象的type类型
	 * @param <T> T
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static <T> T fromJson(String json, Type typeOfT)
			throws JsonSyntaxException {
		try {
			return GSON_BUILDER.fromJson(json, typeOfT);
		} catch (Exception e) {
		}
		return null;
	}

}
