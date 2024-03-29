package com.example.juliemanager.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juliemanager.R;
import com.example.juliemanager.data.FileItem;
import com.example.juliemanager.function.FileFunction;
import com.example.juliemanager.listener.FileListener;

import java.util.ArrayList;

/**
 * Created by julie on 2019-10-08
 * 파일들(데이터)를 화면에 보여주는 어댑터
 */
public class FileAdapter extends RecyclerView.Adapter<FileListHolder> {
    private ArrayList<FileItem> fileItems;

    public FileAdapter(ArrayList<FileItem> fileItems) {
        this.fileItems = fileItems;
    }

    @Override
    public int getItemCount() {
        return fileItems.size();
    }

    @Override
    public FileListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fileitem, parent, false);

        FileListHolder viewHolder = new FileListHolder(view);
        viewHolder.setOnItemClickListener(new FileListener.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                FileItem selectItem = fileItems.get(pos);
                if (selectItem.isFile()) {
                    //파일일 경우 뷰어 열기
                    FileFunction.getInstance().showFileViewer(context, selectItem);
                } else {
                    //폴더일 경우 상위,하위로 이동
                    FileFunction.getInstance().refreshFileList(selectItem.getFilePath(), new FileListener.TaskListener() {
                        @Override
                        public void onCompleted() {
                            // TODO: 2019-12-04 FileListFragment의 refresh와 중복. 추후 방법 생각해야됨  
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        viewHolder.setOnCheckedChangedListener(new FileListener.OnCheckedChangedListener() {
            @Override
            public void onCheckedChanged(int pos, boolean isCheck) {
                //파일의 즐겨찾기를 체크 유무를 데이터에 저장
                fileItems.get(pos).setFileFavorite(isCheck);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FileListHolder holder, int position) {
        FileItem fileItem = fileItems.get(position);

        // 항목 출력 세팅
        setDisplay(fileItem, holder);

        // 항목 출력 활성화
        setActivation(fileItem, holder);
    }

    /**
     * 파일 아이콘, 이름, 날짜, 크기 출력 세팅
     *
     * @param fileItem
     */
    private void setDisplay(FileItem fileItem, FileListHolder viewHolder) {
        viewHolder.fileImage.setImageDrawable(ContextCompat.getDrawable(viewHolder.itemView.getContext(), fileItem.getFileIcon()));
        viewHolder.fileName.setText(fileItem.getFileName());
        viewHolder.fileDate.setText(fileItem.getFileDate());
        viewHolder.fileSize.setText(fileItem.getFileSize());
        viewHolder.fileFavorite.setChecked(fileItem.getFileFavorite());
    }

    /**
     * 항목 출력 활성화
     *
     * @param fileItem 현재 항목
     */
    private void setActivation(FileItem fileItem, FileListHolder viewHolder) {
        //파일일 경우
        if (fileItem.isFile()) {
            viewHolder.li_fileDateAndSize.setVisibility(View.VISIBLE);
            viewHolder.fileSize.setVisibility(View.VISIBLE);
            viewHolder.fileFavorite.setVisibility(View.VISIBLE);
        } else if (fileItem.getFileName().equals("..")) { //상위 경로 이동 폴더일 경우
            viewHolder.li_fileDateAndSize.setVisibility(View.GONE);
            viewHolder.fileSize.setVisibility(View.GONE);
            viewHolder.fileFavorite.setVisibility(View.GONE);
        } else { //폴더일 경우
            viewHolder.li_fileDateAndSize.setVisibility(View.VISIBLE);
            viewHolder.fileSize.setVisibility(View.GONE);
            viewHolder.fileFavorite.setVisibility(View.VISIBLE);
        }
    }
}
