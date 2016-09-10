package com.itheima.sexygirl;

import android.app.Application;

public class SexyGirlApplication extends Application {
	
	/**
	 * 初始化应用层级类或者变量
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		//getActivity()，  MainActivity.this, getContext
		//应用层级的上下文用来处理应用层级的类或者变量
		NetworkManager.init(getApplicationContext());
	}

}
