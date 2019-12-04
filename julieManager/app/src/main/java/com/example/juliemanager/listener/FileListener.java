package com.example.juliemanager.listener;

/**
 * Created by julie on 2019-11-22
 * 파일 매니져에서 사용하는 리스너
 */
public class FileListener {

    /**
     * 작업이 완료됨을 알려주는 리스너
     */
    public interface TaskListener {
        void onCompleted();
    }

    /**
     * 파일 리스트에서 파일 선택 시 리스너
     */
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    /**
     * 즐겨찾기 체크 시 체크 유무를 저장하기 위한 리스너
     */
    public interface OnCheckedChangedListener {
        void onCheckedChanged(int pos, boolean isCheck);
    }
}
