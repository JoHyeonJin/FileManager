package com.example.juliemanager.list;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juliemanager.R;

import static com.example.juliemanager.utils.FileConstant.ROOT;

/**
 * Created by julie on 2019-10-10
 * 파일 리스트를 보여주는 프래그먼트
 */
public class FileListFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        grant();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filelist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.view_fileList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final FileAdapter fileAdapter = new FileAdapter();
        recyclerView.setAdapter(fileAdapter);

        //ROOT 경로의 파일 리스트 갱신
        fileAdapter.refreshFileList(ROOT);
    }

    /**
     * 권한 주기
     */
    private void grant() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = this.getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                // TODO: 2019-11-11 권한이 없을 때 예외 처리 필요
            }
        }
    }
}
