package jp.ind.knhnnh.adbpushex;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		((TextView)findViewById(R.id.app_message)).setText(Html.fromHtml(getString(R.string.app_message)));
	}
}
