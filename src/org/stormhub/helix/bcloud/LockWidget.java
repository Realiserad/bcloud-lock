package org.stormhub.helix.bcloud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.widget.RemoteViews;
import android.widget.Toast;

public class LockWidget extends AppWidgetProvider {
	private static final String WIDGET_PRESSED = "WIDGET_LOCK_PRESSED";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
		ComponentName watchWidget = new ComponentName(context, LockWidget.class);

		remoteViews.setOnClickPendingIntent(R.id.lock_button, getPendingSelfIntent(context, WIDGET_PRESSED));
		appWidgetManager.updateAppWidget(watchWidget, remoteViews);
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		super.onReceive(context, intent);

		if (WIDGET_PRESSED.equals(intent.getAction())) {
			Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(50);

			SharedPreferences prefs = context.getSharedPreferences("DATA", Context.MODE_PRIVATE);
			final String server = prefs.getString("server_url", "");
			final String apiKey = prefs.getString("api_key", "");
			
			new AsyncTask<Void, Void, Boolean>() {

				@Override
				protected Boolean doInBackground(Void... params) {
					try {
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(server + "?action=lock");

						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("apikey", apiKey));
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						
						httpclient.execute(httppost);
						
						return true;
					} catch (IOException e) {
						return false;
					}
				}
				
				@Override
				protected void onPostExecute(Boolean param) {
			        if (param) {
			        	Toast.makeText(context, R.string.lock_notification, Toast.LENGTH_SHORT).show();
			        } else {
			        	Toast.makeText(context, "An error occurred.", Toast.LENGTH_LONG).show();
			        }
			    }
				
			}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
}
