package kr.co.js.moviereview.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {
    @Value("${kr.co.js.upload.path}") // application.properties 변수
    private String uploadPath;
    //파일 업로드 처리 메서드
    /*
    @PostMapping(value = "/uploadajax")
    public void uploadFile(MultipartFile[] uploadFiles) {
        for (MultipartFile uploadFile : uploadFiles) {

        // 업로드 되는 원본파일 이름 출력
            String originalName = uploadFile.getOriginalFilename();
            //실제 파일 이름 IE나 Edge는 파일 경로도 전달되므로 파일의 경로 제거거
           String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("fileName: " + fileName);
        }
    }
     */


    // 디렉토리를 생성해주는 메서드를 생성
    private String makeFolder() {
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // '/'을 파일 구분자로 변경
        String realUploadPath = str.substring(0,4) + "\\" + str.substring(4,6)+ "\\" +str.substring(5);
        // 파일객체로 생성
        File uploadPathDir = new File(uploadPath, realUploadPath);
        // 파일 객체가 없으면 디렉토리 생성
        if (uploadPathDir.exists() == false) {
            uploadPathDir.mkdirs();
        }
        //업로드 디렉토리 경로를 리턴
        return realUploadPath;
    }

    @PostMapping(value = "/uploadajax")
    public void uploadFile(MultipartFile[] uploadFiles) {
        for (MultipartFile uploadFile : uploadFiles) {
            //이미지 파일만 업로드 가능
            if (uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");
                return;
            }
            //업로드 되는 원본파일 출렷
            String originalName = uploadFile.getOriginalFilename();
            //실제 파일 이름 IE나 Edge는 파일 경로도 전달되므로 파일의 경로 제거거
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("fileName: " + fileName);

            String realUploadPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();
            //저장할 파일 이름 중간에 _를 이용해서 구분 -저장할 경로 생성
            String saveName = uploadPath + File.separator + realUploadPath + File.separator + uuid + fileName;

            Path savePath = Paths.get(saveName);
            try {
                //파일 업로드
                uploadFile.transferTo(savePath);
            } catch (Exception e) {
                log.info("예외:" + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

    }
}





