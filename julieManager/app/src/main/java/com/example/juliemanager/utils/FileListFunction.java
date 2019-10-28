package com.example.juliemanager.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.juliemanager.FileItem;
import com.example.juliemanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.juliemanager.utils.FileConstant.ROOT;

/**
 * Created by julie on 2019-10-15
 * 파일 리스트 처리를 위한 함수 모음 클래스
 */
public class FileListFunction {

    /**
     * 파일 리스트를 가져오는 함수
     *
     * @param path 경로
     * @return 파일 리스트
     */
    public List<FileItem> getFileList(String path) {
        File currentDir = new File(path);

        if (!currentDir.exists()) {
            // TODO: julie 2019-10-25 현재 경로가 존재하지 않을 때 예외 처리 필요.
            return Collections.EMPTY_LIST;
        }

        File[] files = currentDir.listFiles();

        if (files == null) {
            // TODO: julie 2019-10-25 현재 경로에 파일 리스트가 존재하지 않을 때 예외 처리 필요.
            return Collections.EMPTY_LIST;
        }

        return addFileList(currentDir, files);
    }

    /**
     * 파일 항목들에 대해 파일 정보를 저장하기 위한 함수
     *
     * @param currentDir 현재 폴더 경로 FILE
     * @param files      현재 폴더의 항목 리스트
     * @return 파일 리스트
     */
    private List<FileItem> addFileList(File currentDir, File[] files) {
        List<FileItem> fileItems = new ArrayList<>();

        // 현재 위치에서 상위로 이동 가능한 경우 상위로 이동할 리스트 아이템 추가
        if (!currentDir.getAbsolutePath().equals(ROOT)) {
            FileItem subFile = new FileItem();
            subFile.setFileName("..");
            subFile.setFileIcon(R.drawable.ico_file_folder_upper);
            subFile.setFilePath(currentDir.getParent());
            fileItems.add(subFile);
        }

        //리스트 아이템 추가
        for (File file : files) {
            FileItem item = new FileItem();
            item.setFile(file.isFile());
            item.setFileName(file.getName());
            item.setFileDate(FileUtils.getFormattedDate(file.lastModified()));
            item.setFilePath(file.getAbsolutePath());
            item.setFileSize(FileUtils.getFormattedSize(file.length()));
            item.setFileExt(FileUtils.getFormattedFileExt(file.getPath()));
            item.setFileIcon(FileUtils.getFormattedFileIcon(item));

            fileItems.add(item);
        }

        return fileItems;
    }

    /**
     * 파일 선택 시 뷰어 열기
     *
     * @param context    컨텍스트
     * @param selectItem 선택한 파일
     */
    public void showFileViewer(Context context, FileItem selectItem) {
        //버전 7.0 부터 앱 사이 공유 엄격화로 인해 외장 경로에 임시 액세스 권한 부여
        Uri data = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(selectItem.getFilePath()));
        String type = FileUtils.getMimeType(selectItem.getFileExt());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(data, type);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // 해당 파일을 열 뷰어가 없는 경우 메시지 출력
            Toast.makeText(context, context.getText(R.string.NOT_FOUND_VIEWER), Toast.LENGTH_SHORT).show();
        }
    }
}
