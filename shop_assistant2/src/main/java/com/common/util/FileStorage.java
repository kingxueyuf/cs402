package com.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileStorage {

	public static String[] uploadFile(MultipartFile files, String appPath) {

		ArrayList<String> paths = new ArrayList<String>();

		MultipartFile file = files;
		String pictureName = Util.getUUID()+".jpg";
		String picturePath = appPath + "resources/pic/" + pictureName;
		String dbPath = "/pic/" + pictureName;
		System.out.println("-----------------picture path = " + picturePath);
		try {
			File picFile= new File(picturePath);
			picFile.createNewFile();
			file.transferTo(picFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paths.add(dbPath);

		return paths.toArray(new String[0]);
	}
}
