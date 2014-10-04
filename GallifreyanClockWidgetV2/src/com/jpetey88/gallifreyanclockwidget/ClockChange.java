package com.jpetey88.gallifreyanclockwidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class ClockChange extends BroadcastReceiver {

	private static int clickCount = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("com.jpetey88.intent.action.CHANGE_FORMAT")){
			Log.d("joshy", "clicked");
			SharedPreferences prefs = context.getSharedPreferences("prefs", 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putLong("ClockMode", System.currentTimeMillis() + Long.valueOf(5000).longValue());
            edit.commit();
            
			ClockProvider.updateAppWidget(context, AppWidgetManager.getInstance( context ), intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,0));
			
			
		//	updateWidgetPictureAndButtonListener(context);
		}
	}
	

	
}