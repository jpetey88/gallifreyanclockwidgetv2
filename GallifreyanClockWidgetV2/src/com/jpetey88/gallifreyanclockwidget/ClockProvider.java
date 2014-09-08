package com.jpetey88.gallifreyanclockwidget;

import java.sql.Date;
import java.util.Calendar;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.Log;
import android.widget.RemoteViews;

public class ClockProvider extends AppWidgetProvider {
	public static String CLOCK_WIDGET_UPDATE = "com.jpetey88.gallifreyanclockwidget.ClockUpdate";
	public static String LOG_TAG = "JoshisSuperawesome";
	
	
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	//	Log.d(LOG_TAG, "Widget Provider disabled. Turning off timer");
    	AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createClockTickIntent(context));	
	}
	
	@Override 
	public void onEnabled(Context context) {
		super.onEnabled(context);
	//	Log.d(LOG_TAG, "Widget Provider enabled.  Starting timer to update widget every second");
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 1000, createClockTickIntent(context));
	}
	
	
	
	
	
	
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
      //  Log.println(Log.ERROR, "Redpop", "I Hate Life Im killing myself");
        // Perform this loop procedure for each App Widget that belongs to this provider
     
     //   Log.d(LOG_TAG, "Updating here");
        
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
           // Intent intent = new Intent(context, ExampleActivity.class);
           // PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the buttonx
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.digital);
           // views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
           appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
    public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		//
	if (CLOCK_WIDGET_UPDATE.equals(intent.getAction())) {
		//	Log.d("Tester", "Clock update");
			// Get the widget manager and ids for this widget provider, then call the shared
			// clock update method.
			ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	    int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
		    for (int appWidgetID: ids) {
		    	
		  //  	Log.d("Tester", "Clock update" +appWidgetID);
				updateAppWidget(context, appWidgetManager, appWidgetID);
		    	
		    }
		}
	}
    private PendingIntent createClockTickIntent(Context context) {
        Intent intent = new Intent(CLOCK_WIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
	}
    public static void updateAppWidget(Context context,	AppWidgetManager appWidgetManager, int appWidgetId) {
    	RemoteViews views = new RemoteViews(context.getPackageName(),	R.layout.digital); 
    	
    	Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(System.currentTimeMillis());
	        
	        
	        int hour = calendar.get(Calendar.HOUR);
	        int minute = calendar.get(Calendar.MINUTE);
	        int second = calendar.get(Calendar.SECOND);
	        
	        if (hour == 0)
	       		hour = 12;
	       		
	        int firstHour = hour>9?1:0;
	        int secondHour = hour %10;
	        
	        
	        int firstMinute = minute/10;
	        int secondMinute = minute %10;
	        
	        int firstSecond = second/10;
	        int secondSecond = second %10;
	       
	        
	     /*   views.setImageViewResource(R.id.pos1, getNumberResource());
	        views.setImageViewResource(R.id.pos2, R.drawable.gale2);
	        views.setImageViewResource(R.id.pos3, R.drawable.gale3);
	       views.setImageViewResource(R.id.pos4, R.drawable.gale4);
	      views.setImageViewResource(R.id.pos5, R.drawable.gale5); 
	      views.setImageViewResource(R.id.pos6, R.drawable.gale6);
	        appWidgetManager.updateAppWidget(appWidgetId, views);
	     
	        */
	        drawResource(views, R.id.pos1,getNumberResource(context, firstHour) );
	        drawResource(views,  R.id.pos2,getNumberResource(context,secondHour) );
	        drawResource(views, R.id.pos3,getNumberResource(context,firstMinute) );
	        drawResource(views,  R.id.pos4,getNumberResource(context,secondMinute) );
	        drawResource(views,  R.id.pos5,getNumberResource(context,firstSecond) );
	        drawResource(views, R.id.pos6,getNumberResource(context,secondSecond) );
	          
	        appWidgetManager.updateAppWidget(appWidgetId, views);
	}
   
    public static void drawResource(RemoteViews views, int posId, int resourceId ){
    	views.setImageViewResource(posId,resourceId);
    }
    
    public static int getNumberResource(Context context, int number){
    	//Log.d("Returning", "return number "+ "for "+number );
    	TypedArray arr = context.getResources().obtainTypedArray(R.array.blackWithWhite);
    	
    	int resource  = 0;
		
		resource =arr.getResourceId(number, 0);
		//Log.d("Returning", "return number "+resource+ "for "+number );
		return resource;
    }
    
    
    
}