package com.tangshilong.run;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * pdf转成text
 * @author tangshilong
 *
 */
public class PdfReader {
	@SuppressWarnings("ConstantConditions")
	private void readPdf(String path) throws Exception {
		// pdf文件名
		// 输入文本文件名称
		String textFile = null;
		// 编码方式
		String encoding = "UTF-8";
		// 开始提取页数
		int startPage = 1;
		// 结束提取页数
		int endPage = Integer.MAX_VALUE;
		// 文件输入流，生成文本文件
		Writer output = null;
		// 内存中存储的PDF Document
		PDDocument document = null;
		try {
			try {
				// 首先当作一个URL来装载文件，如果得到异常再从本地文件系统//去装载文件
				URL url = new URL(path);
				// 注意参数已不是以前版本中的URL.而是File。
				document = PDDocument.load(url);
				// 获取PDF的文件名
				String fileName = url.getFile();
				// 以原来PDF的名称来命名新产生的txt文件
				if (fileName.length() > 4) {
					File outputFile = new File(fileName.substring(0, fileName.length() - 4) + ".txt");
					textFile = outputFile.getName();
				}
			} catch (MalformedURLException e) {
				// 如果作为URL装载得到异常则从文件系统装载
				// 注意参数已不是以前版本中的URL.而是File。
				document = PDDocument.load(path);
				if (path.length() > 4) textFile = path.substring(0, path.length() - 4) + ".txt";
			}
			// 文件输入流，写入文件倒textFile
			output = new OutputStreamWriter(new FileOutputStream(textFile), encoding);
			// PDFTextStripper来提取文本
			PDFTextStripper stripper;
			stripper = new PDFTextStripper();
			// 设置是否排序
			stripper.setSortByPosition(false);
			// 设置起始页
			stripper.setStartPage(startPage);
			// 设置结束页
			stripper.setEndPage(endPage);
			// 调用PDFTextStripper的writeText提取并输出文本
			stripper.writeText(document, output);
		} finally {
			if (output != null) {
				// 关闭输出流
				output.close();
			}
			if (document != null) {
				// 关闭PDF Document
				document.close();
			}
		}
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("JavaDoc")
	public static void main(String[] args) {
		File f = new File("E:\\pdf");
		File[] t = f.listFiles();
		System.out.println("获取文件列表成功，开始解析pdf");
		for (File file : t != null ? t : new File[0]) {
			PdfReader pdfReader = new PdfReader();
			try {
				// 取得E盘下的SpringGuide.pdf的内容
				System.out.println(file.getPath());
				pdfReader.readPdf(file.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}