package nori.m1nthing2322.joongang.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class WidgetBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        String ACTION = mIntent.getAction();

        BapWidget.updateAllBapWidget(mContext);
        TimeTableWidget.updateAllTimeTableWidget(mContext);

        if (Intent.ACTION_BOOT_COMPLETED.equals(ACTION)) {
            // 24시간마다 앱 위젯 업데이트하기
            Calendar mCalendar = Calendar.getInstance();
            AlarmManager mAlarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

            Intent mIntentDate = new Intent(mContext, WidgetBroadCast.class);
            PendingIntent mPending = PendingIntent.getBroadcast(mContext, 0, mIntentDate, 0);
            mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH) + 1, 1, 0);
            mAlarm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 24 * 60 * 60 * 1000, mPending);
        }
    }
}
