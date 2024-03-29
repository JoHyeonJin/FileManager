package com.example.juliemanager.function;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.juliemanager.R;
import com.example.juliemanager.data.FileItem;
import com.example.juliemanager.listener.FileListener;
import com.example.juliemanager.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by julie on 2019-10-15
 * 파일 처리를 위한 함수 모음 클래스
 */
public class FileFunction {
    public static FileFunction instance = new FileFunction();
    private ArrayList<FileItem> fileItems;

    public FileFunction() {
        this.fileItems = new ArrayList<>();
    }

    public static FileFunction getInstance() {
        return instance;
    }

    public ArrayList<FileItem> getFileItems() {
        return fileItems;
    }

    /**
     * 파일 선택 시 뷰어 열기
     *
     * @param context    컨텍스트
     * @param selectItem 선택한 파일
     */
    public void showFileViewer(Context context, FileItem selectItem) {
        //버전 7.0 부터 앱 사이 공유 엄격화로 인해 외장 경로에 임시 액세스 권한 부여
        Uri data = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(selectItem.getFilePath()));
        String type = FileUtils.getMimeType(selectItem.getFileExt());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(data, type);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // 해당 파일을 열 뷰어가 없는 경우 메시지 출력
            Toast.makeText(context, context.getText(R.string.NOT_FOUND_VIEWER), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 경로의 파일 리스트를 가져와 갱신하는 함수
     *
     * @param path                     경로
     * @param taskListener 리스트 데이터 변경 완료를 알려줌
     */
    public void refreshFileList(String path, FileListener.TaskListener taskListener) {
        FileRefreshAsyncTask listAsyncTask = new FileRefreshAsyncTask(fileItems, taskListener);
        listAsyncTask.execute(path);
    }

    /**
     * 선택한 파일을 삭제해 리스트를 갱신하는 함수
     *
     * @param taskListener 리스트 데이터 변경 완료를 알려줌
     */
    public void deleteFile(FileListener.TaskListener taskListener) {
        FileDeleteAsyncTask deleteAsync = new FileDeleteAsyncTask(fileItems, taskListener);
        deleteAsync.execute();
    }
}
