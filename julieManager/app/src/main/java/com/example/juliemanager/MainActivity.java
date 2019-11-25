package com.example.juliemanager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.juliemanager.view.FileListFragment;

/**
 * Created by julie on 2019-10-08
 * 파일 매니저 메인 액티비티
 */
public class MainActivity extends AppCompatActivity {
    FileListFragment fileListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragList();
        initDelete();
    }

    /**
     * 파일 리스트 프래그먼트 초기화
     */
    private void initFragList() {
        fileListFragment = new FileListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.listFragment, fileListFragment);
        fragmentTransaction.commit();
    }

    /**
     * 삭제 버튼 초기화
     */
    private void initDelete() {
        Button deleteBtn = findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileListFragment.delete();
            }
        });
    }
}
