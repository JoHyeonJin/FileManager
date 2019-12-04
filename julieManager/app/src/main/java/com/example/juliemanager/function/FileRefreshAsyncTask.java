package com.example.juliemanager.function;

import android.os.AsyncTask;

import com.example.juliemanager.R;
import com.example.juliemanager.data.FileItem;
import com.example.juliemanager.listener.FileListener;
import com.example.juliemanager.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.juliemanager.utils.FileConstant.ROOT;

/**
 * Created by julie on 2019-11-11
 * 현재 경로에 대해 파일 리스트 가져오는 클래스
 */
public class FileRefreshAsyncTask extends AsyncTask<String, Void, ArrayList<FileItem>> {
    private ArrayList<FileItem> fileItems;
    private FileListener.TaskListener taskListener;

    public FileRefreshAsyncTask(ArrayList<FileItem> fileItems, FileListener.TaskListener taskListener) {
        this.fileItems = fileItems;
        this.taskListener = taskListener;
    }

    @Override
    protected ArrayList<FileItem> doInBackground(String... strings) {
        File currentDir = null;

        if (strings[0] != null) currentDir = new File(strings[0]);

        if (!currentDir.exists()) {
            return (ArrayList) Collections.EMPTY_LIST;
        }

        File[] files = currentDir.listFiles();

        if (files == null) {
            return (ArrayList) Collections.EMPTY_LIST;
        }

        return addFileList(currentDir, files);
    }

    @Override
    protected void onPostExecute(ArrayList<FileItem> fileItemArrayList) {
        super.onPostExecute(fileItemArrayList);
        fileItems.clear();
        fileItems.addAll(fileItemArrayList);

        // TODO: julie 2019-10-25 현재 경로가 존재하지 않거나, 파일 리스트가 존재하지 않을 때 예외 처리 필요.
        if (fileItems.isEmpty()) return;

        taskListener.onCompleted();
    }


    /**
     * 파일 항목들에 대해 파일 정보를 저장하기 위한 함수
     *
     * @param currentDir 현재 폴더 경로 FILE
     * @param files      현재 폴더의 항목 리스트
     * @return 파일 리스트
     */
    private ArrayList<FileItem> addFileList(File currentDir, File[] files) {
        ArrayList<FileItem> items = new ArrayList<>();

        // 현재 위치에서 상위로 이동 가능한 경우 상위로 이동할 리스트 아이템 추가
        if (!currentDir.getAbsolutePath().equals(ROOT)) {
            FileItem subFile = new FileItem();
            subFile.setFileName("..");
            subFile.setFileIcon(R.drawable.ico_file_folder_upper);
            subFile.setFilePath(currentDir.getParent());
            items.add(subFile);
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

            items.add(item);
        }

        return items;
    }
}
