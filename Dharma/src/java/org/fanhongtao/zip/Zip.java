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
 * ʹ�� JDK �Դ���java.util.zip�����ļ�����Ŀ¼����ѹ��
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
     * ��һ���ļ���Ŀ¼��������Ŀ¼��ѹ��
     * @param srcPath ��ѹ�����ļ���Ŀ¼��
     * @param zipFileName ����ѹ���ļ���
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
     * �ݹ�ʵ��ѹ��Ŀ¼������Ŀ¼
     * @param fDir Ҫѹ����Ŀ¼�����ļ�
     * @param pName ��ѹ����¼���ƣ���һ�ε���Ӧ�ñ�����Ϊһ�����ַ���""
     * @param zos ѹ�������
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void RecursiveZip(File fDir, String pName, ZipOutputStream zos) throws FileNotFoundException, IOException
    {
        if (fDir.isDirectory())
        {
            // ���ΪĿ¼��ZipEntry���Ƶ�β��Ӧ���Է�б��"/"��β
            zos.putNextEntry(new ZipEntry(pName + fDir.getName() + "/"));
            File[] files = fDir.listFiles();
            if (files != null)
            {
                for (int i = 0; i < files.length; i++)
                {
                    // ���еݹ飬ͬʱ���ݸ��ļ�ZipEntry�����ƣ�����ѹ�������
                    RecursiveZip(files[i], pName + fDir.getName() + "/", zos);
                }
            }
        }
        if (fDir.isFile())
        {
            byte[] bt = new byte[2048];
            ZipEntry ze = new ZipEntry(pName + fDir.getName());
            // ����ѹ��ǰ���ļ���С
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
