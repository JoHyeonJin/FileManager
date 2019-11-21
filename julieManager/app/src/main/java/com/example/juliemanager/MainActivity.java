package com.example.juliemanager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.juliemanager.view.FileListFragment;

/**
 * Created by julie on 2019-10-08
 * 파일 매니저 메인 액티비티
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragList();
    }

    /**
     * 파일 리스트 프래그먼트 초기화
     */
    private void initFragList() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.listFragment, new FileListFragment());
        fragmentTransaction.commit();
    }
}
