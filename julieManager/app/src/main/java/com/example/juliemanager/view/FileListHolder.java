package com.example.juliemanager.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juliemanager.R;
import com.example.juliemanager.listener.FileListener;

/**
 * Created by julie on 2019-11-11
 * 파일 리스트 뷰 홀더
 */
public class FileListHolder extends RecyclerView.ViewHolder {
    private FileListener.OnItemClickListener onItemClickListener;
    private FileListener.OnCheckedChangedListener onCheckedChangedListener;

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
                    onItemClickListener.onItemClick(pos);
                }
            }
        });

        initView();
    }

    public void setOnItemClickListener(FileListener.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCheckedChangedListener(FileListener.OnCheckedChangedListener onCheckedChangedListener) {
        this.onCheckedChangedListener = onCheckedChangedListener;
    }

    private void initView() {
        li_fileDateAndSize = itemView.findViewById(R.id.li_fileDateAndSize);
        fileImage = itemView.findViewById(R.id.iv_fileImage);
        fileName = itemView.findViewById(R.id.tv_fileName);
        fileDate = itemView.findViewById(R.id.tv_fileDate);
        fileSize = itemView.findViewById(R.id.tv_fileSize);
        fileFavorite = itemView.findViewById(R.id.ch_fileFavorite);
        fileFavorite.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckedChangedListener.onCheckedChanged(getAdapterPosition(), isChecked);
            }
        });
    }
}
