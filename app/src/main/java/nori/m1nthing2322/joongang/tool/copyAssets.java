package nori.m1nthing2322.joongang.tool;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class copyAssets {

    /**
     * @mFilePath = /data/data/wondang.icehs.kr.whdghks913.wondanghighschool/databases/
     * @mFileName = WondangTimeTable.db
     */
    public void assetsFileCopy(Context mContext, String mFilePath,
                               String mFileName) {

        File dbFile = new File(mFilePath + mFileName);

        File mFolder = new File(mFilePath);
        // 폴더도 없으면 폴더를 만든다
        if (!mFolder.exists())
            mFolder.mkdirs();

        AssetManager mAssetM = mContext.getResources().getAssets();
        InputStream mInput = null;
        FileOutputStream mOutput = null;
        long file_size = 0;
        dbFile.delete();

        try {
            mInput = mAssetM.open(mFileName, AssetManager.ACCESS_BUFFER);
            file_size = mInput.available();

            if (dbFile.length() <= 0) {
                byte[] tmpbyte = new byte[(int) file_size];
                mInput.read(tmpbyte);
                dbFile.createNewFile();
                mOutput = new FileOutputStream(dbFile);
                mOutput.write(tmpbyte);
            }

        } catch (IOException e) {
            // error
        } finally {
            try {
                if (mInput != null)
                    mInput.close();
                if (mOutput != null)
                    mOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

