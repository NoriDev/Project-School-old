package nori.m1nthing2322.joongang.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class Preference {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    public Preference(Context mContext) {
        mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mPref.edit();
    }

    public Preference(Context mContext, String prefName) {
        mPref = mContext.getSharedPreferences(prefName, 0);
        mEditor = mPref.edit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }

    public String getString(String key, String defValue) {
        return mPref.getString(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    public void clear() {
        mEditor.clear().commit();
    }

    public void remove(String key) {
        mEditor.remove(key).commit();
    }
}
