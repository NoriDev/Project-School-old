package nori.m1nthing2322.joongang.tool;

import android.database.Cursor;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by whdghks913 on 2015-12-10.
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
     * type 1 : 자연
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
