package jp.ind.knhnnh.adbpushex;

import jp.ind.knhnnh.adbpushex.Utils.Result;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class KickReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Result r = Utils.rename(context, intent.getData().getPath());
		if (TextUtils.isEmpty(r.message))
			return;
		Notification notification = new Notification(android.R.drawable.stat_notify_error,
																	r.message, System.currentTimeMillis());
		Intent aintent = new Intent(context, InfoActivity.class);
		aintent.putExtra("message", r.message);
		notification.setLatestEventInfo(context, context.getString(R.string.app_name),
					r.message, PendingIntent.getActivity(context, 0, aintent, PendingIntent.FLAG_CANCEL_CURRENT));
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
	}

}
