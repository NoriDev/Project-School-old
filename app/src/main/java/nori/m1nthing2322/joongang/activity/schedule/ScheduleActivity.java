package nori.m1nthing2322.joongang.activity.schedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nori.m1nthing2322.joongang.R;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class ScheduleActivity extends AppCompatActivity {
    ViewPager viewPager;

    private int scheduleVer= 201801; // yyyy년도 nn버전 (01 버전 - 일정표에 수정이 가해지지 않음, 02~ 버전 - 일정표가 일부 또는 전체 수정이 가해짐)
    String xml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setSubtitle("학사일정");

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        viewPager = findViewById(R.id.mViewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = findViewById(R.id.mTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        setCurrentItem();

        examTimeTableUpdate();
    }

    private void examTimeTableUpdate() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        StringBuilder sBuffer = new StringBuilder();
        try{//Start Try
            String urlAddr = "https://raw.githubusercontent.com/NoriDev/Project-School/master/version/Project_School_Schedule.xml";
            URL url = new URL(urlAddr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn != null){//Start if
                conn.setConnectTimeout(1000);
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
                    if(Integer.parseInt(xml)==scheduleVer){//new version
//                        Toast.makeText(getApplicationContext(), R.string.latest_version, Toast.LENGTH_SHORT).show();
                    } else if(Integer.parseInt(xml)>scheduleVer){
                        //현재 버전보다 서버 버전이 높을때
                        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
                        builder.setTitle("학사일정이 업데이트됨");
                        builder.setMessage("학사일정이 업데이트 됨에 따라, 기존 일정표를 업데이트 하셔야 합니다.\n일정표를 업데이트 하시려면 \'확인\'을 눌러주십시오.\n\n일정을 업데이트 하시려면, 먼저 앱을 업데이트 하셔야 합니다.");
                        builder.setCancelable(false);
                        builder.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }});
                        builder.setPositiveButton(R.string.update_now, new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent myIntent = new Intent
                                                (Intent.ACTION_VIEW, Uri.parse
                                                        ("https://play.google.com/store/apps/details?id=nori.m1nthing2322.joongang"));
                                        startActivity(myIntent);
										finish();
                                    }});
                        builder.show();
                    } else {
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

    private void setupViewPager(ViewPager viewPager) {
        Adapter mAdapter = new Adapter(getSupportFragmentManager());

        mAdapter.addFragment("3월", ScheduleFragment.getInstance(3));
        mAdapter.addFragment("4월", ScheduleFragment.getInstance(4));
        mAdapter.addFragment("5월", ScheduleFragment.getInstance(5));
        mAdapter.addFragment("6월", ScheduleFragment.getInstance(6));
        mAdapter.addFragment("7월", ScheduleFragment.getInstance(7));
        mAdapter.addFragment("8월", ScheduleFragment.getInstance(8));
        mAdapter.addFragment("9월", ScheduleFragment.getInstance(9));
        mAdapter.addFragment("10월", ScheduleFragment.getInstance(10));
        mAdapter.addFragment("11월", ScheduleFragment.getInstance(11));
        mAdapter.addFragment("12월", ScheduleFragment.getInstance(12));
        mAdapter.addFragment("2019년 1월", ScheduleFragment.getInstance(1));
        mAdapter.addFragment("2019년 2월", ScheduleFragment.getInstance(2));

        viewPager.setAdapter(mAdapter);
    }

    private void setCurrentItem() {
        int month = Calendar.getInstance().get(Calendar.MONTH);

        if (month >= 2) month -= 2;
        else month += 9;

        viewPager.setCurrentItem(month);
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

}
