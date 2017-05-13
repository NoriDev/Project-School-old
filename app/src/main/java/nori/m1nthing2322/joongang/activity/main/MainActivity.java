package nori.m1nthing2322.joongang.activity.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.activity.settings.SettingsActivity;
import nori.m1nthing2322.joongang.tool.Preference;

public class MainActivity extends AppCompatActivity {

    private int ver= 33410;
//    private ProgressDialog dialog;
    String xml;

    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // beta 테스트 앱일 경우에만 활성화
//        FirebaseMessaging.getInstance().subscribeToTopic("beta");

        FirebaseInstanceId.getInstance().getToken();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
//           mActionBar.setSubtitle(R.string.beta);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.mViewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        showUpdateNotification();
    }

    private void showUpdateNotification() {
        try {
            Preference mPref = new Preference(getApplicationContext());
            PackageManager packageManager = getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);

            int versionCode = info.versionCode;

            if (mPref.getInt("versionCode", 0) != versionCode) {
                mPref.putInt("versionCode", versionCode);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(R.string.changelog_title);
//                builder.setTitle(R.string.changelog_title_beta);
                builder.setMessage(R.string.changelog_msg_lite);
//                builder.setMessage(R.string.changelog_msg_beta_lite);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setCancelable(false);
                builder.show();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
/*
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage(getString((R.string.check_version)));
        dialog.show();
*/
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        StringBuilder sBuffer = new StringBuilder();
        try{//Start Try
            String urlAddr = "https://raw.githubusercontent.com/NoriDev/Project-School/master/version/Project_School.xml";
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
//                    dialog.dismiss();
                    if(Integer.parseInt(xml)==ver){//new version
//                        Toast.makeText(getApplicationContext(), R.string.latest_version, Toast.LENGTH_SHORT).show();
                    } else if(Integer.parseInt(xml)>ver){
                        //현재 버전보다 서버 버전이 높을때
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.low_version);
                        builder.setMessage(R.string.plz_update);
                        builder.setCancelable(false);
                        builder.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }});
                        builder.setPositiveButton(R.string.update_now, new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent myIntent = new Intent
                                                (Intent.ACTION_VIEW, Uri.parse
                                                        ("https://play.google.com/store/apps/details?id=nori.m1nthing2322.joongang"));
                                        startActivity(myIntent);
                                    }});
                        builder.show();
                    } else {
                        //현재 버전보다 서버 버전이 낮을때
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, R.string.crack_contents, duration);
                        toast.show();
//                        Toast.makeText(getApplicationContext(), R.string.crack_contents, Toast.LENGTH_SHORT).show();
                    }
                }
            };
            _timer.start();
        }//end try
        catch (Exception e) {
            //네트워크가 올바르지 않을때
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, R.string.offline, duration);
            toast.show();
//            Toast.makeText(getApplicationContext(), R.string.offline, Toast.LENGTH_LONG).show();
//            dialog.cancel();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter mAdapter = new Adapter(getSupportFragmentManager());
        mAdapter.addFragment(getString(R.string.activity_main_fragment_simpleview), MainFragment.getInstance(1));
        mAdapter.addFragment(getString(R.string.activity_main_fragment_notice), MainFragment.getInstance(2));
        mAdapter.addFragment(getString(R.string.activity_main_fragment_schoolinfo), MainFragment.getInstance(3));
        viewPager.setAdapter(mAdapter);
    }

    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        Adapter(FragmentManager manager) {
            super(manager);
        }

        void addFragment(String mTitle, Fragment mFragment) {
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_feedback) {
            Uri uri = Uri.parse("mailto:noridevdroid@gmail.com");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(it);
            return true;
        }
// 채팅 기능 잠정 중단
/*
        if (id == R.id.action_chat) {
            Uri uri = Uri.parse("https://open.kakao.com/o/g6KQyFq");
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
            return true;
        }
*/
// beta 테스트 앱일 경우에만 활성화
/*        if (id == R.id.action_chat_beta) {
            Uri uri = Uri.parse("https://open.kakao.com/o/gahCyFq");
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        long FINISH_INTERVAL_TIME = 2000;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Context context = getApplicationContext();
            CharSequence text = "\'뒤로가기\' 버튼을 한번 더 누르시면 종료됩니다.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
//            Snackbar.make(getWindow().getDecorView().getRootView(), "\'뒤로가기\' 버튼을 한번 더 누르시면 종료됩니다.", Snackbar.LENGTH_SHORT).show();
        }
    }
}
