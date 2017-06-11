package nori.m1nthing2322.joongang.activity.timetable;

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

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder> {
//    private int mBackground;
    private ArrayList<TimeTableInfo> mValues = new ArrayList<>();

    public TimeTableAdapter(Context mContext) {
//        TypedValue mTypedValue = new TypedValue();
//        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        mBackground = mTypedValue.resourceId;
    }

    public void addItem(int time, String name, String room) {
        TimeTableInfo addInfo = new TimeTableInfo();

        addInfo.time = time;
        addInfo.name = name;
//        addInfo.room = room;

        mValues.add(addInfo);
    }

    @Override
    public TimeTableAdapter.TimeTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_timetable_item, parent, false);
//        mView.setBackgroundResource(mBackground);

        return new TimeTableViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final TimeTableViewHolder holder, int position) {
        TimeTableInfo mInfo = getItemData(position);

        holder.mTimeText.setText(String.valueOf(mInfo.time));
        holder.mTimeName.setText(mInfo.name);
//        holder.mRoom.setText(mInfo.room);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public TimeTableInfo getItemData(int position) {
        return mValues.get(position);
    }

    public class TimeTableViewHolder extends RecyclerView.ViewHolder {
        //        public final View mView;
//        public final TextView mTimeText, mTimeName, mRoom;
        public final TextView mTimeText, mTimeName;

        public TimeTableViewHolder(View mView) {
            super(mView);
//            this.mView = mView;

            mTimeText = (TextView) mView.findViewById(R.id.mTimeText);
            mTimeName = (TextView) mView.findViewById(R.id.mTimeName);
//            mRoom = (TextView) mView.findViewById(R.id.mRoom);
        }
    }

    public class TimeTableInfo {
        public int time;
        public String name;
//        public String room;
    }
}
