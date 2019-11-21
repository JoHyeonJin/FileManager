package com.example.juliemanager.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juliemanager.R;

/**
 * Created by julie on 2019-11-11
 * 파일 리스트 뷰 홀더
 */
public class FileListHolder extends RecyclerView.ViewHolder {
    private OnItemClickListener itemClickListener;

    LinearLayout li_fileDateAndSize;
    ImageView fileImage;
    TextView fileName;
    TextView fileDate;
    TextView fileSize;
    CheckBox fileFavorite;

    public FileListHolder(View itemView) {
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

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
