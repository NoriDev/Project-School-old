package nori.m1nthing2322.joongang.activity.bap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nori.m1nthing2322.joongang.R;
import nori.m1nthing2322.joongang.tool.BapTool;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

class BapViewHolder {
    public TextView mCalender;
    public TextView mDayOfTheWeek;
    public TextView mLunch;
    public TextView mDinner;
//    public LinearLayout starLayout;
}

class BapListData {
    public String mCalender;
    public String mDayOfTheWeek;
    public String mLunch;
    public String mDinner;
    public boolean isToday;
}

public class BapAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<BapListData> mListData = new ArrayList<BapListData>();

    public BapAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void addItem(String mCalender, String mDayOfTheWeek, String mLunch, String mDinner, boolean isToday) {
        BapListData addItemInfo = new BapListData();
        addItemInfo.mCalender = mCalender;
        addItemInfo.mDayOfTheWeek = mDayOfTheWeek;
        addItemInfo.mLunch = mLunch;
        addItemInfo.mDinner = mDinner;
        addItemInfo.isToday = isToday;

        mListData.add(addItemInfo);
    }

    public void clearData() {
        mListData.clear();
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public BapListData getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        BapViewHolder mHolder;

        if (convertView == null) {
            mHolder = new BapViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_bap_item, null);

            mHolder.mCalender = (TextView) convertView.findViewById(R.id.mCalender);
            mHolder.mDayOfTheWeek = (TextView) convertView.findViewById(R.id.mDayOfTheWeek);
            mHolder.mLunch = (TextView) convertView.findViewById(R.id.mLunch);
            mHolder.mDinner = (TextView) convertView.findViewById(R.id.mDinner);
//            mHolder.starLayout = (LinearLayout) convertView.findViewById(R.id.starLayout);
            convertView.setTag(mHolder);
        } else {
            mHolder = (BapViewHolder) convertView.getTag();
        }

        BapListData mData = mListData.get(position);

        String mCalender = mData.mCalender;
        String mDayOfTheWeek = mData.mDayOfTheWeek;
        String mLunch = mData.mLunch;
        String mDinner = mData.mDinner;

        if (BapTool.mStringCheck(mLunch))
            mLunch = mData.mLunch = mContext.getResources().getString(R.string.no_data_lunch);
        if (BapTool.mStringCheck(mDinner))
            mDinner = mData.mDinner = mContext.getResources().getString(R.string.no_data_dinner);

        mHolder.mCalender.setText(mCalender);
        mHolder.mDayOfTheWeek.setText(mDayOfTheWeek);
        mHolder.mLunch.setText(mLunch);
        mHolder.mDinner.setText(mDinner);

/*        if (mData.isToday) {
            mHolder.starLayout.setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.giveStar).setOnClickListener(mStarListener);
            convertView.findViewById(R.id.showStar).setOnClickListener(mStarListener);
        } else {
            mHolder.starLayout.setVisibility(View.GONE);
        }
*/
        return convertView;
    }
}