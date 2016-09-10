package com.itheima.sexygirl;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

public class NetworkManager {
	
	//一个app只维护一个请求队列
	private static RequestQueue mRequestQueue;
	
	//一个app只维护一个ImageLoader
	private static ImageLoader mImageLoader;
	
	private static int CACHE_SIZE = (int) (Runtime.getRuntime().freeMemory() / 4);
	
	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mRequestQueue, new ImageLruCache(CACHE_SIZE));
	}
	
	
	public static void sendRequest(Request<?> request) {
		//将请求加入全局的请求队列
		mRequestQueue.add(request);
	}
	
	
	/**
	 * K 键值 图片url
	 * V 值  对应的图片
	 *
	 */
	public static class ImageLruCache extends LruCache<String, Bitmap> implements ImageCache{

		/**
		 * 
		 * @param maxSize 缓存的最大大小
		 */
		public ImageLruCache(int maxSize) {
			super(maxSize);
		}
		
		/**
		 * 返回缓存图片数据的大小
		 */
		@Override
		protected int sizeOf(String key, Bitmap value) {
//			return value.getByteCount();
			return value.getRowBytes() * value.getHeight();
		}

		/**
		 * 从lru缓存中获取url对应的图片
		 */
		@Override
		public Bitmap getBitmap(String url) {
			return get(url);
		}

		/**
		 * 把图片存入缓存
		 */
		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			put(url, bitmap);
		}
		
	}
	
	public static ImageLoader getImageLoader() {
		return mImageLoader;
	}

}
