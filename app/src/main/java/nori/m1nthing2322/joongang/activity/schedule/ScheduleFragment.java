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
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
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

                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                    int diffDaysFix = (int) (diff / (24 * 60 * 60 * 1000) + 1);
                    String mText = "";

                    if (diffDays == 0)
                        mText += "오늘 일정입니다.";
                    else if (isPast)
                        mText = "선택하신 날짜는 " + diffDays + "일 전 날짜입니다";
                    else
                        mText = "선택하신 날짜까지 " + diffDaysFix + "일 남았습니다";

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
                mAdapter.addItem("3.1절", "2018.03.01 (목)", true);
                mAdapter.addItem("입학식 및 시업식, 청렴의 날", "2018.03.02 (금)");
                mAdapter.addItem("신입생 오리엔테이션 (부서별)", "2018.03.05 (월)");
                mAdapter.addItem("신입생 오리엔테이션 (교과별)", "2018.03.06 (화)");
                mAdapter.addItem("다문화이해교육, 양성평등교육", "2018.03.07 (수)");
                mAdapter.addItem("전국 연합 학력평가", "2018.03.08 (목)");
                mAdapter.addItem("성폭력 예방 교육", "2018.03.12 (월)");
                mAdapter.addItem("수련회 (1학년)", "2018.03.14 (수)");
                mAdapter.addItem("수련회 (1학년)", "2018.03.15 (목)");
                mAdapter.addItem("수련회 (1학년)", "2018.03.16 (금)");
                mAdapter.addItem("학생회 임명, 학교폭력 추방의 날, 금연 선포식", "2018.03.19 (월)");
                mAdapter.addItem("학교폭력예방교육 (초청)", "2018.03.21 (수)");
                mAdapter.addItem("학부모회", "2018.03.22 (목)");
                mAdapter.addItem("학교교육계획연수", "2018.03.26 (월)");
                mAdapter.addItem("가정의 날", "2018.03.28 (수)");
                break;
            case 4:
                mAdapter.addItem("청렴의 날", "2018.04.02 (월)");
                mAdapter.addItem("인성(인권) (1, 2학년) 장애이해교육 (3학년)", "2018.04.04 (수)");
                mAdapter.addItem("정보통신윤리교육", "2018.04.09 (금)");
                mAdapter.addItem("전국 연합 학력평가 (3학년)", "2018.04.11 (수)");
                mAdapter.addItem("진로 적성 검사 (1, 2학년)", "2018.04.12 (목)");
                mAdapter.addItem("과학의 날 행사", "2018.04.16 (월)");
                mAdapter.addItem("영어 듣기평가 (1학년)", "2018.04.17 (화)");
                mAdapter.addItem("영어 듣기평가 (2학년), 가정의 날", "2018.04.18 (수)");
                mAdapter.addItem("영어 듣기평가 (3학년)", "2018.04.19 (목)");
                mAdapter.addItem("건강 체력평가", "2018.04.20 (금)");
                mAdapter.addItem("1학기 1차 지필평가", "2018.04.24 (화)");
                mAdapter.addItem("1학기 1차 지필평가", "2018.04.25 (수)");
                mAdapter.addItem("1학기 1차 지필평가", "2018.04.26 (목)");
                mAdapter.addItem("1학기 1차 지필평가", "2018.04.27 (금)");
                mAdapter.addItem("체육대회", "2018.04.30 (월)");
                break;
            case 5:
                mAdapter.addItem("춘계 교외 체험학습 (근로자의 날)", "2018.05.01 (화)");
                mAdapter.addItem("청렴의 날", "2018.05.02 (수)");
                mAdapter.addItem("어린이날", "2018.05.05 (토)", true);
                mAdapter.addItem("대체공휴일", "2018.05.07 (월)", true);
                mAdapter.addItem("재량 휴업일 (개교기념일)", "2018.05.08 (화)");
                mAdapter.addItem("지진·화재·교통안전교육", "2018.05.09 (수)");
                mAdapter.addItem("정보통신윤리교육", "2018.05.14 (월)");
                mAdapter.addItem("스승의 날", "2018.05.15 (화)");
                mAdapter.addItem("생명존중, 양성평등교육", "2018.05.16 (수)");
                mAdapter.addItem("나의 꿈 발표 대회", "2018.05.21 (월)");
                mAdapter.addItem("부처님 오신 날", "2018.05.22 (화)", true);
                mAdapter.addItem("가정폭력, 아동학대예방교육", "2018.05.23 (수)");
                mAdapter.addItem("수업 공개의 날", "2018.05.25 (금)");
                mAdapter.addItem("진로탐색대회", "2018.05.28 (월)");
                mAdapter.addItem("가정의 날", "2018.05.30 (수)");
                break;
            case 6:
                mAdapter.addItem("청렴의 날", "2018.06.01 (금)");
                mAdapter.addItem("수학 페스티벌", "2018.06.02 (토)");
                mAdapter.addItem("정보통신윤리교육", "2018.06.04 (월)");
                mAdapter.addItem("나라 사랑의 날 행사", "2018.06.05 (화)");
                mAdapter.addItem("현충일", "2018.06.06 (수)", true);
                mAdapter.addItem("전국 연합 학력평가 (1, 2학년), 대수능 모의평가 (3학년)", "2018.06.07 (목)");
                mAdapter.addItem("영어 경시 대회", "2018.06.12 (화)");
                mAdapter.addItem("지방선거", "2018.06.13 (수)", true);
                mAdapter.addItem("정보통신윤리교육", "2018.06.18 (월)");
                mAdapter.addItem("흡연예방(금연)교육", "2018.06.20 (수)");
                mAdapter.addItem("가정의 날", "2018.06.27 (수)");
                mAdapter.addItem("1학기 2차 지필평가", "2018.06.28 (목)");
                mAdapter.addItem("1학기 2차 지필평가", "2018.06.29 (금)");
                break;
            case 7:
                mAdapter.addItem("1학기 2차 지필평가, 청렴의 날", "2018.07.02 (월)");
                mAdapter.addItem("1학기 2차 지필평가", "2018.07.03 (화)");
                mAdapter.addItem("성폭력예방교육", "2018.07.04 (수)");
                mAdapter.addItem("과학 경시 대회 (물리, 지구과학)", "2018.07.05 (목)");
                mAdapter.addItem("친구사랑편지쓰기", "2018.07.06 (금)");
                mAdapter.addItem("진로캠프", "2018.07.07 (토)");
                mAdapter.addItem("미술 실기 대회", "2018.07.09 (월)");
                mAdapter.addItem("독서대회", "2018.07.10 (화)");
                mAdapter.addItem("전국 연합 학력 평가 (3학년)", "2018.07.11 (수)");
                mAdapter.addItem("인문 사회 논술대회", "2018.07.12 (목)");
                mAdapter.addItem("한자 활용 대회", "2018.07.13 (금)");
                mAdapter.addItem("1사1교 금융교육 (1학년)", "2018.07.16 (월)");
                mAdapter.addItem("제헌절", "2018.07.17 (화)");
                mAdapter.addItem("통합 토론 대회", "2018.07.18 (수)");
                mAdapter.addItem("여름 방학식", "2018.07.20 (금)");
                break;
            case 8:
                mAdapter.addItem("여름 방학, 3학년 개학", "2018.08.08 (수)");
                mAdapter.addItem("1, 2학년 개학", "2018.08.13 (월)");
                mAdapter.addItem("광복절", "2018.08.15 (수)", true);
                mAdapter.addItem("가정폭력·아동학대 예방 교육, 학교폭력 예방 교육", "2018.08.22 (수)");
                mAdapter.addItem("2학기 1차 지필평가 (3학년)", "2018.08.27 (월)");
                mAdapter.addItem("2학기 1차 지필평가 (3학년)", "2018.08.28 (화)");
                mAdapter.addItem("2학기 1차 지필평가 (3학년), 도박예방교육 (1, 2학년)", "2018.08.29 (수)");
                mAdapter.addItem("2학기 1차 지필평가 (3학년)", "2018.08.30 (목)");
                break;
            case 9:
                mAdapter.addItem("청렴의 날, 동문과의 만남 (직업인 초청)", "2018.09.03 (월)");
                mAdapter.addItem("전국 연합 학력평가 (1, 2학년), 대수능 모의평가 (3학년)", "2018.09.05 (수)");
                mAdapter.addItem("학부모회", "2018.09.06 (목)");
                mAdapter.addItem("정보통신윤리교육", "2018.09.10 (월)");
                mAdapter.addItem("지진·화재·교통안전교육", "2018.09.12 (수)");
                mAdapter.addItem("추계 교외 체험활동 (1, 3학년), 전공체험활동 (2학년)", "2018.09.14 (금)");
                mAdapter.addItem("영어 듣기평가 (1학년)", "2018.09.18 (화)");
                mAdapter.addItem("영어 듣기평가 (2학년), 가정의 날", "2018.09.19 (수)");
                mAdapter.addItem("영어 듣기평가 (3학년)", "2018.09.20 (목)");
                mAdapter.addItem("수업 공개의 날", "2018.09.21 (금)");
                mAdapter.addItem("가정의 날", "2018.09.27 (수)");
                mAdapter.addItem("추석", "2018.09.24 (월)", true);
                mAdapter.addItem("추석 연휴", "2018.09.25 (화)", true);
                mAdapter.addItem("대체공휴일", "2018.09.26 (수)", true);
                break;
            case 10:
                mAdapter.addItem("흡연예방(금연)교육", "2018.10.01 (월)");
                mAdapter.addItem("청렴의 날", "2018.10.02 (화)");
                mAdapter.addItem("개천절", "2018.10.03 (수)", true);
                mAdapter.addItem("2학기 1차 지필평가 (1, 2학년), 2학기 2차 지필평가 (3학년)", "2018.10.08 (월)");
                mAdapter.addItem("한글날", "2018.10.09 (화)", true);
                mAdapter.addItem("2학기 1차 지필평가 (1, 2학년), 2학기 2차 지필평가 (3학년)", "2018.10.10 (수)");
                mAdapter.addItem("2학기 1차 지필평가 (1, 2학년), 2학기 2차 지필평가 (3학년)", "2018.10.11 (목)");
                mAdapter.addItem("2학기 1차 지필평가 (1, 2학년), 2학기 2차 지필평가 (3학년)", "2018.10.12 (금)");
                mAdapter.addItem("우리말 겨루기 대회", "2018.10.15 (월)");
                mAdapter.addItem("전국 연합 학력평가 (3학년)", "2018.10.16 (화)");
                mAdapter.addItem("성폭력 예방 교육", "2018.10.17 (수)");
                mAdapter.addItem("수학여행 (2학년)", "2018.10.23 (화)");
                mAdapter.addItem("수학여행 (2학년)", "2018.10.24 (수)");
                mAdapter.addItem("수학여행 (2학년)", "2018.10.25 (목)");
                mAdapter.addItem("수학여행 (2학년)", "2018.10.26 (금)");
                mAdapter.addItem("정보통신윤리교육", "2018.10.29 (월)");
                mAdapter.addItem("가정의 날", "2018.10.31 (수)");
                break;
            case 11:
                mAdapter.addItem("청렴의 날", "2018.11.01 (목)");
                mAdapter.addItem("다문화이해교육, 양성평등교육", "2018.11.05 (월)");
                mAdapter.addItem("성매매예방교육", "2018.11.07 (수)");
                mAdapter.addItem("경제체험대회", "2018.11.09 (금)");
                mAdapter.addItem("진로직업체험", "2018.11.10 (토)");
                mAdapter.addItem("정보통신윤리교육", "2018.11.12 (월)");
                mAdapter.addItem("대학수학능력시험", "2018.11.15 (목)", true);
                mAdapter.addItem("전국 연합 학력평가 (1, 2학년), 도박중독예방교육 (3학년)", "2018.11.21 (수)");
                mAdapter.addItem("가정의 날", "2018.11.28 (수)");
                break;
            case 12:
                mAdapter.addItem("청렴의 날", "2018.12.03 (월)");
                mAdapter.addItem("2학기 2차 지필평가 (1, 2학년)", "2018.12.04 (화)");
                mAdapter.addItem("2학기 2차 지필평가 (1, 2학년)", "2018.12.05 (수)");
                mAdapter.addItem("2학기 2차 지필평가 (1, 2학년)", "2018.12.06 (목)");
                mAdapter.addItem("2학기 2차 지필평가 (1, 2학년)", "2018.12.07 (금)");
                mAdapter.addItem("신입생 원서 접수", "2018.12.10 (월)");
                mAdapter.addItem("장애이해교육(1, 2학년), 인성(인권)교육 (3학년)", "2018.12.12 (수)");
                mAdapter.addItem("과학경시대회 (생물, 화학)", "2018.12.13 (목)");
                mAdapter.addItem("지리 체험 나눔의 날, 진로 포트폴리오 대회", "2018.12.14 (금)");
                mAdapter.addItem("1사1교금융교육 (2학년)", "2018.12.17 (월)");
                mAdapter.addItem("신입생 전형", "2018.12.18 (화)");
                mAdapter.addItem("생명존중교육, 양성평등교육", "2018.12.19 (수)");
                mAdapter.addItem("신입생 합격자 발표", "2018.12.20 (목)");
                mAdapter.addItem("장복제", "2018.12.24 (월)");
                mAdapter.addItem("성탄절", "2018.12.25 (화)", true);
                mAdapter.addItem("가정의 날", "2018.12.26 (수)");
                mAdapter.addItem("겨울 방학식", "2018.12.28 (금)");
                break;
            case 1:
                mAdapter.addItem("신정", "2019.01.01 (화)", true);
                mAdapter.addItem("개학", "2019.02.29 (화)");
                break;
            case 2:
                mAdapter.addItem("졸업식, 종업식", "2019.02.01 (금)");
                mAdapter.addItem("설 연휴", "2019.02.04 (월)", true);
                mAdapter.addItem("설날", "2019.02.05 (화)", true);
                mAdapter.addItem("설 연휴", "2019.02.06 (수)", true);
                break;
        }
        return recyclerView;
    }
}
