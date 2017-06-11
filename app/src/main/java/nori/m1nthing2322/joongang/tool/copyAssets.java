package nori.m1nthing2322.joongang.tool;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 이 소스는 원작자: Mir(whdghks913)에 의해 생성되었으며,
 * 2차 수정자: NoriDev(noridevdroid@gmail.com)에 의해 수정되었습니다.
 *
 * 본 소스를 사용하고자 한다면, 이 주석을 삭제 또는 수정해서는 안됩니다.
 * 또한 앱 내부 및 스토어 등록 정보에서 다른 사람이 볼 수 있는 곳에 적어도 하나 이상의 위치에 위 저작자가 표시되어야 합니다.
 */

public class copyAssets {

    /**
     * @mFilePath = /data/data/nori.m1nthing2322.joongang/databases/
     * @mFileName = JinhaeJoongangHSTimeTable.db
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

