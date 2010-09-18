package org.fanhongtao.tools.chmbuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

import org.fanhongtao.log.LogUtils;
import org.fanhongtao.log.RunLogger;


/**
 * 将指定目录下的 .htm/.html 文件生成可以供 Microsoft HTML Help WorkShop 使用的 .hhp、.hhc、.hhk 文件。<br>
 * 然后调用 Microsoft HTML Help WorkShop 中的 hhc.exe 生成CHM文件。<br>
 * 
 * @author Dharma
 * @created 2008-11-12
 */
public class ChmBuilder
{
    /** HTML文件所在目录 */
    private String htmlPath = null;

    /** CHM文件启动时显示的页面（含路径） */
    private String defaultPage = null;

    /** 要生成的CHM文件名（含路径） */
    private String chmFileName = null;

    /** 要生成的CHM文件的标题 */
    private String chmTitle = null;

    /** .hhp文件的文件名（含路径） */
    private String hhpFileName = null;

    /** .hhc文件的文件名（含路径） */
    private String hhcFileName = null;

    /** .hhk文件的文件名（含路径） */
    private String hhkFileName = null;

    /**
     * 
     * @param chmFileName : 
     * @param htmlPath
     * @param defaultPage
     */
    public ChmBuilder(String chmFileName, String htmlPath, String defaultPage, String chmTitle)
    {
        super();
        this.chmFileName = new File(chmFileName).getAbsolutePath();
        this.htmlPath = new File(htmlPath).getAbsolutePath();
        this.defaultPage = new File(defaultPage).getAbsolutePath();
        this.chmTitle = chmTitle;

        // 生成相应的文件名
        String basePath = new File(htmlPath).getParent();
        String baseName = new File(chmFileName).getName();
        if (baseName.endsWith(".chm"))
        {
            baseName = baseName.substring(0, baseName.length() - 4);
        }
        if (this.defaultPage.startsWith(basePath + "\\"))
        {
            this.defaultPage = this.defaultPage.substring(basePath.length() + 1);
        }

        hhpFileName = basePath + "\\" + baseName + ".hhp";
        hhcFileName = basePath + "\\" + baseName + ".hhc";
        hhkFileName = basePath + "\\" + baseName + ".hhk";

        RunLogger.info(".hhp : " + hhpFileName);
        RunLogger.info(".hhc : " + hhcFileName);
        RunLogger.info(".hhk : " + hhkFileName);
        RunLogger.info(".chm : " + chmFileName);
    }

    public void run() throws IOException
    {
        RunLogger.info("Generate .hhp");
        generateHHP();
        RunLogger.info("Generate .hhc");
        generateHHC();
        RunLogger.info("Generate .hhk");
        generateHHK();
        RunLogger.info("Generate .chm");
        generateCHM();
        RunLogger.info("Build success! CHM file: " + chmFileName);
    }

