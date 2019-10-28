package com.example.juliemanager;

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

import com.example.juliemanager.utils.FileListFunction;

import java.util.ArrayList;

import static com.example.juliemanager.utils.FileConstant.ROOT;

/**
 * Created by julie on 2019-10-10
 * 파일 리스트를 보여주는 프래그먼트
 */
public class FileListFragment extends Fragment {
    private ArrayList<FileItem> fileItems;
    private FileListFunction fileListFunction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileListFunction = new FileListFunction();
        grant();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filelist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.view_fileList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final FileAdapter fileAdapter = new FileAdapter(fileItems);
        fileAdapter.setItemClickListener(new FileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                FileItem selectItem = fileItems.get(position);
                if (selectItem.isFile()) { //파일일 경우 뷰어 열기
                    fileListFunction.showFileViewer(getContext(), selectItem);
                } else {
                    fileItems.clear(); //폴더일 경우 상위,하위로 이동
                    fileItems.addAll(fileListFunction.getFileList(selectItem.getFilePath()));
                    fileAdapter.notifyDataSetChanged();
                }
            }
        });
        recyclerView.setAdapter(fileAdapter);

        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 권한 주기
     */
    private void grant() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = this.getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                fileItems = (ArrayList) fileListFunction.getFileList(ROOT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fileItems = (ArrayList) fileListFunction.getFileList(ROOT);
            }
        }
    }
}
