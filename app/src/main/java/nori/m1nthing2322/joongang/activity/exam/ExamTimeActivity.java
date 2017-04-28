package nori.m1nthing2322.joongang.activity.exam;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import itmir.tistory.com.spreadsheets.GoogleSheetTask;
import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.tool.Database;
import nori.m1nthing2322.joongang.tool.ExamTimeTool;
import nori.m1nthing2322.joongang.tool.Preference;
import nori.m1nthing2322.joongang.tool.TimeTableTool;
import nori.m1nthing2322.joongang.tool.Tools;

public class ExamTimeActivity extends AppCompatActivity {
    ViewPager viewPager;
    Preference mPref;
    int mGrade, mType;

    private int examver= 20170101;
    String xml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_time);

// 2학기 2차 고사가 끝난 경우 활성화
/*
        AlertDialog.Builder end_Exam = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        end_Exam.setMessage("드디어 마지막 시험이 끝났습니다! (짝짝짝)\n시험 치시느라 고생많으셨고, 이제 얼마 남지 않은 담임선생님과의 시간을 소중히 간직하는 시간을 가져봅시다 :)");
        end_Exam.setPositiveButton(android.R.string.ok, null);
        end_Exam.show();
*/
        mPref = new Preference(getApplicationContext());
        mGrade = mPref.getInt("myGrade", -1);
        mType = mPref.getInt("myType", -1);
        boolean fileExists = ExamTimeTool.fileExists();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        if ((mGrade != -1) && (mType != -1)) {
            if (fileExists) {
                ExamTimeTool.examData mData = ExamTimeTool.getExamInfoData();
                mToolbar.setTitle(String.format(getString(R.string.exam_time_title), mData.date, mData.type));
                mToolbar.setSubtitle(String.format(getString(R.string.exam_time_subtitle), mGrade, (mType == 0 ? "인문" : "공학(자연)")));
            }
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

        if (mGrade == -1) {
            resetGrade();
            return;
        }

        if (mType == -1) {
            resetType();
            return;
        }

        if (!fileExists) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(R.string.no_exam_db_title);
            builder.setMessage(R.string.no_exam_db_message);
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

        examTimeTableUpdate();
    }

    private void examTimeTableUpdate() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        StringBuilder sBuffer = new StringBuilder();
        try{//Start Try
            String urlAddr = "http://noridev.iptime.org/Project%20School/Jinhae%20Joongang%20High%20School/Project_School_JoongangHS_Exam.xml";
            URL url = new URL(urlAddr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn != null){//Start if
                conn.setConnectTimeout(20000);
                //conn.setUseCaches(false);
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){//Start if
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    while(true){//Start While
                        String line = br.readLine();
                        if(line==null){//Start if
                            break;
                        }//end if
                        sBuffer.append(line);
                    }//end while
                    br.close();
                    conn.disconnect();
                }//end if
            }//end if
            xml = sBuffer.toString();
            CountDownTimer _timer = new CountDownTimer(1000, 1000){
                public void onTick(long millisUntilFinished)
                {}
                public void onFinish(){
                    if(Integer.parseInt(xml)==examver){//new version
//                        Toast.makeText(getApplicationContext(), R.string.latest_version, Toast.LENGTH_SHORT).show();
                    } else if(Integer.parseInt(xml)>examver){
                        //현재 버전보다 서버 버전이 높을때
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamTimeActivity.this);
                        builder.setTitle("시험 시간표가 업데이트됨");
                        builder.setMessage("시간표가 업데이트 됨에 따라, 기존 시험 시간표를 업데이트 하셔야 합니다.\n시험 시간표를 업데이트 하시려면 \'확인\'을 눌러주십시오.");
                        builder.setCancelable(false);
                        builder.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }});
                        builder.setPositiveButton(R.string.update_now, new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        downloadingDB();
                                    }});
                        builder.setCancelable(false);
                        builder.show();
                    }else {
                        //현재 버전보다 서버 버전이 낮을때
                    }
                }
            };
            _timer.start();
        }//end try
        catch (Exception e) {
            //네트워크가 올바르지 않을때
        }
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
            builder.show();
        }
    }

    private void downloadStart() {
        new File(TimeTableTool.mFilePath + ExamTimeTool.ExamDBName).delete();
        ExamDBDownloadTask mTask = new ExamDBDownloadTask();
        mTask.execute(ExamTimeTool.mGoogleSpreadSheetUrl);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter mAdapter = new Adapter(getSupportFragmentManager());

        ExamTimeTool.examData mData = ExamTimeTool.getExamInfoData();
        int days = Integer.parseInt(mData.days);

        for (int day = 1; day <= days; day++) {
            mAdapter.addFragment(day + "일째", ExamTimeFragment.getInstance(mGrade, mType, day));
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

    private void resetGrade() {
        mPref.remove("myGrade");

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.action_setting_mygrade);
        builder.setItems(R.array.myGrade, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPref.putInt("myGrade", which + 1);
                if (mType == -1) {
                    resetType();
                } else {
                    Toast.makeText(getApplicationContext(), "다시 로딩됩니다", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ExamTimeActivity.class));
                    finish();
                }
            }
        });
        builder.show();
    }

    private void resetType() {
        mPref.remove("myType");

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.action_setting_mytype);
        builder.setItems(R.array.myType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPref.putInt("myType", which);
                Toast.makeText(getApplicationContext(), "다시 로딩됩니다", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ExamTimeActivity.class));
                finish();
            }
        });
        builder.show();
    }

    class ExamDBDownloadTask extends GoogleSheetTask {
        private ProgressDialog mDialog;
        private Database mDatabase;
        private String[] columnFirstRow;

        @Override
        public void onPreDownload() {
            mDialog = new ProgressDialog(ExamTimeActivity.this);
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.loading_title));
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();

            mDatabase = new Database();
        }

        @Override
        public void onFinish(long result) {
            startActivity(new Intent(ExamTimeActivity.this, ExamTimeActivity.class));
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

                mDatabase.openOrCreateDatabase(TimeTableTool.mFilePath, ExamTimeTool.ExamDBName, ExamTimeTool.ExamTableName, Column.substring(0, Column.length() - 2));
            } else {
                int length = row.length;
                for (int i = 0; i < length; i++) {
                    mDatabase.addData(columnFirstRow[i], row[i]);
                }
                mDatabase.commit(ExamTimeTool.ExamTableName);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_download_db) {
            downloadingDB();
            return true;
        } else if (id == R.id.action_reset_mygrade) {
            mPref.remove("myType");
            mType = -1;
            resetGrade();
        }

        return super.onOptionsItemSelected(item);
    }

}
