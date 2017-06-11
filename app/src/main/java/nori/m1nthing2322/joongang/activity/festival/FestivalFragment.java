package nori.m1nthing2322.joongang.activity.festival;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nori.m1nthing2322.joongang.R;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class FestivalFragment extends Fragment {

    public static Fragment getInstance(int month) {
        FestivalFragment mFragment = new FestivalFragment();

        Bundle args = new Bundle();
        args.putInt("month", month);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        final FestivalAdapter mAdapter = new FestivalAdapter();
        recyclerView.setAdapter(mAdapter);

        Bundle args = getArguments();
        int month = args.getInt("month");

        switch (month) {
            case 1:
                mAdapter.addItem("수련활동 참여상 - 1학년", "3월");
                mAdapter.addItem("물리 경시대회 - 전교생 중 희망자", "4월");
                mAdapter.addItem("지구 과학 경시대회 - 전교생 중 희망자", "4월");
                mAdapter.addItem("과학의 날 행사 (독후감 쓰기, 글짓기, 포스터 그리기, 골든벨) - 전교생 중 희망자", "4월");
                mAdapter.addItem("진로 의사 결정대회 - 전교생 중 희망자", "5월");
                mAdapter.addItem("나의 꿈 발표대회 - 1, 2학년 학생 중 희망자", "5월");
                mAdapter.addItem("인문 사회 논술대회 - 전교생 중 희망자", "6월");
                mAdapter.addItem("나라사랑의 날 행사 - 전교생", "6월");
                mAdapter.addItem("수학 역량 대회 - 전교생", "6월");
                mAdapter.addItem("Joongang English Championship - 전교생", "6월");
                mAdapter.addItem("English Reading Activity - 3학년", "7월");
                mAdapter.addItem("미술 실기 대회 - 전교생", "7월");
                mAdapter.addItem("독서 대회 - 전교생 중 희망자", "7월");
                mAdapter.addItem("배논창 토론광장 - 전교생", "7월");
                mAdapter.addItem("유클리드(수학) 독서대회 - 전교생 중 희망자", "7월");
                mAdapter.addItem("수학체험 부스운영 - 전교생", "7월");
                mAdapter.addItem("수학 시(詩) 대회 - 전교생", "7월");
                mAdapter.addItem("수학 UCC 공모전 - 전교생 중 희망자", "7월");
                mAdapter.addItem("수학여행 일정 공모전 - 2학년", "7월");
                mAdapter.addItem("생명과학 경시대회 - 전교생 중 희망자", "9월");
                mAdapter.addItem("화학 경시대회 - 전교생 중 희망자", "9월");
                mAdapter.addItem("English Presentation Activity - 1학년", "10월");
                mAdapter.addItem("우리말 겨루기 대회 - 전교생 중 희망자", "10월");
                mAdapter.addItem("1인 1과제 연구활동 - 전교생 중 희망자", "11월");
                mAdapter.addItem("English Quiz Activity - 2학년", "11월");
                mAdapter.addItem("경제 올림피아드 - 전교생", "11월");
                mAdapter.addItem("한자 경시대회 - 1, 2학년", "12월");
                mAdapter.addItem("진로 포트폴리오 대회 - 1, 2학년", "12월");
                break;
/*
            case 2:
                mAdapter.addItem("개회식", "오전 9시");
                mAdapter.addItem("준비 체조", "오전 9시 20분");
                mAdapter.addItem("축구 - 1학년 축구 결승", "오전 9시 30분");
                mAdapter.addItem("족구 - 2학년 족구 예선", "오전 9시 30분");
                mAdapter.addItem("족구 - 3학년 족구 예선", "오전 9시 30분");
                mAdapter.addItem("축구 - 2학년 축구 결승", "오전 10시 30분");
                mAdapter.addItem("족구 - 1학년 족구 예선", "오전 10시 30분");
                mAdapter.addItem("족구 - 3학년 족구 결승", "오전 10시 30분");
                mAdapter.addItem("축구 - 3학년 축구 결승", "오전 11시 30분");
                mAdapter.addItem("족구 - 1학년 족구 결승", "오전 11시 30분");
                mAdapter.addItem("족구 - 1학년 족구 결승", "오전 11시 30분");
                mAdapter.addItem("점심시간", "오후 12시 30분 ~ 1시");
                mAdapter.addItem("사제 축구 - 학생회 간부 vs 남교사", "오후 1시 ~ 2시");
                mAdapter.addItem("플라잉디스크 윷놀이 - 여교사", "오후 1시 ~ 2시");
                mAdapter.addItem("400mR - 1, 2, 3학년", "오후 2시 ~ 2시 30분");
                mAdapter.addItem("시상식", "오후 2시 30분");
                break;
*/
        }
        return recyclerView;
    }
}
