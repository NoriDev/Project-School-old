package nori.m1nthing2322.joongang.activity.exam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nori.m1nthing2322.joongang.R;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class ExamTimeAdapter extends RecyclerView.Adapter<ExamTimeAdapter.ExamTimeViewHolder> {
    private ArrayList<ExamTimeInfo> mValues = new ArrayList<>();

    public void addItem(int time, String subject) {
        ExamTimeInfo addInfo = new ExamTimeInfo();

        addInfo.time = time;
        addInfo.subject = subject;

        mValues.add(addInfo);
    }

    @Override
    public ExamTimeAdapter.ExamTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_exam_item, parent, false);

        return new ExamTimeViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ExamTimeViewHolder holder, int position) {
        ExamTimeInfo mInfo = getItemData(position);

        holder.mTime.setText(String.valueOf(mInfo.time));
        holder.mSubject.setText(mInfo.subject);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ExamTimeInfo getItemData(int position) {
        return mValues.get(position);
    }

    public class ExamTimeViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final TextView mTime, mSubject;

        public ExamTimeViewHolder(View mView) {
            super(mView);

            mContext = mView.getContext();
            mTime = (TextView) mView.findViewById(R.id.list_item_entry_title);
            mSubject = (TextView) mView.findViewById(R.id.list_item_entry_summary);
        }
    }

    public class ExamTimeInfo {
        public int time;
        public String subject;
    }
}
