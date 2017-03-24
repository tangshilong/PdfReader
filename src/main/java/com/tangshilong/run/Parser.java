package com.tangshilong.run;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 直接提取pdf信息
 *
 * @author tangshilong
 */
public class Parser {
    public static void main(String[] args) {
        for (int n = 0; n < 32; n++) {
            // int i = 1;
            int j = 0;
            File f = new File("h:\\spider\\" + n);
            File[] t = f.listFiles();
            System.out.println("获取文件列表成功，开始解析pdf");
            for (File file : t != null ? t : new File[0]) {
                // if (i > 1) {
                // break;
                // }
                // i++;
                Parser parser = new Parser();
                try {
                    // 取得E盘下的SpringGuide.pdf的内容
                    System.out.println(file.getPath());
                    parser.get(file.getPath(), n);
                    System.out.println("*********************************************" + (++j) + "    第" + n + "个文件夹");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @SuppressWarnings({"resource", "ResultOfMethodCallIgnored"})
    private void get(String pdfPath, int n) throws Exception {
        InputStream input = null;
        File pdfFile = new File(pdfPath);
        PDDocument document = null;
        String path = "h:\\spider\\" + n + "\\";
        // TODO 添加文件报告名称
        String name = pdfPath.replace(path, "");

        // TODO 提取作者姓名
        List<String> username = getWriterName(name);

        FileWriter fw;
        // 定义FileWriter对象，关联文件f:\text.txt，用来向文件写内容
        File f = new File("H:\\spider\\" + n + "\\作者信息.txt");
        if (!f.exists()) {
            f.getParent();
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        fw = new FileWriter("H:\\spider\\" + n + "\\作者信息.txt", true);
        final BufferedWriter bw = new BufferedWriter(fw);
        bw.write(name + "\r\n");

        // 设置正则表达式
        // List<String> validator = setValidator();

        try {
            input = new FileInputStream(pdfFile);
            // 加载 pdf 文档
            PDFParser parser = new PDFParser(input);
            parser.parse();
            document = parser.getPDDocument();
            // 获取内容信息
            PDFTextStripper pts = new PDFTextStripper();
            String content;
            content = pts.getText(document);
            for (final String string : username) {
                StringLineStream.stream(content, new StringLineStream.CallBack() {
                    @SuppressWarnings("unused")
                    @Override
                    public boolean deal(String line, int i, String[] split) {
                        if (line.contains(string)) {
                            try {
                                bw.write(split[i]);
                                bw.write(split[i + 1]);
                                bw.write(split[i + 2]);
                                bw.write(split[i + 3]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            // 依次对各个正则表达式进行检测，并保存作者信息
            // ValidateAndSaveInfo(username, content, bw);
            // System.out.println(content);
            // TODO 添加换行
            bw.write("\r\n");
            bw.close();
            fw.close();
        } finally {
            if (null != input)
                input.close();
            if (null != document)
                document.close();
        }
    }

    /**
     * 获取文件作者名称
     *
     * @param name
     * @return
     */
    @SuppressWarnings("JavaDoc")
    private List<String> getWriterName(String name) {
        String[] s = name.split("-");
        String[] list = s[2].split(",");
        List<String> names = new ArrayList<String>();
        for (String string : list) {
            names.add(string);
            System.out.println(string);
        }
        return names;
    }

    /**
     * 依次对各个正则表达式进行检测FF
     *
     * @param username
     * @param content
     */
    @SuppressWarnings({"unused", "JavaDoc"})
    private void ValidateAndSaveInfo(List<String> username, String content, BufferedWriter bw) {
        for (String string : username) {
            Pattern pattern = Pattern.compile(string);
            Matcher matcher = pattern.matcher(content);
            // while (matcher.find())
            // // TODO 存储作者信息
            if (matcher.find()) {
                try {
                    bw.write(matcher.group() + "\r\n");
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(matcher.group());
            }
        }
    }

    /**
     * TODO 设置正则表达式
     *
     * @return
     */
    @SuppressWarnings({"unused", "JavaDoc"})
    private List<String> setValidator() {
        List<String> validator = new ArrayList<String>();
        validator.add("证券分析师：[^\r\n]+");
        validator.add("分析师：[^\r\n]+");
        validator.add("研究助理：[^\r\n]+");
        validator.add("助理分析师：[^\r\n]+");
        return validator;
    }
}
