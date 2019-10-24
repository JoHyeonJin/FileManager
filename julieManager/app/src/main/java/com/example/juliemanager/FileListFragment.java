package com.example.juliemanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.juliemanager.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;

import static com.example.juliemanager.utils.FileConstant.ROOT;

/**
 * Created by julie on 2019-10-10
 * 파일 리스트를 보여주는 프래그먼트
 */
public class FileListFragment extends ListFragment {
    private ArrayList<FileItem> fileItems;
    private File currentDir;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = new File(ROOT);
        grant();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //리스트에서 항목 눌렀을 때 배경 변경을 위한 셀렉터 지정
        getListView().setSelector(R.drawable.listview_selector);
        updateAdapter();
        super.onViewCreated(view, savedInstanceState);
    }

    private void updateAdapter() {
        FileAdapter fileAdapter = new FileAdapter(fileItems);
        setListAdapter(fileAdapter);
    }

    /**
     * 권한 주기
     */
    private void grant() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = this.getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                initListItem();
            }
        }
    }

    /**
     * sdcard/documents 경로 파일 리스트를 가져오는 함수
     */
    private void initListItem() {
        fileItems = new ArrayList<>();

        if (!currentDir.exists() || currentDir.isFile()) {
            // TODO: 2019/10/07 추후 리스트를 가져올 파일 경로가 존재하지 않을 경우 코드 추가.
            return;
        }

        File[] files = currentDir.listFiles();

        if (files == null) {
            // TODO: 2019/10/08 dir과 가져온 files null일 경우 예외 코드 추가
            return;
        }

        // 현재 위치에서 상위로 이동 가능한 경우 상위로 이동할 리스트 아이템 추가
        if (!currentDir.getAbsolutePath().equals(ROOT)) {
            FileItem subFile = new FileItem();
            subFile.setFileName("...");
            subFile.setFileIcon(R.drawable.ico_file_folder_upper);
            fileItems.add(subFile);
        }

        for (File file : files) {
            fileItems.add(initFileItem(file));
        }

    }

    /**
     * 파일 아이템을 만드는 함수
     *
     * @param file
     * @return 각각의 파일 아이템
     */
    private FileItem initFileItem(File file) {
        FileItem item = new FileItem();

        item.setFile(file.isFile());
        item.setFileName(file.getName());
        item.setFileDate(FileUtils.getFormattedDate(file.lastModified()));

        if (item.isFile()) {
            item.setFileSize(FileUtils.getFormattedSize(file.length()));
            item.setFileExt(FileUtils.getFormattedFileExt(file.getName()));
            item.setFileIcon(FileUtils.getFormattedFileIcon(file.getName()));
        } else {
            item.setFileIcon(FileUtils.getFormattedFolderIcon());
        }

        return item;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FileItem selectItem = (FileItem) getListAdapter().getItem(position);
        if (selectItem.isFile()) return;

        //상위 경로로 이동
        if (selectItem.getFileName().equals("...")) {
            currentDir = new File(currentDir.getParent());
            refreshFileList();
        } else { //하위 경로 이동
            currentDir = new File(currentDir + "/" + selectItem.getFileName());
            refreshFileList();
        }

        super.onListItemClick(l, v, position, id);
    }

    /**
     * 현재 폴더에서 상위, 하위로 이동해 리스트 갱신
     */
    private void refreshFileList() {
        initListItem();
        updateAdapter();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initListItem();
            }
        }
    }
}
