package nori.m1nthing2322.joongang.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.activity.bap.BapActivity;
import nori.m1nthing2322.joongang.activity.changelog.ChangelogBetaActivity;
import nori.m1nthing2322.joongang.activity.exam.ExamTimeActivity;
import nori.m1nthing2322.joongang.activity.festival.FestivalActivity;
import nori.m1nthing2322.joongang.activity.notice.NoticeActivity;
import nori.m1nthing2322.joongang.activity.notice.SCNoticeActivity;
import nori.m1nthing2322.joongang.activity.schedule.ScheduleActivity;
import nori.m1nthing2322.joongang.activity.tel.TelActivity;
import nori.m1nthing2322.joongang.activity.timetable.TimeTableActivity;
import nori.m1nthing2322.joongang.tool.BapTool;
import nori.m1nthing2322.joongang.tool.Preference;
import nori.m1nthing2322.joongang.tool.RecyclerItemClickListener;
import nori.m1nthing2322.joongang.tool.TimeTableTool;

// import nori.m1nthing2322.joongang.activity.changelog.ChangelogBetaActivity;

/**
 * Created by whdghks913 on 2015-11-30.
 */
public class MainFragment extends Fragment {

    public static Fragment getInstance(int code) {
        MainFragment mFragment = new MainFragment();

        Bundle args = new Bundle();
        args.putInt("code", code);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        final MainAdapter mAdapter = new MainAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View mView, int position) {
                Bundle args = getArguments();
                int code = args.getInt("code");

                if (code == 1) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), BapActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(getActivity(), TimeTableActivity.class));
                            break;
                    }
                }
                if (code == 2) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), SCNoticeActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(getActivity(), NoticeActivity.class));
                            break;
                        case 2:
//                            startActivity(new Intent(getActivity(), ChangelogActivity.class));
                            startActivity(new Intent(getActivity(), ChangelogBetaActivity.class));
                            break;
                    }
                }
                if (code == 3) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), ExamTimeActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(getActivity(), ScheduleActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(getActivity(), FestivalActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(getActivity(), TelActivity.class));
                            break;
                    }
                }

        }}));

        Bundle args = getArguments();
        int code = args.getInt("code");
        Preference mPref = new Preference(getActivity());

        if (code == 1) {
            if (mPref.getBoolean("simpleShowBap", true)) {
                BapTool.todayBapData mBapData = BapTool.getTodayBap(getActivity());
                mAdapter.addItem(R.drawable.rice,
                        getString(R.string.title_activity_bap),
                        getString(R.string.message_activity_bap),
                        mBapData.title,
                        mBapData.info);
            } else {
                mAdapter.addItem(R.drawable.rice,
                        getString(R.string.title_activity_bap),
                        getString(R.string.message_activity_bap), true);
            }
            if (mPref.getBoolean("simpleShowTimeTable", true)) {
                TimeTableTool.todayTimeTableData mTimeTableData = TimeTableTool.getTodayTimeTable(getActivity());
                mAdapter.addItem(R.drawable.timetable,
                        getString(R.string.title_activity_time_table),
                        getString(R.string.message_activity_time_table),
                        mTimeTableData.title,
                        mTimeTableData.info);
            } else {
                mAdapter.addItem(R.drawable.timetable,
                        getString(R.string.title_activity_time_table),
                        getString(R.string.message_activity_time_table), true);
            }
        }
        if (code == 2) {
			mAdapter.addItem(R.drawable.notice,
			    	getString(R.string.title_activity_scnotice),
				    getString(R.string.message_activity_scnotice));
            mAdapter.addItem(R.drawable.notice,
                    getString(R.string.title_activity_notice),
                    getString(R.string.message_activity_notice));
            mAdapter.addItem(R.drawable.notice,
                    getString(R.string.title_activity_changelog_beta),
                    getString(R.string.message_activity_changelog));
            }
        if (code == 3) {
            mAdapter.addItem(R.drawable.exam,
                    getString(R.string.title_activity_exam_range),
                    getString(R.string.message_activity_exam_range));
            mAdapter.addItem(R.drawable.calendar,
                    getString(R.string.title_activity_schedule),
                    getString(R.string.message_activity_schedule));
            mAdapter.addItem(R.drawable.events,
                    getString(R.string.title_activity_festival),
                    getString(R.string.message_activity_festival));
            mAdapter.addItem(R.drawable.phone,
                    getString(R.string.title_activity_tel),
                    getString(R.string.message_activity_tel));
            }
        return recyclerView;
    }
}
