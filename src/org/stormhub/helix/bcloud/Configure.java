package org.stormhub.helix.bcloud;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Configure extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        getActionBar().hide();
        Button button = (Button) findViewById(R.id.save);
        final Intent intent = getIntent();
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText serverEditText = (EditText) findViewById(R.id.server);
		        final String serverUrl = serverEditText.getText().toString();
		        EditText apiKeyEditText = (EditText) findViewById(R.id.api_key);
		        final String apiKey = apiKeyEditText.getText().toString();
				SharedPreferences sp = getSharedPreferences("DATA", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("server_url", serverUrl);
				editor.putString("api_key", apiKey);
				editor.commit();
				Intent resultValue = new Intent();
				int mAppWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
				setResult(RESULT_OK, resultValue);
				finish();
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.configure, menu);
        return true;
    }
}
