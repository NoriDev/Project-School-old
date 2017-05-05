package nori.m1nthing2322.joongang.activity.tel;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.io.File;

import itmir.tistory.com.spreadsheets.GoogleSheetTask;
import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.tool.Database;
import nori.m1nthing2322.joongang.tool.Preference;
import nori.m1nthing2322.joongang.tool.TimeTableTool;
import nori.m1nthing2322.joongang.tool.Tools;

public class TelActivity extends AppCompatActivity {
    public static final String TelDBName = "Tel.db";
    public static final String TelTableName = "TelInfo";

    boolean isAdmin;

    ListView mListView;
    TelAdapter mAdapter;
    ProgressDialog mDialog;

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setSubtitle("학교 연락망");

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        isAdmin = new Preference(getApplicationContext()).getBoolean("userAdmin_1", false);

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new TelAdapter(this);
        mListView.setAdapter(mAdapter);

        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.mFab);
        if (isAdmin) {
            mFab.setVisibility(View.VISIBLE);
        }
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAdmin) {
//                    startActivity(new Intent(getApplicationContext(), ChangelogSendActivity.class).putExtra("userAdmin_1", true));
                } else {
                    Snackbar.make(view, R.string.user_info_require_message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        showNoticeData();
    }

    private void showNoticeData() {
        mAdapter.clearData();
        mAdapter.notifyDataSetChanged();

        pref = getSharedPreferences("pref", 0);  //변경하지 마시오
        edit = pref.edit();   //변경하지 마시오

        if (pref.getInt("tel_1", 0) == 0) {
            if (Tools.isOnline(getApplicationContext())) {
                if (Tools.isWifi(getApplicationContext())) {
                    getNoticeDownloadTask mTask = new getNoticeDownloadTask();
                    mTask.execute("https://docs.google.com/spreadsheets/d/1Cny4MK3_Y2m90QKUjLV0o_T1JhB0c1P37cAnD6giBj4/pubhtml?gid=1943305429&single=true");
                    edit.putInt("tel_1", 1);
                    // edit.remove("tel_1");  // 이전 변수를 지울 때 주석 제거
                    edit.apply();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(R.string.no_wifi_title);
                builder.setMessage(R.string.no_wifi_msg);
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        offlineData();
                    }
                });
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getNoticeDownloadTask mTask = new getNoticeDownloadTask();
                        mTask.execute("https://docs.google.com/spreadsheets/d/1Cny4MK3_Y2m90QKUjLV0o_T1JhB0c1P37cAnD6giBj4/pubhtml?gid=1943305429&single=true");
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        } else {
            offlineData();
        }
    }

    private void offlineData() {
        if (new File(TimeTableTool.mFilePath + TelDBName).exists()) {
            showListViewDate();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatErrorAlertDialogStyle);
            builder.setTitle(R.string.no_network_title);
            builder.setMessage(R.string.no_network_msg);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.show();
        }

    }

    class getNoticeDownloadTask extends GoogleSheetTask {
        private Database mDatabase;
        private String[] columnFirstRow;

        @Override
        public void onPreDownload() {
            mDialog = new ProgressDialog(TelActivity.this);
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.loading_title));
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();

            new File(TimeTableTool.mFilePath + TelDBName).delete();
            mDatabase = new Database();
        }

        @Override
        public void onFinish(long result) {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }

            if (mDatabase != null)
                mDatabase.release();

            showListViewDate();
        }

        @Override
        public void onRow(int startRowNumber, int position, String[] row) {
            if (startRowNumber == position) {
                columnFirstRow = row;

                StringBuilder Column = new StringBuilder();

                // remove deviceId
                for (int i = 0; i < row.length - 1; i++) {
                    Column.append(row[i]);
                    Column.append(" text, ");
                }

                mDatabase.openOrCreateDatabase(TimeTableTool.mFilePath, TelDBName, TelTableName, Column.substring(0, Column.length() - 2));
            } else {
                int length = row.length;
                for (int i = 0; i < length - 1; i++) {
                    mDatabase.addData(columnFirstRow[i], row[i]);
                }
                mDatabase.commit(TelTableName);
            }
        }
    }

    private void showListViewDate() {
        Database mDatabase = new Database();
        mDatabase.openDatabase(TimeTableTool.mFilePath, TelDBName);
        Cursor mCursor = mDatabase.getData(TelTableName, "*");

        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToNext();

            String title = mCursor.getString(1);
            String message = mCursor.getString(2);

            mAdapter.addItem(title, message);
        }

        mAdapter.notifyDataSetChanged();
    }

}
