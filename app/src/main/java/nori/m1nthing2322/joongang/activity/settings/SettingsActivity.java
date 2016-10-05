package nori.m1nthing2322.joongang.activity.settings;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import itmir.tistory.com.xor.SecurityXor;
import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.autoupdate.updateAlarm;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(R.id.container, new PrefsFragment()).commit();
    }

    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.pref_settings);

            boolean isAdmin = getPreferenceManager().getSharedPreferences().getBoolean("userAdmin_1", false);
            if (isAdmin) {
                Preference proUpgrade = findPreference("proUpgrade");
                proUpgrade.setSummary(R.string.user_info_licensed);
                proUpgrade.setEnabled(false);
            }

            findPreference("myDeviceId")
                    .setSummary(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));

            setOnPreferenceClick(findPreference("infoAutoUpdate"));
            setOnPreferenceClick(findPreference("openSource"));
            setOnPreferenceClick(findPreference("ChangeLog"));
			setOnPreferenceClick(findPreference("Developer"));
            //setOnPreferenceClick(findPreference("proUpgrade"));
            setOnPreferenceChange(findPreference("autoBapUpdate"));
            setOnPreferenceChange(findPreference("updateLife"));

            try {
                PackageManager packageManager = getActivity().getPackageManager();
                PackageInfo info = packageManager.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
                findPreference("appVersion").setSummary(info.versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void setOnPreferenceClick(Preference mPreference) {
            mPreference.setOnPreferenceClickListener(onPreferenceClickListener);
        }

        private Preference.OnPreferenceClickListener onPreferenceClickListener = new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                String getKey = preference.getKey();

                if ("openSource".equals(getKey)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
                    builder.setTitle(R.string.license_title);
                    builder.setMessage(R.string.license_msg);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.show();
                } else if ("ChangeLog".equals(getKey)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
// Stable Version
//                    builder.setTitle(R.string.changeLog_title);
//                    builder.setMessage(R.string.changeLog_msg);
// Beta Version
                    builder.setTitle(R.string.changeLog_title_beta);
                    builder.setMessage(R.string.changeLog_msg_beta);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.show();
                } else if ("Developer".equals(getKey)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
                    builder.setTitle(R.string.developer_title);
                    builder.setMessage(R.string.developer_msg);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.show();
                } else if ("infoAutoUpdate".equals(getKey)) {
                    showNotification();
                } else if ("proUpgrade".equals(getKey)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
                    builder.setTitle(R.string.user_info_class_up_title);
//                    builder.setMessage(R.string.no_network_msg);

                    // Set an EditText view to get user input
                    final EditText input = new EditText(getActivity());
                    builder.setView(input);

                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            SecurityXor securityXor = new SecurityXor();
                            if ("^fgmhgnAz".equals(securityXor.getSecurityXor(value, 777))) {
                                getPreferenceManager().getSharedPreferences().edit().putBoolean("userAdmin_1", true).commit();
                            }
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, null);
                    builder.show();
                }
                return true;
            }
        };

        private void setOnPreferenceChange(Preference mPreference) {
            mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);

            if (mPreference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) mPreference;
                int index = listPreference.findIndexOfValue(listPreference.getValue());
                mPreference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
            } else if (mPreference instanceof EditTextPreference) {
                String values = ((EditTextPreference) mPreference).getText();
                if (values == null) values = "";
                onPreferenceChangeListener.onPreferenceChange(mPreference, values);
            }
        }

        private Preference.OnPreferenceChangeListener onPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                if (preference instanceof EditTextPreference) {
                    preference.setSummary(stringValue);

                } else if (preference instanceof ListPreference) {

                    /**
                     * ListPreference의 경우 stringValue가 entryValues이기 때문에 바로 Summary를
                     * 적용하지 못한다 따라서 설정한 entries에서 String을 로딩하여 적용한다
                     */
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);

                    preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

                    updateAlarm updateAlarm = new updateAlarm(getActivity());
                    updateAlarm.cancel();

                    if (index == 0) updateAlarm.autoUpdate();
                    else if (index == 1) updateAlarm.SaturdayUpdate();
                    else if (index == 2) updateAlarm.SundayUpdate();

                } else if (preference instanceof CheckBoxPreference) {
                    nori.m1nthing2322.joongang.tool.Preference mPref = new nori.m1nthing2322.joongang.tool.Preference(getActivity());

                    if (mPref.getBoolean("firstOfAutoUpdate", true)) {
                        mPref.putBoolean("firstOfAutoUpdate", false);
                        showNotification();
                    }

                    if (!mPref.getBoolean("autoBapUpdate", false) && preference.isEnabled()) {
                        int updateLife = Integer.parseInt(mPref.getString("updateLife", "0"));

                        updateAlarm updateAlarm = new updateAlarm(getActivity());
                        if (updateLife == 1) updateAlarm.autoUpdate();
                        else if (updateLife == 0) updateAlarm.SaturdayUpdate();
                        else if (updateLife == -1) updateAlarm.SundayUpdate();

                    } else {
                        updateAlarm updateAlarm = new updateAlarm(getActivity());
                        updateAlarm.cancel();
                    }
                }
                return true;
            }
        };

        private void showNotification() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
            builder.setTitle(R.string.info_autoUpdate_title);
            builder.setCancelable(false);
            builder.setMessage(R.string.info_autoUpdate_msg);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.show();
        }
    }
}
