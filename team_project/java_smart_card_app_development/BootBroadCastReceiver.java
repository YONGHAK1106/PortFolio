package kr.co.cardledger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 *	전원을 켯을시 수신할 리시버
 */
public class BootBroadCastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, final Intent intent) {
		// 브로드 캐스팅이 수신될떄
		// 해당 액션인지 체크한다.
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Log.i("BOOTSVC", "Intent received");
			// 서비스를 실행한다.
			context.startService(new Intent(context, CardSmsCheckService.class));
		}
	}
}
