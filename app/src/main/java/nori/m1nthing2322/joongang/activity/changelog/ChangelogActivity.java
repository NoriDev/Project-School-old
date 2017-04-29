package nori.m1nthing2322.joongang.activity.changelog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class ChangelogActivity extends AppCompatActivity {
    public static final String ChangelogDBName = "Changelog.db";
    public static final String ChangelogTableName = "ChangelogInfo";

    boolean isAdmin;

    ListView mListView;
    ChangelogAdapter mAdapter;
    ProgressDialog mDialog;

    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelog);
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

        isAdmin = new Preference(getApplicationContext()).getBoolean("userAdmin_1", false);

        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new ChangelogAdapter(this);
        mListView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showNoticeData(true);
            }
        });

        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.mFab);
        if (isAdmin) {
            mFab.setVisibility(View.VISIBLE);
        }
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAdmin) {
                    startActivity(new Intent(getApplicationContext(), ChangelogSendActivity.class).putExtra("userAdmin_1", true));
                } else {
                    Snackbar.make(view, R.string.user_info_require_message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        showNoticeData(false);
    }

    private void showNoticeData(boolean forceUpdate) {
        mAdapter.clearData();
        mAdapter.notifyDataSetChanged();

        if (Tools.isOnline(getApplicationContext())) {
            if (Tools.isWifi(getApplicationContext()) || forceUpdate) {
                getNoticeDownloadTask mTask = new getNoticeDownloadTask();
                mTask.execute("https://docs.google.com/spreadsheets/d/1hTI5-CFLTV_2yUO6shkFv1quoHJFTlBx7WxjS9KOwGg/pubhtml?gid=1725465132&single=true");
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
                        mTask.execute("https://docs.google.com/spreadsheets/d/1hTI5-CFLTV_2yUO6shkFv1quoHJFTlBx7WxjS9KOwGg/pubhtml?gid=1725465132&single=true");
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
        if (new File(TimeTableTool.mFilePath + ChangelogDBName).exists()) {
            showListViewDate();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatErrorAlertDialogStyle);
            builder.setTitle(R.string.no_network_title);
            builder.setMessage(R.string.no_network_msg);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.show();
        }

        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    class getNoticeDownloadTask extends GoogleSheetTask {
        private Database mDatabase;
        private String[] columnFirstRow;

        @Override
        public void onPreDownload() {
            mDialog = new ProgressDialog(ChangelogActivity.this);
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.loading_title));
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();

            new File(TimeTableTool.mFilePath + ChangelogDBName).delete();
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

                mDatabase.openOrCreateDatabase(TimeTableTool.mFilePath, ChangelogDBName, ChangelogTableName, Column.substring(0, Column.length() - 2));
            } else {
                int length = row.length;
                for (int i = 0; i < length - 1; i++) {
                    mDatabase.addData(columnFirstRow[i], row[i]);
                }
                mDatabase.commit(ChangelogTableName);
            }
        }
    }

    private void showListViewDate() {
        Database mDatabase = new Database();
        mDatabase.openDatabase(TimeTableTool.mFilePath, ChangelogDBName);
        Cursor mCursor = mDatabase.getData(ChangelogTableName, "*");

        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToNext();

            String date = mCursor.getString(1);
            String title = mCursor.getString(2);
            String message = mCursor.getString(3);

            mAdapter.addItem(title, message, date);
        }

        mAdapter.notifyDataSetChanged();

        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

}
