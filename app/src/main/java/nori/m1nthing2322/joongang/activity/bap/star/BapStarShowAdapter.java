package nori.m1nthing2322.joongang.activity.bap.star;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

class BapStarShowViewHolder {
    public TextView mMemo;
}

class BapStarShowData {
    public String mText;
}

public class BapStarShowAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<BapStarShowData> mListData = new ArrayList<BapStarShowData>();

    public BapStarShowAdapter(Context mContext) {
        super();

        this.mContext = mContext;
    }

    public void addItem(String mText) {
        BapStarShowData addItemInfo = new BapStarShowData();
        addItemInfo.mText = mText;

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
    public BapStarShowData getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        BapStarShowViewHolder mHolder;

        if (convertView == null) {
            mHolder = new BapStarShowViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);

            mHolder.mMemo = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(mHolder);
        } else {
            mHolder = (BapStarShowViewHolder) convertView.getTag();
        }

        BapStarShowData mData = mListData.get(position);
        String mText = mData.mText;

        mHolder.mMemo.setText(mText);

        return convertView;
    }
}
