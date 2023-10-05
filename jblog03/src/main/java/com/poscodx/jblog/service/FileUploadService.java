package com.poscodx.jblog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.exception.FileUploadServiceException;


@Service
public class FileUploadService {
	private static String SAVE_PATH = "/jblog-uploads";
	private static String URL_PATH = "/assets/upload-image";
	
	public String restore(MultipartFile file) {
		String url = null;

		try {

			File uploadDirectory = new File(SAVE_PATH);
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}

			// 사용자가 파일을 선택 안함
			if (file.isEmpty()) {
				return url;
			}

			// 파일 정보 수집
			String originFileName = file.getOriginalFilename();
			String extName = originFileName.substring(originFileName.lastIndexOf(".") + 1);
			String saveFileName = generateSaveFilename(extName);
			Long fileSize = file.getSize();

			System.out.println("#######" + originFileName);
			System.out.println("#######" + saveFileName);
			System.out.println("#######" + fileSize);

			// 파일 저장
			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(data);
			os.close();

			url = URL_PATH + "/" + saveFileName;

		} catch (IOException ex) {
			throw new FileUploadServiceException(ex.toString());
		}

		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";

		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName );

		return filename;
	}

}
