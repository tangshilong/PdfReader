package com.tangshilong.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class CopyFile {

	public static void main(String[] args) {
		for (int i = 0; i < 32; i++) {
			CopyFile(i);
		}
	}

	@SuppressWarnings({"ResultOfMethodCallIgnored", "MethodNameSameAsClassName"})
	private static void CopyFile(int n) {
		FileWriter fw = null;
		BufferedReader br;
		String path = "h:\\spider\\" + n + "\\作者信息.txt";
		File f = new File("H:\\spider\\作者信息.txt");
		System.out.println("开始复制第"+n+"个文件");
		if (!f.exists()) {
			f.getParent();
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw = new FileWriter("H:\\spider\\作者信息.txt", true);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				//System.out.println("文件内容: " + line);
				fw.write(line+"\n");
				fw.flush();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("文件复制完毕");
	}
}
