package nori.m1nthing2322.joongang.activity.notice;

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
    public TextView mDate;
}

class NoticeShowData {
    public String title;
    public String message;
    public String date;
}

public class NoticeAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<NoticeShowData> mListData = new ArrayList<NoticeShowData>();

    public NoticeAdapter(Context mContext) {
        super();

        this.mContext = mContext;
    }

    public void addItem(String title, String message, String date) {
        NoticeShowData addItemInfo = new NoticeShowData();
        addItemInfo.title = title;
        addItemInfo.message = message;
        addItemInfo.date = date;

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
            convertView = inflater.inflate(R.layout.row_notice_item, null);

            mHolder.mTitle = (TextView) convertView.findViewById(R.id.mTitle);
            mHolder.mMessage = (TextView) convertView.findViewById(R.id.mMessage);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.mDate);

            convertView.setTag(mHolder);
        } else {
            mHolder = (NoticeViewHolder) convertView.getTag();
        }

        NoticeShowData mData = mListData.get(position);
        String title = mData.title;
        String message = mData.message;
        String date = mData.date;

        mHolder.mTitle.setText(title);
        mHolder.mMessage.setText(message);
        mHolder.mDate.setText(date);

        return convertView;
    }
}
