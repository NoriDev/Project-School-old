package nori.m1nthing2322.joongang.activity.exam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.tool.ExamTimeTool;

/**
 * Created by whdghks913 on 2015-12-10.
 */
public class ExamTimeFragment extends Fragment {

    public static Fragment getInstance(int grade, int type, int position) {
        ExamTimeFragment mFragment = new ExamTimeFragment();

        Bundle args = new Bundle();
        args.putInt("grade", grade);
        args.putInt("type", type);
        args.putInt("position", position);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        final ExamTimeAdapter mAdapter = new ExamTimeAdapter();
        recyclerView.setAdapter(mAdapter);

        Bundle args = getArguments();
        int grade = args.getInt("grade");
        int type = args.getInt("type");
        int position = args.getInt("position");

        ArrayList<ExamTimeTool.examTimeTableData> mValues = ExamTimeTool.getExamTimeTable(grade, type, position);
        for (int i = 0; i < mValues.size(); i++) {
            ExamTimeTool.examTimeTableData mData = mValues.get(i);
            mAdapter.addItem(i + 1, mData.subject);
        }

        return recyclerView;
    }
}
