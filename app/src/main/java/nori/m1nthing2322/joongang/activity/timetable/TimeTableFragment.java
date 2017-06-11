package nori.m1nthing2322.joongang.activity.timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.tool.TimeTableTool;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class TimeTableFragment extends Fragment {

    public static Fragment getInstance(int mGrade, int mClass, int dayOfWeek) {
        TimeTableFragment mFragment = new TimeTableFragment();

        Bundle args = new Bundle();
        args.putInt("mGrade", mGrade);
        args.putInt("mClass", mClass);
        args.putInt("dayOfWeek", dayOfWeek);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        final TimeTableAdapter mAdapter = new TimeTableAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        Bundle args = getArguments();
        int mGrade = args.getInt("mGrade");
        int mClass = args.getInt("mClass");
        int dayOfWeek = args.getInt("dayOfWeek");

        TimeTableTool.timeTableData mData = TimeTableTool.getTimeTableData(mGrade, mClass, dayOfWeek);

        for (int position = 0; position < 7; position++) {
            mAdapter.addItem(position + 1, mData.subject[position], mData.room[position]);
        }

        return recyclerView;
    }
}