    /**
     * 生成 .hhp 文件
     * @throws IOException 
     */
    private void generateHHP() throws IOException
    {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(hhpFileName)));
        pw.println("[OPTIONS]");
        pw.println("Compatibility=1.1 or later");
        pw.println("Compiled file=" + chmFileName);
        pw.println("Contents file=" + hhcFileName);
        pw.println("Default Window=win1");
        pw.println("Default topic=" + defaultPage);
        pw.println("Display compile progress=Yes");
        pw.println("Full-text search=Yes");
        pw.println("Index file=" + hhkFileName);
        pw.println("Language=0x804 中文(中国)");
        if ((chmTitle != null) && (chmTitle.trim().length() > 0))
        {
            pw.println("Title=" + chmTitle);
        }
        pw.println("");

        // 指定Windows属性，主要是为了实现 “书签”功能
        pw.println("[WINDOWS]");
        StringBuffer sb = new StringBuffer(1024);
        sb.append("win1=,\"");
        sb.append(hhcFileName);
        sb.append("\",\"");
        sb.append(hhkFileName);
        sb.append("\",\"");
        sb.append(defaultPage); // default page
        sb.append("\",\"");
        sb.append(defaultPage); // home page
        sb.append("\",,,,,0x23520,,0x104e,,,,,,,,0");
        pw.println(sb.toString());
        pw.println("");

        pw.println("[FILES]");
        printHHC(pw, htmlPath);

        pw.close();
    }

    private void printHHC(PrintWriter pw, String path) throws IOException
    {
        File[] files = new File(path).listFiles();
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++)
        {
            File f = files[i];
            if (f.isDirectory())
            {
                printHHC(pw, f.getCanonicalPath());
            }
            else
            {
                // String fileName = f.getName().toLowerCase();
                // if (fileName.endsWith(".htm") || fileName.endsWith(".html"))
                {
                    pw.println(f.getCanonicalPath());
                }
            }
        }
    }

    /**
     * 生成 .hhc 文件
     * @throws IOException 
     */
    private void generateHHC() throws IOException
    {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(hhcFileName)));
        pw.println("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">");
        pw.println("<HTML>");
        pw.println("<HEAD>");
        pw.println("<!-- Dharma -->");
        pw.println("</HEAD><BODY>");
        pw.println("<OBJECT type=\"text/site properties\">");
        pw.println("\t<param name=\"ImageType\" value=\"Folder\">");
        pw.println("</OBJECT>");
        printHHC(pw, htmlPath, 0);
        pw.println("</BODY></HTML>");
        pw.close();
    }

    private String getIndent(int indentNum)
    {
        StringBuffer sb = new StringBuffer(64);
        for (int i = 0; i < indentNum; i++)
        {
            sb.append("    ");
        }
        return sb.toString();
    }

    private void printHHC(PrintWriter pw, String path, int identNum) throws IOException
    {
        pw.println(getIndent(identNum) + "<UL>");
        File[] files = new File(path).listFiles();
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++)
        {
            File f = files[i];
            if (f.isDirectory())
            {
                pw.println(getIndent(identNum + 1) + "<LI> <OBJECT type=\"text/sitemap\">");
                pw.println(getIndent(identNum + 2) + "<param name=\"Name\" value=\"" + f.getName() + "\">");
                pw.println(getIndent(identNum + 2) + "</OBJECT>");
                printHHC(pw, f.getCanonicalPath(), identNum + 1);
            }
            else
            {
                String fileName = f.getName().toLowerCase();
                if (fileName.endsWith(".htm") || fileName.endsWith(".html"))
                {
                    pw.println(getIndent(identNum + 1) + "<LI> <OBJECT type=\"text/sitemap\">");
                    pw.println(getIndent(identNum + 2) + "<param name=\"Name\" value=\"" + f.getName() + "\">");
                    pw.println(getIndent(identNum + 2) + "<param name=\"Local\" value=\"" + f.getCanonicalPath()
                            + "\">");
                    pw.println(getIndent(identNum + 2) + "</OBJECT>");
                }
            }
        }
        pw.println(getIndent(identNum) + "</UL>");
    }

    /**
     * 生成 .hhk 文件
     * @throws IOException 
     */
    private void generateHHK() throws IOException
    {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(hhkFileName)));
        pw.println("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">");
        pw.println("<HTML>");
        pw.println("<HEAD>");
        pw.println("<!-- Dharma -->");
        pw.println("</HEAD><BODY>");
        pw.println("<UL>");
        printHHK(pw, htmlPath, 0);
        pw.println("</UL>");
        pw.println("</BODY></HTML>");
        pw.close();
    }

    private void printHHK(PrintWriter pw, String path, int identNum) throws IOException
    {
        File[] files = new File(path).listFiles();
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++)
        {
            File f = files[i];
            if (f.isDirectory())
            {
                printHHK(pw, f.getCanonicalPath(), identNum + 1);
            }
            else
            {
                String fileName = f.getName().toLowerCase();
                if (fileName.endsWith(".htm") || fileName.endsWith(".html"))
                {
                    String title = getTitle(f);
                    pw.println(getIndent(1) + "<LI> <OBJECT type=\"text/sitemap\">");
                    pw.println(getIndent(2) + "<param name=\"Name\" value=\"" + title + "\">");
                    pw.println(getIndent(2) + "<param name=\"Name\" value=\"" + title + "\">");
                    pw.println(getIndent(2) + "<param name=\"Local\" value=\"" + f.getCanonicalPath() + "\">");
                    pw.println(getIndent(2) + "</OBJECT>");
                }
            }
        }
    }

    /**
     * 获取HTML文件的<title>属性
     * @param file
     * @return
     * @throws IOException
     */
    private String getTitle(File file) throws IOException
    {
        String title = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        int findStatus = 0;
        while (((line = br.readLine()) != null) && (findStatus < 2))
        {
            int startIndex = 0;
            String temp = line.toLowerCase();
            if (findStatus == 0) // 还没有找到过 <title>
            {
                startIndex = temp.indexOf("<title>");
                if (startIndex != -1)
                {
                    startIndex += 7;
                    findStatus = 1;
                    int stopIndex = temp.indexOf("</title>", startIndex);
                    if (stopIndex != -1) // 一行中同时有<title>和</title>
                    {
                        findStatus = 2;
                        title = line.substring(startIndex, stopIndex);
                        break;
                    }
                    title = line.substring(startIndex); // 有可能<title>后面有内容
                }
            }
            else if (findStatus == 1) // 已经找到过<title>
            {
                startIndex = temp.indexOf("</title>");
                if (startIndex != -1)
                {
                    findStatus = 2;
                    title = title + line.substring(0, startIndex);
                }
                else
                {
                    title = title + line;
                }
            }
        }
        br.close();

        title = title.trim();
        if ((findStatus != 2) || (title.equals("")))
        {
            title = file.getName();
        }

        return title;
    }

    /**
     * 生成 .chm 文件
     * @throws IOException
     */
    private void generateCHM() throws IOException
    {
        Runtime rt = Runtime.getRuntime();
        String[] cmd = new String[2];
        cmd[0] = "hhc.exe";
        cmd[1] = hhpFileName;
        Process proc = rt.exec(cmd);

        new StreamGobbler(proc.getErrorStream(), "ERROR").start();
        new StreamGobbler(proc.getInputStream(), "OUTPUT").start();

        int exitValue;
        try
        {
            exitValue = proc.waitFor();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new IOException("Meet InterruptedException");
        }
        if (exitValue != 1)
        {
            throw new IOException("Complied failed. exitValue is : " + exitValue);
        }
    }

    public String getChmFileName()
    {
        return chmFileName;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length != 4)
        {
            System.out.println("Usage: ChmBuilder  chm-file-name  html-path  default-page  chm-title");
            System.out.println("For example, ");
            System.out.println("       ChmBuilder  d:\\temp\\test.chm  d:\\temp\\html  d:\\temp\\html\\index.html  test");
            return;
        }

        LogUtils.initBasicLog();
        try
        {
            new ChmBuilder(args[0], args[1], args[2], args[3]).run();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Failed to create CHM file. " + e.getLocalizedMessage());
        }
    }

}

class StreamGobbler extends Thread
{
    InputStream is;

    String type;

    OutputStream os;

    StreamGobbler(InputStream is, String type)
    {
        this(is, type, null);
    }

    StreamGobbler(InputStream is, String type, OutputStream redirect)
    {
        this.is = is;
        this.type = type;
        this.os = redirect;
    }

    public void run()
    {
        try
        {
            PrintWriter pw = null;
            if (os != null)
                pw = new PrintWriter(os);

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
            {
                if (pw != null)
                    pw.println(line);
                RunLogger.info(type + ">" + line);
            }
            if (pw != null)
                pw.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
