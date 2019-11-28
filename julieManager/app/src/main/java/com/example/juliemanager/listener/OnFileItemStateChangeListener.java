package com.example.juliemanager.listener;

/**
 * Created by julie on 2019-11-22
 * 파일 아이템의 상태 변화를 체크하기 위한 리스너
 */
public interface OnFileItemStateChangeListener {

    /**
     * 파일 아이템 선택 시 선택됨을 알려주기 위한 함수
     * @param pos 선택한 위치
     */
    void onItemClick(int pos);


    /**
     * 즐겨찾기 체크 시 체크 유무를 알려주기 위한 함수
     * @param pos 체크한 위치
     * @param isCheck 체크 유무
     */
    void onCheckedChanged(int pos, boolean isCheck);
}
