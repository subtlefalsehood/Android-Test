package com.training.common.utlis;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 封装对SharedPreferences操作的工具类.
 * @author yanglei
 *
 */
public class SharedPreferencesHelper {
	/**
	 * SharedPreferences文件名
	 */
	private String name;
	/**
	 * 应用上下文
	 */
	protected Context ctx;
	/**
	 * SharedPreferences
	 */
	private SharedPreferences preferences;
    /**
     * 构造函数.
     * @param context Android上下文
     * @param name  preferences名
     */
	public SharedPreferencesHelper(Context context, String name) {
		this.ctx = context;	
		this.name=name;			
	}

	/**
	 * @return SharedPreferences
	 */
	public SharedPreferences getPreferences() {
		if(preferences==null){
			preferences=ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
		}
		return preferences;
	}	
    /**
     * 保存字符串值的配置项.
     * @param key  配置项名
     * @param value 配置项值
     */
	public void save(String key, String value){
		if(value==null){
			return;
		}
		Editor editor = getPreferences().edit();
		editor.putString(key, value);
		editor.commit();
	}
	/**
	 * 保存整型值的配置项.
     * @param key  配置项名
     * @param value 配置项值
	 */
	public void save(String key, int value){
		save(key, Integer.toString(value));
	}
	/**
	 * 保存长整型值的配置项.
     * @param key  配置项名
     * @param value 配置项值
	 */
	public void save(String key, long value){
		Editor editor = getPreferences().edit();
		editor.putLong(key, value);
		editor.commit();
	}
	/**
	 * 保存boolean值的配置项.
     * @param key  配置项名
     * @param value 配置项值
	 */
	public void save(String key, boolean value){
		save(key, Boolean.toString(value));
	}
	/**
	 * 读取配置项的值.
	 * @param key key字段
	 * @param defaultValue 默认的值
	 * @return
	 */
	public String getString(String key, String defaultValue){
		return getPreferences().getString(key, defaultValue);
	}
	/**
	 * 读取配置项的值.
	 * @param key key字段
	 * @param defaultValue 默认的值
	 * @return
	 */
	public int getInt(String key, int defaultValue){
		String str=getString(key, String.valueOf(defaultValue));
		return Integer.parseInt(str);
	}
	/**
	 * 读取长整型的值.
	 * @param key key字段
	 * @param defaultValue 默认的值
	 * @return
	 */
	public long getLong(String key, long defValue){
		return getPreferences().getLong(key, defValue);
	}
	/**
	 * 读取配置项的值.
	 * @param key key字段
	 * @param defaultValue 默认的值
	 * @return
	 */
	public boolean getBoolean(String key, boolean defaultValue){
		String str=getString(key, String.valueOf(defaultValue));
		return Boolean.parseBoolean(str);
	}

}
