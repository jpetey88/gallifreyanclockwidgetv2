package com.jpetey88.gallifreyanclockwidget;

import java.util.GregorianCalendar;

import com.jpetey88.gallifreyanclockwidget.utils.Picasso;
import com.larswerkman.holocolorpicker.ColorPicker;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RemoteViews;
import android.widget.Spinner;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends Activity {
	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */
	private static final boolean ALWAYS_SIMPLE_PREFS = false;
	 private Context self = this;
     private int appWidgetId;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // get the appWidgetId of the appWidget being configured
            Intent launchIntent = getIntent();
            Bundle extras = launchIntent.getExtras();
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID);

            // set the result for cancel first
            // if the user cancels, then the appWidget
            // should not appear
            Intent cancelResultValue = new Intent();
            cancelResultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                            appWidgetId);
            setResult(RESULT_CANCELED, cancelResultValue);
            // show the user interface of configuration
            setContentView(R.layout.configuration);
            final Spinner spinner = (Spinner) findViewById(R.id.color_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.clockcolors, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            
            
            // the OK button
            Button ok = (Button) findViewById(R.id.okbutton);
            ok.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                            // get the date from DatePicker
                          

                            // save the goal date in SharedPreferences
                            // we can only store simple types only like long
                            // if multiple widget instances are placed
                            // each can have own goal date
                            // so store it under a name that contains appWidgetId
                    	    ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
                    	
                            SharedPreferences prefs = self.getSharedPreferences("prefs", 0);
                            SharedPreferences.Editor edit = prefs.edit();
                            edit.putString("ColorChoice", spinner.getSelectedItem().toString());
                           Log.d("joshy",""+ picker.getColor());
                            edit.putInt("NewColor", picker.getColor());
                            Picasso.invalidateCache();
                            //edit.putLong("goal" + appWidgetId, date.getTime());
                            edit.commit();
                        //    Picasso.map = null;
                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(self.getApplicationContext());
                            RemoteViews views = new RemoteViews(self.getApplicationContext().getPackageName(),
                            		R.layout.digital);
                            views.setOnClickPendingIntent(R.id.digital, ClockProvider.buildButtonPendingIntent(self.getApplicationContext()));	
                            		appWidgetManager.updateAppWidget(appWidgetId, views);
                            
                            
                            // change the result to OK
                            Intent resultValue = new Intent();
                            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                            appWidgetId);
                            setResult(RESULT_OK, resultValue);
                            // finish closes activity
                            // and sends the OK result
                            // the widget will be be placed on the home screen
                            finish();
                    }
            });

            // cancel button
            Button cancel = (Button) findViewById(R.id.cancelbutton);
            cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                            // finish sends the already configured cancel result
                            // and closes activity
                            finish();
                    }
            });
    }
}
