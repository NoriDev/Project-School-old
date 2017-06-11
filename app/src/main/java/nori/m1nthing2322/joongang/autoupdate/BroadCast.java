package nori.m1nthing2322.joongang.autoupdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import nori.m1nthing2322.joongang.tool.BapTool;
import nori.m1nthing2322.joongang.tool.Preference;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class BroadCast extends BroadcastReceiver {
    Preference mPref;

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        String ACTION = mIntent.getAction();
        mPref = new Preference(mContext);

        boolean autoUpdate = mPref.getBoolean("autoBapUpdate", false);
        if (!autoUpdate)
            return;
        /**
         * 1 : 자동
         * 0 : 매주 토요일
         * -1 : 매주 일요일
         */
        int updateLife = Integer.parseInt(mPref.getString("updateLife", "0"));

        Calendar mCalendar = Calendar.getInstance();
        int DayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        /**
         * 부팅후 실행
         */
        if (Intent.ACTION_BOOT_COMPLETED.equals(ACTION)) {
            if ((DayOfWeek == Calendar.SUNDAY && updateLife == -1)
                    || (DayOfWeek == Calendar.SATURDAY && updateLife == 0)) {
                if (haveToUpdate(mContext, mCalendar)) {
                    mContext.startService(new Intent(mContext, updateService.class));
                }
            }

            updateAlarm updateAlarm = new updateAlarm(mContext);
            switch (updateLife) {
                case 1:
                    updateAlarm.autoUpdate();
                    break;
                case 0:
                    updateAlarm.SaturdayUpdate();
                    break;
                case -1:
                    updateAlarm.SundayUpdate();
                    break;
            }

        } else if (BapTool.ACTION_UPDATE.equals(ACTION)) {
            if (haveToUpdate(mContext, mCalendar))
                mContext.startService(new Intent(mContext, updateService.class));
        }
    }

    private boolean haveToUpdate(Context mContext, Calendar mCalendar) {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        BapTool.restoreBapDateClass mData = BapTool.restoreBapData(mContext, year, month, day);

        return mData.isBlankDay;
    }
}
