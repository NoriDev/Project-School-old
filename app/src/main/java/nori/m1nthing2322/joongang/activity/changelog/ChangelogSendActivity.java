package nori.m1nthing2322.joongang.activity.changelog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.Vector;

import nori.m1nthing2322.joongang.R;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class ChangelogSendActivity extends AppCompatActivity {
    EditText mTitle, mMessage;

    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_send);
        Toolbar mToolbar = findViewById(R.id.mToolbar);
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

        boolean isAdmin = getIntent().getBooleanExtra("userAdmin_1", false);
        if (!isAdmin)
            finish();

        mTitle = findViewById(R.id.mTitle);
        mMessage = findViewById(R.id.mMessage);
    }

    public void sendData(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangelogSendActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.post_notice_title);
        builder.setMessage(R.string.post_notice_alert);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = mTitle.getText().toString().trim();
                String message = mMessage.getText().toString().trim();

                if (!title.isEmpty() && !message.isEmpty()) {
                    (new HttpTask()).execute(title, message);
                }
            }
        });
        builder.show();
    }

    private class HttpTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(ChangelogSendActivity.this);
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.post_notice_posting));
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                HttpPost postRequest = new HttpPost("https://script.google.com/macros/s/AKfycbwR755X_mEWKZ8LKQuQf81t5rVerzOLCg1ztZyHisNr7rB8rIo/exec");


                //전달할 값들
                Vector<NameValuePair> nameValue = new Vector<>();
                nameValue.add(new BasicNameValuePair("sheet_name", "알림"));
                nameValue.add(new BasicNameValuePair("title", params[0]));
                nameValue.add(new BasicNameValuePair("message", params[1]));
                nameValue.add(new BasicNameValuePair("deviceId", Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID)));

                //웹 접속 - UTF-8으로
                HttpEntity Entity = new UrlEncodedFormEntity(nameValue, "UTF-8");
                postRequest.setEntity(Entity);

                HttpClient mClient = new DefaultHttpClient();
                mClient.execute(postRequest);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        protected void onPostExecute(Boolean value) {
            super.onPostExecute(value);

            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }

            if (value) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangelogSendActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(R.string.post_notice_title);
                builder.setMessage(R.string.post_notice_success);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.show();

                mTitle.setText("");
                mMessage.setText("");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangelogSendActivity.this, R.style.AppCompatErrorAlertDialogStyle);
                builder.setTitle(R.string.post_notice_title);
                builder.setMessage(R.string.post_notice_failed);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.show();
            }
        }
    }

}
