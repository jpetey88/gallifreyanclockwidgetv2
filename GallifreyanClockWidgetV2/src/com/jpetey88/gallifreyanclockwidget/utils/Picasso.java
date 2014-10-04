package com.jpetey88.gallifreyanclockwidget.utils;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.util.LruCache;
import android.widget.RemoteViews;

import com.jpetey88.gallifreyanclockwidget.R;

public class Picasso {
	private Context context = null;
	private static LruCache<String, Bitmap> mMemoryCache;
	public Picasso(Context context) {
		this.context = context;
	}
	public static void invalidateCache(){
		mMemoryCache = null;
	}
	
	public  Bitmap getCachedImage(int digit){
		if(mMemoryCache == null){
			SharedPreferences prefs = context.getSharedPreferences("prefs", 0);
			int colorpicked = prefs.getInt("NewColor", Color.BLUE);
			prefs = null;
			generateImages(colorpicked);
			
		}
		Log.d("joshy","my size is"+mMemoryCache.size());
		return mMemoryCache.get(""+digit);
			
	}
	public void generateImages(int argbColor){
	
		if(mMemoryCache == null){
			
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
			final int cacheSize = maxMemory;
			  mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			        @Override
			        protected int sizeOf(String key, Bitmap bitmap) {
			            // The cache size will be measured in kilobytes rather than
			            // number of items.
			            return bitmap.getByteCount() / 1024;
			        }
			    };
		}
		TypedArray arr = context.getResources().obtainTypedArray(R.array.baseImages);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTargetDensity = 120;
		for(int i = 0 ; i < arr.length();i++){
			Bitmap map = BitmapFactory.decodeResource(context.getResources(), arr.getResourceId(i , R.drawable.base0), options);
			
			Bitmap newMap = transform(map,argbColor);
			//map.recycle();
			Log.d("joshy","putting in "+i +" " + newMap.getByteCount());
			mMemoryCache.put(""+i, newMap);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		 * use this later future josh the future rests with you to dynamically switch resources to generate
		 * 
		 * 
		 * 	Class res = R.array.class;
		 int drawableId = 0;
		    Field field;
			try {
				field = res.getField(viewResource);
				drawableId = field.getInt(null);
				
				//Log.d("joshy", "I got " + drawableId);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
		
		
		
		
		
		TypedArray arr = context.getResources().obtainTypedArray(
				drawableId);

		int resource = 0;
		*/
	}
	

	public void paintNumber(RemoteViews view, int position, int timeDigit) {
		 paint(timeDigit,view,position);
	
	
		
	   
	}

	public void drawResource(RemoteViews views, int posId, int resourceId) {
		views.setImageViewResource(posId, resourceId);

	}

	public void paint(int number,RemoteViews view, int position) {
		
		SharedPreferences prefs = context.getSharedPreferences("prefs", 0);
		int colorpicked = prefs.getInt("NewColor", Color.BLUE);
		long timer = prefs.getLong("ClockMode",Long.valueOf(0));
		//Log.d("joshy", "I got this time left" + timer +"my time is "+System.currentTimeMillis());
		
		
		
		
		if(timer != 0 && timer >= System.currentTimeMillis()){
		//	Log.d("joshy", "I got this");
			//view.setTextViewText(position, number+"");
			 Bitmap dest = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
			 Canvas cs = new Canvas(dest);
			    Paint tPaint = new Paint();
			    tPaint.setTextSize(35);
			    tPaint.setColor(Color.BLUE);
			    tPaint.setStyle(Style.FILL);
			   //cs.drawBitmap(src, 0f, 0f, null);
			    float height = tPaint.measureText("yY");
			    float width = tPaint.measureText(""+number);
			    float x_coord = (100 - width)/2;
			    cs.drawText(""+number, x_coord, height+15f, tPaint);
			 view.setImageViewBitmap(position, dest);
			 return;
		}
	
				 Bitmap map = getCachedImage(number);
				 Log.d("joshy","getting number" + number);
				 Log.d("joshy", ""+map.getHeight());
		
		view.setImageViewBitmap(position,map);
	}
	
	
	public static Bitmap transform(Bitmap src, int argbcolorbra) {
		 
		Bitmap map = src.copy(src.getConfig(), true);
		Paint pnt = new Paint();
		  Log.d("joshy", "making bitmap");
		    Canvas myCanvas = new Canvas(map );

		    int myColor = src.getPixel(0, 0);

		    // Set the colour to replace.
		    ColorFilter filter = new LightingColorFilter(myColor, argbcolorbra );
		    pnt.setColorFilter(filter);

		    // Draw onto new bitmap. result Bitmap is newBit
		    myCanvas.drawBitmap(map,0,0, pnt);
		    return map;
		
	
	}
	
	
	

}
