package com.example.juliemanager;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by julie on 2019-10-08
 * 파일 리스트를 화면에 보여주는 어댑터
 */
public class FileAdapter extends BaseAdapter {
    private ArrayList<FileItem> fileItems;
    private ViewHolder viewHolder;

    public FileAdapter(ArrayList<FileItem> fileItems) {
        this.fileItems = fileItems;
    }

    @Override
    public int getCount() {
        return fileItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public FileItem getItem(int position) {
        return fileItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fileitem, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.li_fileDateAndSize = convertView.findViewById(R.id.li_fileDateAndSize);
            viewHolder.fileImage = convertView.findViewById(R.id.iv_fileImage);
            viewHolder.fileName = convertView.findViewById(R.id.tv_fileName);
            viewHolder.fileDate = convertView.findViewById(R.id.tv_fileDate);
            viewHolder.fileSize = convertView.findViewById(R.id.tv_fileSize);
            viewHolder.fileFavorite = convertView.findViewById(R.id.ch_fileFavorite);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FileItem fileItem = fileItems.get(position);

        // 항목 출력 세팅
        settingFileItem(fileItem, context);

        // 항목 출력 활성화
        if (fileItem.getFileName().equals("...")) {
            setVisibilitySubFileItem();
        } else {
            setVisibilityFileItem();
        }

        return convertView;
    }

    /**
     * 파일 아이콘, 이름, 날짜, 크기 출력 세팅
     *
     * @param fileItem
     */
    private void settingFileItem(FileItem fileItem, Context context) {
        viewHolder.fileImage.setImageDrawable(ContextCompat.getDrawable(context, fileItem.getFileIcon()));
        viewHolder.fileName.setText(fileItem.getFileName());
        viewHolder.fileDate.setText(fileItem.getFileDate());
        viewHolder.fileSize.setText(fileItem.getFileSize());
    }

    /**
     * 상위 이동을 위한 항목 일때 활성화 세팅
     */
    private void setVisibilitySubFileItem() {
        viewHolder.li_fileDateAndSize.setVisibility(View.GONE);
        viewHolder.fileFavorite.setVisibility(View.GONE);
    }

    /**
     * 파일/폴더 항 일때 활성화 세팅
     */
    private void setVisibilityFileItem() {
        viewHolder.li_fileDateAndSize.setVisibility(View.VISIBLE);
        viewHolder.fileFavorite.setVisibility(View.VISIBLE);
    }

    private static class ViewHolder {
        LinearLayout li_fileDateAndSize;
        ImageView fileImage;
        TextView fileName;
        TextView fileDate;
        TextView fileSize;
        CheckBox fileFavorite;
    }
}
