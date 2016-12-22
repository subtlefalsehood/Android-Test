package com.training.network.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.LOCATION_SERVICE;

/**
 * 快速获取Android系统服务的帮助类
 * @author yanglei
 */
public final class SystemUtils {
	/**
	 * 得到系统服务
	 * @param context Context
	 * @param name 服务名
	 * @param <T> T
	 * @return 系统服务
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Context context, String name) {
		return (T) context.getSystemService(name);
	}
	
	/**
	 * 得到网络连接服务
	 * @param context Context
	 * @return ConnectivityManager
	 */
	public static ConnectivityManager getConnectivityManager(Context context) {
		return get(context,CONNECTIVITY_SERVICE);
	}
	

	/**
	 * 得到提供位置信息服务的管理类.
	 * @param context Context
	 * @return LocationManager
	 */
	public static LocationManager getLocationManager(Context context) {
		return get(context,LOCATION_SERVICE);
	}
	
	public static boolean isGPSEnabled(Context context)
	{
		 LocationManager locationManager = getLocationManager(context);
		 return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	/**
	 * 得到Activity的管理类.
	 * @param context Context
	 * @return ActivityManager
	 */
	public static ActivityManager getActivityManager(Context context) {
		return get(context,ACTIVITY_SERVICE);
	}
	/**
	 * 获取Telephony管理类.
	 * @param context Context
	 * @return TelephonyManager
	 */
	public static TelephonyManager getTelephoneManager(Context context){
		return get(context,Context.TELEPHONY_SERVICE);
	}
	
	/**
	 * @param context Context
	 * @return 网络是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = getConnectivityManager(context);
		NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
		if ((networkInfo != null) && (networkInfo.isConnectedOrConnecting())) {
			return true;
		}
		return false;

	}
	
	/**
	 * @param context 上下文件
	 * @return 设备ID
	 */
	public static String getDeviceId(Context context){
		try {
			return getTelephoneManager(context).getDeviceId();
		}catch (Exception e){

		}
		return "";
	}
	
	/**
	 * 检查是否存在SD卡
	 * 
	 * @return 如果存在则返回true
	 */
	public static boolean checkSDStated() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
}
