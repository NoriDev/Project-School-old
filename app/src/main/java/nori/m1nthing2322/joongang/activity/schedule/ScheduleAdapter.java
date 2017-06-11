package nori.m1nthing2322.joongang.activity.schedule;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    //    private int mBackground;
    private ArrayList<ScheduleInfo> mValues = new ArrayList<>();

//    public ScheduleAdapter(Context mContext) {
//        TypedValue mTyp/edValue = new TypedValue();
//        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        mBackground = mTypedValue.resourceId;
//    }

    public void addItem(String mSchedule, String mDate) {
        ScheduleInfo addInfo = new ScheduleInfo();

        addInfo.date = mDate;
        addInfo.schedule = mSchedule;
        addInfo.isHoliday = false;

        mValues.add(addInfo);
    }

    public void addItem(String mSchedule, String mDate, boolean isHoliday) {
        ScheduleInfo addInfo = new ScheduleInfo();

        addInfo.date = mDate;
        addInfo.schedule = mSchedule;
        addInfo.isHoliday = isHoliday;

        mValues.add(addInfo);
    }

    @Override
    public ScheduleAdapter.ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_schedule_item, parent, false);
//        mView.setBackgroundResource(mBackground);

        return new ScheduleViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ScheduleViewHolder holder, int position) {
        ScheduleInfo mInfo = getItemData(position);

        holder.mDate.setText(mInfo.date);
        holder.mSchedule.setText(mInfo.schedule);

        if (mInfo.isHoliday) {
            holder.mDate.setTextColor(Color.RED);
        } else {
            holder.mDate.setTextColor(ContextCompat.getColor(holder.mDate.getContext(), R.color.colorSecondaryText));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ScheduleInfo getItemData(int position) {
        return mValues.get(position);
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        //        public final View mView;
        public final TextView mDate, mSchedule;

        public ScheduleViewHolder(View mView) {
            super(mView);
//            this.mView = mView;

            mSchedule = (TextView) mView.findViewById(R.id.list_item_entry_title);
            mDate = (TextView) mView.findViewById(R.id.list_item_entry_summary);
        }
    }

    public class ScheduleInfo {
        public String date;
        public String schedule;
        public boolean isHoliday;
    }
}
