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
                        mText = "선택하신 날짜는 " + diffDays + "일 전 날짜입니다";
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
                mAdapter.addItem("3.1절", "2017.03.01 (수)", true);
                mAdapter.addItem("입학식 및 시업식", "2017.03.02 (목)");
                mAdapter.addItem("신입생 오리엔테이션 (부서별)", "2017.03.06 (월)");
                mAdapter.addItem("신입생 오리엔테이션 (교과별)", "2017.03.07 (화)");
                mAdapter.addItem("지진, 화재, 교통안전교육", "2017.03.08 (수)");
                mAdapter.addItem("전국 연합 학력 평가 (전학년)", "2017.03.09 (목)");
                mAdapter.addItem("성폭력 예방 교육", "2015.03.13 (월)");
                mAdapter.addItem("수련회 (1학년)", "2017.03.15 (수)");
                mAdapter.addItem("수련회 (1학년)", "2017.03.16 (목)");
                mAdapter.addItem("수련회 (1학년)", "2017.03.17 (금)");
                mAdapter.addItem("학생회 임명, 학교폭력 추방의 날, 금연 선포식", "2017.03.20 (월)");
                mAdapter.addItem("학교 교육과정 연수", "2017.03.22 (수)");
                mAdapter.addItem("학부모회", "2017.03.23 (목)");
                mAdapter.addItem("가정의 날", "2017.03.29 (수)");
                break;
            case 4:
                mAdapter.addItem("식목일, 장애인 & 인권교육", "2017.04.05 (수)");
                mAdapter.addItem("전국 연합 학력 평가 (3학년)", "2017.04.12 (수)");
                mAdapter.addItem("진로 적성 검사 (!학년)", "2017.04.13 (목)");
                mAdapter.addItem("건강 체력 평가", "2017.04.14 (금)");
                mAdapter.addItem("영어 듣기 평가", "2017.04.18 (화)");
                mAdapter.addItem("영어 듣기 평가, 가정폭력, 아동학대 예방 교육", "2017.04.19 (수)");
                mAdapter.addItem("영어 듣기 평가", "2017.04.20 (목)");
                mAdapter.addItem("과학의 날 행사", "2017.04.21 (금)");
                mAdapter.addItem("1학기 1차 지필평가", "2017.04.25 (화)");
                mAdapter.addItem("1학기 1차 지필평가", "2017.04.26 (수)");
                mAdapter.addItem("1학기 1차 지필평가", "2017.04.27 (목)");
                mAdapter.addItem("1학기 1차 지필평가", "2017.04.28 (금)");
                break;
            case 5:
                mAdapter.addItem("체육대회", "2017.05.01 (월)");
                mAdapter.addItem("춘계 교외 체험학습", "2017.05.02 (화)");
                mAdapter.addItem("석가탄신일", "2017.05.03 (수)", true);
                mAdapter.addItem("재량휴업일 (개교기념일)", "2017.05.04 (목)");
                mAdapter.addItem("어린이날", "2017.05.05 (금)", true);
                mAdapter.addItem("어버이날", "2017.05.08 (월)");
                mAdapter.addItem("생명존중, 양성평등교육", "2017.05.10 (수)");
                mAdapter.addItem("과학 경시 대회", "2017.05.11 (목)");
                mAdapter.addItem("스승의 날", "2017.05.15 (월)");
                mAdapter.addItem("의사 결정 대회", "2017.05.23 (화)");
                mAdapter.addItem("나의 꿈 발표 대회", "2017.05.25 (목)");
                mAdapter.addItem("수업 공개의 날", "2017.05.26 (금)");
                mAdapter.addItem("가정의 날", "2017.05.31 (수)");
                break;
            case 6:
                mAdapter.addItem("전국 연합 학력 평가 (1,2학년), 대 수능 모의 평가 (3학년)", "2017.06.01 (목)");
                mAdapter.addItem("현충일", "2017.06.06 (화)", true);
                mAdapter.addItem("나라 사랑의 날 행사", "2017.06.07 (수)");
                mAdapter.addItem("영어 경시 대회", "2017.06.09 (금)");
                mAdapter.addItem("학업 성취도 평가", "2017.06.20 (화)");
                mAdapter.addItem("가정의 날", "2017.06.28 (수)");
                mAdapter.addItem("1학기 2차 지필평가", "2017.06.29 (목)");
                mAdapter.addItem("1학기 2차 지필평가", "2017.06.30 (금)");
                break;
            case 7:
                mAdapter.addItem("1학기 2차 지필평가", "2017.07.03 (월)");
                mAdapter.addItem("1학기 2차 지필평가", "2017.07.04 (화)");
                mAdapter.addItem("친구 사랑 편지 쓰기", "2017.07.05 (수)");
                mAdapter.addItem("미술 실기 대회", "2017.07.07 (금)");
                mAdapter.addItem("진로캠프", "2017.07.08 (토)");
                mAdapter.addItem("전국 연합 학력 평가 (3학년)", "2017.07.12 (수)");
                mAdapter.addItem("제헌절", "2017.07.17 (월)");
                mAdapter.addItem("통합 토론 대회, 여릅 방학식", "2017.07.18 (화)");
                mAdapter.addItem("여름 방학, 탄핵 인용시 여름 방학식", "2017.07.19 (수)");
                break;
            case 8:
                mAdapter.addItem("여름 방학, 3학년 개학", "2017.08.01 (화)");
                mAdapter.addItem("여름 방학, 광복절", "2017.08.15 (화)", true);
                mAdapter.addItem("1, 2학년 개학", "2017.08.16 (수)");
                mAdapter.addItem("가정폭력, 아동학대 예방 교육", "2017.08.23 (수)");
                mAdapter.addItem("2학기 1차 지필평가 (3학년)", "2017.08.28 (월)");
                mAdapter.addItem("2학기 1차 지필평가 (3학년)", "2017.08.29 (화)");
                mAdapter.addItem("2학기 1차 지필평가 (3학년)", "2017.08.30 (수)");
                mAdapter.addItem("2학기 1차 지필평가 (3학년)", "2017.08.31 (목)");
                break;
            case 9:
                mAdapter.addItem("9월 이후 일정은 학교에서 발표하는대로", "2017.09.01 (금)");
                mAdapter.addItem("추가할 예정이며, 9월 이후 일정은", "2017.09.01 (금)");
                mAdapter.addItem("[대한민국 공휴일]만 표시됩니다.", "2017.09.01 (금)");
                break;
            case 10:
                mAdapter.addItem("개천절, 추석 연휴", "2017.10.03 (화)", true);
                mAdapter.addItem("추석", "2017.10.04 (수)", true);
                mAdapter.addItem("추석 연휴", "2017.10.05 (목)", true);
                mAdapter.addItem("추석 연휴 (대체 휴일)", "2017.10.06 (금)", true);
                mAdapter.addItem("한글날", "2017.10.09 (월)", true);
                break;
            case 11:
                mAdapter.addItem("대학 수학 능력 시험", "2017.11.16 (목)", true);
                break;
            case 12:
                break;
            case 1:
                mAdapter.addItem("신정", "2018.01.01 (월)", true);
                break;
            case 2:
                break;
        }

        return recyclerView;
    }
}
