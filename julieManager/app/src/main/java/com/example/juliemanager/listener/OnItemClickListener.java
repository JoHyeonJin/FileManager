package com.example.juliemanager.listener;

import android.view.View;

/**
 * Created by julie on 2019-11-22
 * 파일 리스트에서 파일 선택 시 리스너
 */
public interface OnItemClickListener {
    void onItemClick(View v, int pos);
}