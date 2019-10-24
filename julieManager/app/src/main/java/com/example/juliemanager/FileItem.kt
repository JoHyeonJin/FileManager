package com.example.juliemanager

/**
 * Created by julie on 2019-10-08
 * 파일 정보(폴더 유무, 이름, 날짜, 사이즈, 확장자, 즐겨찾기 유무) 저장하는 클래스
 */
class FileItem {
    var isFile: Boolean = false
    var fileIcon: Int = 0
    var fileName: String? = null
    var fileDate: String? = null
    var fileSize: String? = null
    var fileExt: String? = null
}
