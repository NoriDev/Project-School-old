package nori.m1nthing2322.joongang.activity.timetable;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import itmir.tistory.com.spreadsheets.GoogleSheetTask;
import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.tool.Database;
import nori.m1nthing2322.joongang.tool.Preference;
import nori.m1nthing2322.joongang.tool.TimeTableTool;
import nori.m1nthing2322.joongang.tool.Tools;

public class TimeTableActivity extends AppCompatActivity {
    Preference mPref;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        mPref = new Preference(getApplicationContext());
        int mGrade = mPref.getInt("myGrade", -1);
        int mClass = mPref.getInt("myClass", -1);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        if ((mGrade != -1) && (mClass != -1)) {
            mToolbar.setTitle(String.format(getString(R.string.timetable_title), mGrade, mClass));
        }
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

        if ((mGrade == -1) || (mClass == -1)) {
            resetGrade();
            return;
        }

        if (!TimeTableTool.fileExists()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(R.string.no_time_table_db_title);
            builder.setMessage(R.string.no_time_table_db_message);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    downloadingDB();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.show();
            return;
        }

        viewPager = (ViewPager) findViewById(R.id.mViewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.mFab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadingDB();
            }
        });

        setCurrentItem();

        timeTableUpdate();
    }
// 다음 업데이트 시점까지 1회 활성화 (시간표가 업데이트 되면 1회 재활성화)
    private void timeTableUpdate() {
        try {
            Preference mPref = new Preference(getApplicationContext());
            PackageManager packageManager = getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);

            int timeTableUpdateCode = 201701; // 2017(학년도) 01(학기)

            if (mPref.getInt("timeTableUpdateCode", 0) != timeTableUpdateCode) {
                mPref.putInt("timeTableUpdateCode", timeTableUpdateCode);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("시간표가 업데이트됨");
                builder.setMessage("시간표가 업데이트 됨에 따라, 기존 시간표를 업데이트 하셔야 합니다.\n2017학년도 시간표를 설치하시려면 \'확인\'을 눌러주십시오.");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadingDB();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentItem() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek > 1 && dayOfWeek < 7) {
            viewPager.setCurrentItem(dayOfWeek - 2);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    private void resetGrade() {
        mPref.remove("myGrade");

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.action_setting_mygrade);
        builder.setItems(R.array.myGrade, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPref.putInt("myGrade", which + 1);
                resetClass();
            }
        });
        builder.show();
    }

    private void resetClass() {
        mPref.remove("myClass");

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.action_setting_myclass);
        builder.setItems(R.array.myClass, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPref.putInt("myClass", which + 1);
                Toast.makeText(getApplicationContext(), "다시 로딩됩니다", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), TimeTableActivity.class));
                finish();
            }
        });
        builder.show();
    }

    public void downloadingDB() {
        if (Tools.isOnline(getApplicationContext())) {
            if (Tools.isWifi(getApplicationContext())) {
                downloadStart();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(R.string.no_wifi_title);
                builder.setMessage(R.string.no_wifi_msg);
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        downloadStart();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatErrorAlertDialogStyle);
            builder.setTitle(R.string.no_network_title);
            builder.setMessage(R.string.no_network_msg);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setCancelable(false);
            builder.show();
        }
    }

    private void downloadStart() {
        new File(TimeTableTool.mFilePath + TimeTableTool.TimeTableDBName).delete();
        DBDownloadTask mTask = new DBDownloadTask();
        mTask.execute(TimeTableTool.mGoogleSpreadSheetUrl);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter mAdapter = new Adapter(getSupportFragmentManager());

        for (int dayOfWeek = 0; dayOfWeek < 5; dayOfWeek++) {
            mAdapter.addFragment(TimeTableTool.mDisplayName[dayOfWeek], TimeTableFragment.getInstance(mPref.getInt("myGrade", -1), mPref.getInt("myClass", -1), dayOfWeek));
        }

        viewPager.setAdapter(mAdapter);
    }

    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        public void addFragment(String mTitle, Fragment mFragment) {
            mFragments.add(mFragment);
            mFragmentTitles.add(mTitle);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    class DBDownloadTask extends GoogleSheetTask {
        private ProgressDialog mDialog;
        private Database mDatabase;
        private String[] columnFirstRow;

        @Override
        public void onPreDownload() {
            mDialog = new ProgressDialog(TimeTableActivity.this);
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.loading_title));
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();

            mDatabase = new Database();
        }

        @Override
        public void onFinish(long result) {
            startActivity(new Intent(TimeTableActivity.this, TimeTableActivity.class));
            finish();

            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }

            if (mDatabase != null)
                mDatabase.release();
        }

        @Override
        public void onRow(int startRowNumber, int position, String[] row) {
            if (startRowNumber == position) {
                columnFirstRow = row;

                StringBuilder Column = new StringBuilder();

                for (String column : row) {
                    Column.append(column);
                    Column.append(" text, ");
                }

                mDatabase.openOrCreateDatabase(TimeTableTool.mFilePath, TimeTableTool.TimeTableDBName, TimeTableTool.tableName, Column.substring(0, Column.length() - 2));
            } else {
                int length = row.length;
                for (int i = 0; i < length; i++) {
                    mDatabase.addData(columnFirstRow[i], row[i]);
                }
                mDatabase.commit(TimeTableTool.tableName);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset_mygrade) {
            resetGrade();
            return true;

        } else if (id == R.id.action_share_timetable) {
            shareTimeTable();
            return true;
        } else if (id == R.id.action_download_db) {
            downloadingDB();
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareTimeTable() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.action_share_day);
        builder.setItems(R.array.myWeek, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shareTimeTable(which, mPref.getInt("myGrade", -1), mPref.getInt("myClass", -1));
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void shareTimeTable(int position, int mGrade, int mClass) {
        try {
            String mText = "";

            TimeTableTool.timeTableData mData = TimeTableTool.getTimeTableData(mGrade, mClass, position + 2);

            String[] subject = mData.subject;
            String[] room = mData.room;

            for (int period = 0; period < 7; period++) {
                mText += "\n" + (period + 1) + "교시 : " + subject[period] + "(" + room[period] + ")";
            }

            String title = getString(R.string.action_share_timetable);
            Intent msg = new Intent(Intent.ACTION_SEND);
            msg.addCategory(Intent.CATEGORY_DEFAULT);
            msg.putExtra(Intent.EXTRA_TITLE, title);
            msg.putExtra(Intent.EXTRA_TEXT, String.format(
                    getString(R.string.action_share_timetable_msg),
                    TimeTableTool.mDisplayName[position], mText));
            msg.setType("text/plain");
            startActivity(Intent.createChooser(msg, title));

        } catch (Exception ex) {
            ex.printStackTrace();

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatErrorAlertDialogStyle);
            builder.setTitle(R.string.I_do_not_know_the_error_title);
            builder.setMessage(R.string.I_do_not_know_the_error_message);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setCancelable(false);
            builder.show();
        }
    }
}
