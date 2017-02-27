package com.training.common.utlis;

import android.content.Context;
import android.util.TypedValue;
import android.view.WindowManager;

public class DimenUtils {
	/**
	 * 方形区域
	 */
	public static class SquareArea {
		/** 宽度 */
		private int width;
		/** 高度 */
		private int height;

		public final int getWidth() {
			return width;
		}

		public final void setWidth(int width) {
			this.width = width;
		}

		public final int getHeight() {
			return height;
		}

		public final void setHeight(int height) {
			this.height = height;
		}
	}

	/**
	 * 获取屏幕的长和宽
	 * @param context 上下文
	 * @return 屏幕区域
	 */
	public static SquareArea getWindowDisplay(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();

		SquareArea mArea = new SquareArea();
		mArea.setWidth(width);
		mArea.setHeight(height);
		return mArea;
	}
	
	/**
	 * dip转化为px
	 * @param context 上下文
	 * @param dip 待转化dip值
	 * @return px值
	 */
	public static float dip2px(Context context, float dip)
	{
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
	}
	
	/** 
     * 将px值转换为dip或dp值，保证尺寸大小不变 
     *  
     * @param pxValue 
     * @param scale 
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
  
    /** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param pxValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int px2sp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }  
  
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static float sp2px(Context context, float spValue) {  
    	return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }  
}
