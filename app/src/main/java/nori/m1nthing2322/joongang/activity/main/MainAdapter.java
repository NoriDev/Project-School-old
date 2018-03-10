package nori.m1nthing2322.joongang.activity.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    //    private int mBackground;
    private ArrayList<MainInfo> mValues = new ArrayList<>();

    public MainAdapter(Context mContext) {
//        TypedValue mTypedValue = new TypedValue();
//        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        mBackground = mTypedValue.resourceId;
    }

    public void addItem(int imageId, String mTitle, String mText) {
        MainInfo addInfo = new MainInfo();

        addInfo.imageId = imageId;
        addInfo.mTitle = mTitle;
        addInfo.mText = mText;
        addInfo.isSimple = false;
        addInfo.isSimpleButDetailedLayout = false;

        mValues.add(addInfo);
    }

    public void addItem(int imageId, String mTitle, String mText, boolean isSimple) {
        MainInfo addInfo = new MainInfo();

        addInfo.imageId = imageId;
        addInfo.mTitle = mTitle;
        addInfo.mText = mText;
        addInfo.isSimple = true;
        addInfo.isSimpleButDetailedLayout = isSimple;

        mValues.add(addInfo);
    }

    public void addItem(int imageId, String mTitle, String mText, String mSimpleTitle, String mSimpleText) {
        MainInfo addInfo = new MainInfo();

        addInfo.imageId = imageId;
        addInfo.mTitle = mTitle;
        addInfo.mText = mText;
        addInfo.mSimpleTitle = mSimpleTitle;
        addInfo.mSimpleText = mSimpleText;
        addInfo.isSimple = true;
        addInfo.isSimpleButDetailedLayout = false;

        mValues.add(addInfo);
    }

    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_fragment, parent, false);
//        mView.setBackgroundResource(mBackground);

        return new MainViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, int position) {
        MainInfo mInfo = getItemData(position);

        holder.mTitle.setText(mInfo.mTitle);
//        holder.mText.setText(mInfo.mText);

        if (mInfo.isSimple && !(mInfo.isSimpleButDetailedLayout)) {
            holder.mSimpleLayout.setVisibility(View.VISIBLE);
            holder.mSimpleTitle.setText(mInfo.mSimpleTitle);
            holder.mSimpleText.setText(mInfo.mSimpleText);
        } else {
            holder.mSimpleLayout.setVisibility(View.GONE);
        }

/*        Glide.with(holder.mImageView.getContext())
                .load(mInfo.imageId)
                //.fitCenter()
                .into(holder.mImageView); */

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public MainInfo getItemData(int position) {
        return mValues.get(position);
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final ImageView mImageView;
        public final LinearLayout mSimpleLayout;
        public final TextView mTitle, mSimpleTitle, mSimpleText;

        public MainViewHolder(View mView) {
            super(mView);
//            this.mView = mView;

//            mImageView = mView.findViewById(R.id.mImageView);
            mTitle = mView.findViewById(R.id.mTitle);
//            mText = mView.findViewById(R.id.mText);
            mSimpleLayout = mView.findViewById(R.id.mSimpleLayout);
            mSimpleTitle = mView.findViewById(R.id.mSimpleTitle);
            mSimpleText = mView.findViewById(R.id.mSimpleText);
        }
    }

    public class MainInfo {
        public boolean isSimple;
        public boolean isSimpleButDetailedLayout = false;
        public int imageId;
        public String mTitle;
        public String mText;
        public String mSimpleTitle;
        public String mSimpleText;
    }
}
