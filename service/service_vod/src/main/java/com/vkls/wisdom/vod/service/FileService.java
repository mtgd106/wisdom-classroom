package com.vkls.wisdom.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    //文件上传
    String upload(MultipartFile file);
}
