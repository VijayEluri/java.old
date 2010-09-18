package org.fanhongtao.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 使用 JDK 自带的java.util.zip包对文件或者目录进行压缩
 * @author Dharma
 * @created 2010-5-14
 */
public class Zip
{
    public static void main(String argv[])
    {
        if (2 != argv.length)
        {
            System.err.println("Usage: Zip SrcFolder  ZipFileName");
            return;
        }
        new Zip().zipFile(argv[0], argv[1]);
    }

    /**
     * 对一个文件或目录（含其子目录）压缩
     * @param srcPath 待压缩的文件或目录名
     * @param zipFileName 生成压缩文件名
     */
    public void zipFile(String srcPath, String zipFileName)
    {
        try
        {
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
            RecursiveZip(new File(srcPath), "", zos);
            zos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 递归实现压缩目录及其子目录
     * @param fDir 要压缩的目录或者文件
     * @param pName 父压缩记录名称，第一次调用应该被设置为一个空字符串""
     * @param zos 压缩输出流
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void RecursiveZip(File fDir, String pName, ZipOutputStream zos) throws FileNotFoundException, IOException
    {
        if (fDir.isDirectory())
        {
            // 如果为目录，ZipEntry名称的尾部应该以反斜杠"/"结尾
            zos.putNextEntry(new ZipEntry(pName + fDir.getName() + "/"));
            File[] files = fDir.listFiles();
            if (files != null)
            {
                for (int i = 0; i < files.length; i++)
                {
                    // 进行递归，同时传递父文件ZipEntry的名称，还有压缩输出流
                    RecursiveZip(files[i], pName + fDir.getName() + "/", zos);
                }
            }
        }
        if (fDir.isFile())
        {
            byte[] bt = new byte[2048];
            ZipEntry ze = new ZipEntry(pName + fDir.getName());
            // 设置压缩前的文件大小
            ze.setSize(fDir.length());
            zos.putNextEntry(ze);
            FileInputStream fis = new FileInputStream(fDir);
            int i = 0;
            while ((i = fis.read(bt)) != -1)
            {
                zos.write(bt, 0, i);
            }
            fis.close();
        }
    }
}
