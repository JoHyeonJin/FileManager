package com.example.juliemanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.juliemanager.view.FileListFragment;

import java.util.ArrayList;

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

        if (hasPermission()) {
            initFragList();
            initDelete();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initFragList();
                initDelete();
            }
        }
    }

    /**
     * 권한이 없다면 권한 요청하는 함수
     *
     * @return 권한 있음
     */
    private boolean hasPermission() {
        ArrayList<String> permissionList = new ArrayList<>();
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int permissionResult;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                permissionResult = checkSelfPermission(permission);
                if (permissionResult == PackageManager.PERMISSION_DENIED) {
                    permissionList.add(permission);
                }
            }

            if (!permissionList.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
                return false;
            }
        }

        return true;
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
