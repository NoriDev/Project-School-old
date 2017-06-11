package nori.m1nthing2322.joongang.tool;

import android.database.Cursor;

import java.io.File;
import java.util.ArrayList;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class ExamTimeTool {
    public static final String ExamDBName = "ExamInfo.db";
    public static final String ExamTableName = "ExamInfo";
    public final static String mGoogleSpreadSheetUrl = "https://docs.google.com/spreadsheets/d/1b-0C78gwHjRSW6AtxdzTDZrazcO8Lus4Ph2n9gpRZik/pubhtml?gid=118310004&single=true";

    public static boolean fileExists() {
        return new File(TimeTableTool.mFilePath + ExamDBName).exists();
    }

    public static examData getExamInfoData() {
        Database mDatabase = new Database();
        mDatabase.openDatabase(TimeTableTool.mFilePath, ExamDBName);

        Cursor mCursor = mDatabase.getData(ExamTableName, "examdata");
        mCursor.moveToNext();

        examData mData = new examData();
        mData.date = mCursor.getString(0);

        mCursor.moveToNext();
        mData.type = mCursor.getString(0);

        mCursor.moveToNext();
        mData.days = mCursor.getString(0);

        return mData;
    }

    /**
     * type 0 : 인문
     * type 1 : 공학(자연)
     * position : 시험날짜
     */
    public static ArrayList<examTimeTableData> getExamTimeTable(int grade, int type, int position) {
        Database mDatabase = new Database();
        mDatabase.openDatabase(TimeTableTool.mFilePath, ExamDBName);

        Cursor mCursor = mDatabase.getData(ExamTableName, "position, date, " + (type == 0 ? "culture_" : "science_") + grade);
        ArrayList<examTimeTableData> mValues = new ArrayList<>();

        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToNext();

            int examPosition = Integer.parseInt(mCursor.getString(0));
            if (examPosition != position)
                continue;

            examTimeTableData mData = new examTimeTableData();

            mData.date = mCursor.getString(1);
            mData.subject = mCursor.getString(2);

            mValues.add(mData);
        }

        return mValues;
    }

    public static class examData {
        public String date, type, days;
    }

    public static class examTimeTableData {
        public String date, subject;
    }

}
