package com.example.juliemanager.function.edit;

import android.os.AsyncTask;

import com.example.juliemanager.callback.NotifyFileAdapterCallback;
import com.example.juliemanager.data.FileItem;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by julie on 2019-11-22
 * 즐겨찾기 ON인 경우 파일 리스트에서 삭제하는 클래스
 */
public class FileDeleteAsync extends AsyncTask {
    private ArrayList<FileItem> fileItems;
    private NotifyFileAdapterCallback notifyFileAdapterCallback;

    public FileDeleteAsync(ArrayList<FileItem> fileItems, NotifyFileAdapterCallback notifyFileAdapterCallback) {
        this.fileItems = fileItems;
        this.notifyFileAdapterCallback = notifyFileAdapterCallback;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        boolean isSuccess = false;    // TODO: 2019-11-25 추후 삭제 실패 예외 처리해야함.
        for (int i = fileItems.size() - 1; i >= 0; i--) {
            //즐겨찾기 ON일때 해당 파일 삭제
            if (fileItems.get(i).getFileFavorite()) {
                File file = new File(fileItems.get(i).getFilePath());
                if (file.exists()) {
                    //파일 삭제
                    isSuccess = file.delete();
                    //리스트 데이터에서 삭제
                    if (isSuccess) fileItems.remove(i);
                }
            }
        }

        return isSuccess;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        notifyFileAdapterCallback.notifyAdapter();
    }
}
