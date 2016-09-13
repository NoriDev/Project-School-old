package nori.m1nthing2322.joongang.activity.schedule;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.tool.RecyclerItemClickListener;

/**
 * Created by whdghks913 on 2015-12-10.
 */
public class ScheduleFragment extends Fragment {

    public static Fragment getInstance(int month) {
        ScheduleFragment mFragment = new ScheduleFragment();

        Bundle args = new Bundle();
        args.putInt("month", month);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        final ScheduleAdapter mAdapter = new ScheduleAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View mView, int position) {
                try {
                    String date = mAdapter.getItemData(position).date;
                    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREA);

                    Calendar mCalendar = Calendar.getInstance();
                    long nowTime = mCalendar.getTimeInMillis();

                    mCalendar.setTime(mFormat.parse(date));
                    long touchTime = mCalendar.getTimeInMillis();

                    long diff = (touchTime - nowTime);

                    boolean isPast = false;
                    if (diff < 0) {
                        diff = -diff;
                        isPast = true;
                    }

                    int diffDays = (int) (diff /= 24 * 60 * 60 * 1000);
                    String mText = "";

                    if (diffDays == 0)
                        mText += "오늘 일정입니다.";
                    else if (isPast)
                        mText = "선택하신 날짜는 " + diffDays + "일전 날짜입니다";
                    else
                        mText = "선택하신 날짜까지 " + diffDays + "일 남았습니다";

                    Snackbar.make(mView, mText, Snackbar.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));

        Bundle args = getArguments();
        int month = args.getInt("month");

        switch (month) {
            case 3:
                mAdapter.addItem("3.1절", "2015.03.01 (일)", true);
                mAdapter.addItem("입학식 및 시업식", "2015.03.02 (월)");
                mAdapter.addItem("전국 연합 학력 평가 (전학년)", "2015.03.11 (수)");
                mAdapter.addItem("학부모 총회", "2015.03.13 (금)");
                break;
            case 4:
                mAdapter.addItem("전국 연합 학력 평가 (3학년)", "2015.04.09 (목)");
                mAdapter.addItem("영어 듣기 평가", "2015.04.14 (화)");
                mAdapter.addItem("영어 듣기 평가", "2015.04.15 (수)");
                mAdapter.addItem("영어 듣기 평가", "2015.04.16 (목)");
                mAdapter.addItem("1학기 1회고사", "2015.04.27 (월)");
                mAdapter.addItem("1학기 1회고사", "2015.04.28 (화)");
                mAdapter.addItem("1학기 1회고사", "2015.04.29 (수)");
                mAdapter.addItem("1학기 1회고사", "2015.04.30 (목)");
                break;
            case 5:
                mAdapter.addItem("어린이날", "2015.05.05 (화)", true);
                mAdapter.addItem("꿈끼 탐색 주간", "2015.05.06 (수)");
                mAdapter.addItem("꿈끼 탐색 주간", "2015.05.07 (목)");
                mAdapter.addItem("꿈끼 탐색 주간", "2015.05.08 (금)");
                mAdapter.addItem("교내 체육대회 (1,2학년)", "2015.05.15 (금)");
                mAdapter.addItem("현장 학습 (3학년)", "2015.05.15 (금)");
                mAdapter.addItem("석가탄신일", "2015.05.25 (월)");
                break;
            case 6:
                mAdapter.addItem("전국 연합 학력 평가 (1,2학년)", "2015.06.04 (목)");
                mAdapter.addItem("대 수능 모의 평가 (3학년)", "2015.06.04 (목)");
                mAdapter.addItem("개교기념일", "2015.06.05 (금)", true);
                mAdapter.addItem("현충일", "2015.06.06 (토)", true);
                mAdapter.addItem("국가 수준 학업 성취도 평가 (2학년)", "2015.06.23 (화)");
                break;
            case 7:
                mAdapter.addItem("1학기 2회고사", "2015.06.29 (월)");
                mAdapter.addItem("1학기 2회고사", "2015.06.30 (화)");
                mAdapter.addItem("1학기 2회고사", "2015.07.01 (수)");
                mAdapter.addItem("1학기 2회고사", "2015.07.02 (목)");
                mAdapter.addItem("전국 연합 학력 평가 (3학년)", "2015.07.09 (목)");
                mAdapter.addItem("여름방학식", "2015.07.17 (금)", true);
                break;
            case 8:
                mAdapter.addItem("개학식", "2015.08.05 (수)");
                break;
            case 9:
                mAdapter.addItem("전국 연합 학력 평가 (1,2학년)", "2015.09.02 (수)");
                mAdapter.addItem("대 수능 모의 평가 (3학년)", "2015.09.02 (수)");
                mAdapter.addItem("영어 듣기 평가", "2015.09.15 (화)");
                mAdapter.addItem("영어 듣기 평가", "2015.09.16 (수)");
                mAdapter.addItem("영어 듣기 평가", "2015.09.17 (목)");
                mAdapter.addItem("추석 연휴", "2015.9.28 (월)", true);
                mAdapter.addItem("추석 연휴", "2015.9.29 (화)", true);
                break;
            case 10:
                mAdapter.addItem("2학기 1회고사", "2015.09.30 (수)");
                mAdapter.addItem("2학기 1회고사", "2015.10.01 (목)");
                mAdapter.addItem("2학기 1회고사", "2015.10.02 (금)");
                mAdapter.addItem("2학기 1회고사", "2015.10.05 (월)");
                mAdapter.addItem("한글날", "2015.10.09 (금)", true);
                mAdapter.addItem("전국 연합 학력 평가 (3학년)", "2015.10.13 (화)");
                break;
            case 11:
                mAdapter.addItem("대학 수학 능력 시험", "2015.11.12 (목)", true);
                mAdapter.addItem("2학기 2회고사 (3학년)", "2015.11.16 (월)");
                mAdapter.addItem("2학기 2회고사 (3학년)", "2015.11.17 (화)");
                mAdapter.addItem("2학기 2회고사 (3학년)", "2015.11.18 (수)");
                mAdapter.addItem("2학기 2회고사 (3학년)", "2015.11.19 (목)");
                mAdapter.addItem("전국 연합 학력 평가 (1,2학년)", "2015.11.17 (화)");
                break;
            case 12:
                mAdapter.addItem("2학기 2회고사 (1,2학년)", "2015.11.30 (월)");
                mAdapter.addItem("2학기 2회고사 (1,2학년)", "2015.12.01 (화)");
                mAdapter.addItem("2학기 2회고사 (1,2학년)", "2015.12.02 (수)");
                mAdapter.addItem("2학기 2회고사 (1,2학년)", "2015.12.03 (목)");
                mAdapter.addItem("졸업 사정회 (3학년)", "2015.12.07 (월)");
                mAdapter.addItem("한마음 으뜸제", "2015.12.16 (수)");
                mAdapter.addItem("겨울 방학식", "2015.12.18 (금)", true);
                break;
            case 1:
                mAdapter.addItem("신정", "2016.01.01 (금)", true);
                break;
            case 2:
                mAdapter.addItem("개학식", "2016.02.01 (월)");
                mAdapter.addItem("졸업식/종업식", "2016.02.04 (목)");
                break;
        }

        return recyclerView;
    }
}
