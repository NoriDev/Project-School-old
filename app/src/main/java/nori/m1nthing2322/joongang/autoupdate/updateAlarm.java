package nori.m1nthing2322.joongang.autoupdate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import nori.m1nthing2322.joongang.tool.BapTool;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class updateAlarm {
    private AlarmManager mAlarm;
    private PendingIntent mPendingIntent;
    private Context mContext;
    private Calendar mCalendar;

    public updateAlarm(Context mContext) {
        this.mContext = mContext;
        this.mCalendar = Calendar.getInstance();
        this.mAlarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public void autoUpdate() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        Intent mIntent = new Intent(mContext, BroadCast.class);
        mIntent.setAction(BapTool.ACTION_UPDATE);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
        mCalendar.set(year, month, day + 1, 1, 0);
        mAlarm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 4 * 24 * 60 * 60 * 1000, mPendingIntent);
    }

    public void SundayUpdate() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        // 저번주 일요일을 구한다
        mCalendar.add(Calendar.DAY_OF_WEEK, -1 * (dayOfWeek - 1));
        // 다음주 일요일을 구한다
        mCalendar.add(Calendar.DAY_OF_WEEK, 7);

        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        Intent mIntent = new Intent(mContext, BroadCast.class);
        mIntent.setAction(BapTool.ACTION_UPDATE);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
        mCalendar.set(year, month, day, 1, 0);
        mAlarm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000, mPendingIntent);
    }

    public void SaturdayUpdate() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        // 저번주 일요일을 구한다
        mCalendar.add(Calendar.DAY_OF_WEEK, -1 * (dayOfWeek - 1));
        // 다음주 토요일을 구한다
        mCalendar.add(Calendar.DAY_OF_WEEK, 6);

        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        Intent mIntent = new Intent(mContext, BroadCast.class);
        mIntent.setAction(BapTool.ACTION_UPDATE);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
        mCalendar.set(year, month, day, 1, 0);
        mAlarm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000, mPendingIntent);
    }

    public void wifiOFF() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);

        Intent mIntent = new Intent(mContext, BroadCast.class);
        mIntent.setAction(BapTool.ACTION_UPDATE);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
        mCalendar.set(year, month, day, hour + 2, 0);
        mAlarm.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), mPendingIntent);
    }

    public void cancel() {
        Intent mIntent = new Intent(mContext, BroadCast.class);
        mIntent.setAction(BapTool.ACTION_UPDATE);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);

        mAlarm.cancel(mPendingIntent);
    }

}
