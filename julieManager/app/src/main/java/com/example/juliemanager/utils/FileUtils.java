package com.example.juliemanager.utils;

import com.example.juliemanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by julie on 2019-10-08
 * 파일 리스트에 보여줄 형식에 따라 파일 처리하는 함수 모음
 */
public class FileUtils {

    /**
     * YYYY-MM-DD 형식으로 파일 날짜 가져오는 함수
     *
     * @param data 날짜
     * @return formatted fileDate
     */

    public static String getFormattedDate(long data) {
        DateFormat dateFormat = new SimpleDateFormat("YYYY.MM.dd");
        return dateFormat.format(data);
    }

    /**
     * 바이트 단위로 소수 둘째 자리까지 표기하여 파일 크기를 가져오는 함수
     *
     * @param size 파일 크기
     * @return formatted fileSize
     */
    public static String getFormattedSize(long size) {
        long base = 1024;
        if (size <= 0) {
            return "0B";
        }

        if (size < base) {
            return String.format("%.2f%s", size, "B");
        }

        int exp = (int) (Math.log(size) / Math.log(base));
        return String.format("%.2f%s", size / Math.pow(base, exp), "KMGTPE".charAt(exp - 1) + "B");
    }

    /**
     * 파일 확장자만 가져오는 함수
     *
     * @param name 파일 이름
     * @return 파일 확장자
     */
    public static String getFormattedFileExt(String name) {
        if (name.contains(".")) return name.substring(name.lastIndexOf(".") + 1, name.length());
        return "";
    }


    /**
     * 파일 확장자에 따른 파일 아이콘을 가져오는 함수
     *
     * @param name 파일 이름
     * @return 파일 아이콘
     */
    public static int getFormattedFileIcon(String name) {
        int draw;
        String ext = getFormattedFileExt(name);
        switch (ext) {
            case "doc":
                draw = R.drawable.ico_file_doc;
                break;
            case "docx":
                draw = R.drawable.ico_file_docx;
                break;
            case "ppt":
                draw = R.drawable.ico_file_ppt;
                break;
            case "pptx":
                draw = R.drawable.ico_file_pptx;
                break;
            case "xls":
                draw = R.drawable.ico_file_xls;
                break;
            case "xlsx":
                draw = R.drawable.ico_file_xlsx;
                break;
            default:
                draw = R.drawable.ico_file_unknown;
                break;

        }

        return draw;
    }

    /**
     * 폴더 형태에 따른 아이콘을 가져오는 함수
     *
     * @return 폴더 아이콘
     */
    public static int getFormattedFolderIcon() {
        // TODO: 2019/10/22 추후 폴더 형태에 따라 아이콘 다르게 반환하도록 추가
        return R.drawable.ico_file_folder_nosub;
    }

}
