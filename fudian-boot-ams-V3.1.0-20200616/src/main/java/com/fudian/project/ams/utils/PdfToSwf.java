package com.fudian.project.ams.utils;

import java.io.*;

public class PdfToSwf {
    public static int convertPDF2SWF(String sourcePath, String destPath, String fileName) throws IOException {
////目标路径不存在则建立目标路径
        File dest = new File(destPath);
        if (!dest.exists()) dest.mkdirs();

        //源文件不存在则返回
        File source = new File(sourcePath);
        if (!source.exists()) return 0;

        //调用pdf2swf命令进行转换
//        String command = "D:\\swftools\\pdf2swf.exe" + " -o \"" + destPath  + fileName +"\"  <SPAN style='COLOR: #ff0000'>-s languagedir=D:\\xpdf\\xpdf-chinese-simplified</SPAN> -s flashversion=9 \"" + sourcePath + "\"";
//        String command = "D:\\swftools\\pdf2swf.exe" + " -o \"" + destPath  +  fileName +"\" -s flashversion=9 \"" + sourcePath + "\"";
        String command= "D:/SWFTools/pdf2swf.exe  -t \""+destPath+"\\Java.pdf\" -o  \""+destPath+"\\test.swf\" -s flashversion=9 -s languagedir=D:\\xpdf\\xpdf-chinese-simplified ";
        System.out.println("cmd:"+command);
//        Process pro = Runtime.getRuntime().exec(command);
        Process process = Runtime.getRuntime().exec(command); // 调用外部程序
        final InputStream is1 = process.getInputStream();
        new Thread(new Runnable() {
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(is1));
                try {
                    while(br.readLine()!= null) ;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start(); // 启动单独的线程来清空process.getInputStream()的缓冲区
        InputStream is2 = process.getErrorStream();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
        StringBuilder buf = new StringBuilder(); // 保存输出结果流
        String line = null;
        while((line = br2.readLine()) != null) buf.append(line); // 循环等待ffmpeg进程结束
        System.out.println("输出结果为：" + buf);

//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        while (br2.readLine() != null);

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return process.exitValue();


    }


    public static void main(String []args) throws IOException {
//        String sourcePath = "D:\\Java.pdf";
//        String destPath = "D:\\";
//        String fileName = "Javssa.swf";
//        try{
//            PdfToSwf.convertPDF2SWF(sourcePath, destPath, fileName);
//
//        }catch(Exception ex)
//        {
//            System.out.println("error");
//        }
//        System.out.println("success");



    }
}
