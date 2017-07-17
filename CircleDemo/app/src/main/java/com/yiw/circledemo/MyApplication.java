package com.yiw.circledemo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import java.io.File;
/**
 * 
* @ClassName: MyApplication 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午4:21:08 
*
 */
public class MyApplication extends Application {

	private static Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		//LeakCanary.install(this);

		MultiDex.install(this);
	}

	public static Context getContext(){
		return mContext;
	}


}
