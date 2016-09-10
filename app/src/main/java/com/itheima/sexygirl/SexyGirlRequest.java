package com.itheima.sexygirl;

import java.io.UnsupportedEncodingException;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

public class SexyGirlRequest<T> extends JsonRequest<T> {
	
	private Gson mGson;
	private Class<T> mClass;

	private static final String TAG = "SexyGirlRequest";

	/**
	 * 
	 * @param method 请求方法 GET（智慧北京只获取数据） POST
	 * @param url 请求地址
	 * @param requestBody 请求体，上传数据
	 * @param listener 网络请求成功的回调
	 * @param errorListener 网络请求失败的回调
	 */
	public SexyGirlRequest(String url, Class<T> classz,
						   Listener listener, ErrorListener errorListener) {
		super(Method.GET, url, null, listener, errorListener);
		mGson = new Gson();
		mClass = classz;
	}

	/**
	 * 在请求成功之后解析网络返回的结果，在子线程调用
	 */
	@Override
	protected Response parseNetworkResponse(NetworkResponse response) {
		Log.d(TAG, "parseNetworkResponse");
		try {
			String result = new String(response.data, PROTOCOL_CHARSET);
			Log.d(TAG, result);
			//将字符串解析成java bean
//			CategoriesBean resultBean = mGson.fromJson(result, CategoriesBean.class);
//			String beijing = resultBean.data.get(0).children.get(0).title;
			T resultBean = mGson.fromJson(result, mClass);
//			Log.d(TAG, beijing);
			//缓存相关数据，如果缓存的过期时间
			Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
			Log.d(TAG, "ttl " + cacheEntry.ttl);
			return Response.success(resultBean, cacheEntry);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
