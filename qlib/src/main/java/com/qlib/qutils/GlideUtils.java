package com.qlib.qutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.io.File;

public class GlideUtils {
	private static Context mContext;

	private GlideUtils() {
	}

	public static GlideUtils getInstance(Context context) {
		mContext = context;
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder{
		private static final GlideUtils INSTANCE = new GlideUtils();
	}


	/** 显示网络图片 */
	public void displayNetImage(ImageView imageView, String url) {
		if (TextUtils.isEmpty(url))
			return;
		//Picasso.with(mContext).load(url).placeholder(R.drawable.bg_default).error(R.drawable.bg_default)
			//	.into(imageView);

		Glide.with(mContext).load(url).into(imageView);
	}

	/** 显示网络图片 */
	public void displayNetImage(ImageView imageView, String url, int width, int height) {
		if (TextUtils.isEmpty(url))
			return;
		Glide.with(mContext).load(url).into(imageView);
	}

	/** 显示SD卡上的图片 */
	public void displayLocalFileImage(ImageView imageView, String path) {
		File file = new File(path);
		//加载图片
		Glide.with(mContext).load(file).into(imageView);
	}

	// 加载base64格式图片
	public void addBase64Image(String strBase64, ImageView imageView) {
		//String base64 = "data:image/png;base64," + strBase64;
		byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		imageView.setImageBitmap(decodedByte);
	}

	/** 获取SD缓存文件的地址 */
	public String getCacheFilePath(String path) {
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		File file = new File(path);
		if (file != null && file.exists()) {
			return file.getAbsolutePath();
		}
		return null;
	}

	// 加载本地GIF图片
//	public void loadLocalGifImage(ImageView ivGif,  int resId) {
//		Glide.with(mContext).load(resId)
//				.diskCacheStrategy(DiskCacheStrategy.ALL).into(ivGif);
//	}
}
