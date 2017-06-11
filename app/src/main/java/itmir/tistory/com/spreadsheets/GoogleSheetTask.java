package itmir.tistory.com.spreadsheets;

import android.os.AsyncTask;
import android.util.Log;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.net.URL;
import java.util.List;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public abstract class GoogleSheetTask extends AsyncTask<String, Integer, Long> {
    /**
     * --------- Row
     * --------- Row
     * | | | | | Column
     * | | | | | Column
     */
    private int startRowNumber = 0;
    private int startColumnNumber = 0;

    public abstract void onPreDownload();

//    public abstract void onUpdate(int params);

    public abstract void onFinish(long result);

    public abstract void onRow(int startRowNumber, int position, String[] row);

    protected void setStartRowNumber(int startRowNumber) {
        this.startRowNumber = startRowNumber;
    }

    public void setStartColumnNumber(int startColumnNumber) {
        this.startColumnNumber = startColumnNumber;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPreDownload();
    }

    @Override
    protected Long doInBackground(String... params) {
        try {
            Source mSource = null;

            mSource = new Source(new URL(params[0]));
            mSource.fullSequentialParse();

            List<Element> tables = mSource.getAllElements(HTMLElementName.TABLE);
            Element table = tables.get(0);

            if (table.getAttributeValue("class").equals("waffle")) {
                Element tbody = table.getAllElements(HTMLElementName.TBODY).get(0);

                List<Element> tr = tbody.getAllElements(HTMLElementName.TR);

                int tr_size = tr.size();
                for (int i = startRowNumber; i < tr_size; i++) {
                    List<Element> td = tr.get(i).getAllElements(HTMLElementName.TD);
                    String[] sheetData = new String[td.size()];

                    int td_size = td.size();
                    for (int j = startColumnNumber; j < td_size; j++) {
                        Element data = td.get(j);
                        String mRow = data.getContent().toString().trim();
                        mRow = mRow.replace("<br>", "\n");
                        sheetData[j] = removeHTMLTag(mRow);
                    }

                    onRow(startRowNumber, i, sheetData);
                }
            }

        } catch (Exception e) {
            Log.e("GoogleSheetTask Error", "Message : " + e.getMessage());
            Log.e("GoogleSheetTask Error", "LocalizedMessage : " + e.getLocalizedMessage());

            e.printStackTrace();
            return -1L;
        }
        return 0L;
    }

    @Override
    protected void onProgressUpdate(Integer... params) {
//        onUpdate(params[0]);
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
        onFinish(result);
    }

    private String removeHTMLTag(String changeStr) {
        if (changeStr != null && !changeStr.equals("")) {
            changeStr = changeStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        } else {
            changeStr = "";
        }
        return changeStr;
    }
}
