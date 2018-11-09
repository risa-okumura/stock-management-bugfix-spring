package jp.co.rakus.stockmanagement.web;

import java.io.File;

public class ImageDirectory {

	public String filePath(String path) {

		File file = new File(path);
		String filePath = file.getParent();

		return filePath;
	}

}
