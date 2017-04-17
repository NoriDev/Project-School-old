package nori.m1nthing2322.joongang.activity.schedule;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.tool.Preference;

/**
 * Created by whdghks913 on 2015-12-10.
 */
public class ScheduleActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
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

        viewPager = (ViewPager) findViewById(R.id.mViewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        setCurrentItem();

        showScheduleNotification();
    }

    private void showScheduleNotification() {
        try {
            Preference mPref = new Preference(getApplicationContext());
            PackageManager packageManager = getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);

            int scheduleCode = 1;

            /*
            if (mPref.getInt("scheduleCode", 0) != scheduleCode) {
                mPref.putInt("scheduleCode", scheduleCode);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("일정 관련 안내");
                builder.setMessage("9월 이후 일정은 학교에서 발표하는대로 추가할 예정이며, 9월 이후 일정은 [대한민국 공휴일]만 표시됩니다.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.show();
            }
            */
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
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
        mAdapter.addFragment("2018년 1월", ScheduleFragment.getInstance(1));
        mAdapter.addFragment("2018년 2월", ScheduleFragment.getInstance(2));

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
