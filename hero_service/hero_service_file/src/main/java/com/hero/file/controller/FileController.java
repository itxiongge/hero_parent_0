package com.hero.file.controller;

import com.hero.file.pojo.FastDFSFile;
import com.hero.file.util.FastDFSClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
 * @Author yaxiongliu
 **/
@RestController
@CrossOrigin
public class FileController {

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String path ="";
        try {
            path=saveFile(file);
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {
        //1. 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        //2. 获取文件内容
        byte[] content = multipartFile.getBytes();
        //3. 获取文件扩展名
        String ext = "";
        if (fileName != null && !"".equals(fileName)) {
            ext = fileName.substring(fileName.lastIndexOf("."));
        }
        //4. 创建文件实体类对象
        FastDFSFile fastDFSFile = new FastDFSFile(fileName, content, ext);
        //5. 上传
        String[] uploadResults = FastDFSClient.upload(fastDFSFile);
        //6. 拼接上传后的文件的完整路径和名字, uploadResults[0]为组名, uploadResults[1]为文件名称和路径
        String path = FastDFSClient.getTrackerUrl() + uploadResults[0] + "/" + uploadResults[1];
        //7. 返回
        return path;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(){
        String groupName = "group01"; //组名
        String filePath = "M00/00/00/wKjIgGQ2YVOAGw24AACHnqcTMcc31..jpg";//文件路径
        byte[] content = FastDFSClient.download(groupName,filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",
                new String("bd.jpg".getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1));
        return new ResponseEntity<>(content, headers, HttpStatus.CREATED);
    }
}
