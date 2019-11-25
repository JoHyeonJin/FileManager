package com.example.juliemanager.function.edit;

import com.example.juliemanager.callback.NotifyFileAdapterCallback;;
import com.example.juliemanager.data.FileItem;

import java.util.ArrayList;

/**
 * Created by julie on 2019-11-21
 * 파일 삽입, 삭제 등 파일 편집 기능을 위한 함수 모음 클래스
 */
public class FileEditFunction {

    /**
     * 선택한 파일을 삭제해 리스트를 갱신하는 함수
     *
     * @param fileItems                 현재 파일 리스트
     * @param notifyFileAdapterCallback 파일 리스트 데이터 삭제 후 데이터 변경 알림을 위한 콜백
     */
    public static void deleteFile(ArrayList<FileItem> fileItems, NotifyFileAdapterCallback notifyFileAdapterCallback) {
        FileDeleteAsync deleteAsync = new FileDeleteAsync(fileItems, notifyFileAdapterCallback);
        deleteAsync.execute();
    }
}
