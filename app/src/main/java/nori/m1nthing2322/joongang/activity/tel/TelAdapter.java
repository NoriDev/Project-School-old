package nori.m1nthing2322.joongang.activity.tel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nori.m1nthing2322.joongang.R;

/**
 * Created by whdghks913 on 2015-12-10.
 */
class NoticeViewHolder {
    public TextView mTitle;
    public TextView mMessage;
}

class NoticeShowData {
    public String title;
    public String message;
}

public class TelAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<NoticeShowData> mListData = new ArrayList<NoticeShowData>();

    public TelAdapter(Context mContext) {
        super();

        this.mContext = mContext;
    }

    public void addItem(String title, String message) {
        NoticeShowData addItemInfo = new NoticeShowData();
        addItemInfo.title = title;
        addItemInfo.message = message;

        mListData.add(0, addItemInfo);
    }

    public void clearData() {
        mListData.clear();
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public NoticeShowData getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        NoticeViewHolder mHolder;

        if (convertView == null) {
            mHolder = new NoticeViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_tel_item, null);

            mHolder.mTitle = (TextView) convertView.findViewById(R.id.mTitle);
            mHolder.mMessage = (TextView) convertView.findViewById(R.id.mMessage);

            convertView.setTag(mHolder);
        } else {
            mHolder = (NoticeViewHolder) convertView.getTag();
        }

        NoticeShowData mData = mListData.get(position);
        String title = mData.title;
        String message = mData.message;

        mHolder.mTitle.setText(title);
        mHolder.mMessage.setText(message);

        return convertView;
    }
}
