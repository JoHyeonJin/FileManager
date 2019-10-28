package com.example.juliemanager;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by julie on 2019-10-08
 * 파일 리스트를 화면에 보여주는 어댑터
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private OnItemClickListener itemClickListener;
    private ArrayList<FileItem> files;

    public FileAdapter(ArrayList<FileItem> files) {
        this.files = files;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fileitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FileItem fileItem = files.get(position);

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
    private void setDisplay(FileItem fileItem, ViewHolder holder) {
        holder.fileImage.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), fileItem.getFileIcon()));
        holder.fileName.setText(fileItem.getFileName());
        holder.fileDate.setText(fileItem.getFileDate());
        holder.fileSize.setText(fileItem.getFileSize());
    }

    /**
     * 항목 출력 활성화
     *
     * @param fileItem 현재 항목
     */
    private void setActivation(FileItem fileItem, ViewHolder viewHolder) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout li_fileDateAndSize;
        ImageView fileImage;
        TextView fileName;
        TextView fileDate;
        TextView fileSize;
        CheckBox fileFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(v, pos);
                    }
                }
            });

            initView();
        }

        private void initView() {
            li_fileDateAndSize = itemView.findViewById(R.id.li_fileDateAndSize);
            fileImage = itemView.findViewById(R.id.iv_fileImage);
            fileName = itemView.findViewById(R.id.tv_fileName);
            fileDate = itemView.findViewById(R.id.tv_fileDate);
            fileSize = itemView.findViewById(R.id.tv_fileSize);
            fileFavorite = itemView.findViewById(R.id.ch_fileFavorite);
        }
    }
}
