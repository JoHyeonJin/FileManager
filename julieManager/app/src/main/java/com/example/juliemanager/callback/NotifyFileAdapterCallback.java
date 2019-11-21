package com.example.juliemanager.callback;

import com.example.juliemanager.data.FileItem;

import java.util.ArrayList;

/**
 * Created by julie on 2019-11-14
 * 파일 리스트 데이터 업데이트 후, 어댑터에 알려주기 위한 콜백 인터페이스
 */
public interface NotifyFileAdapterCallback {
    void notifyAdapter(ArrayList<FileItem> fileItemArrayList);
}
