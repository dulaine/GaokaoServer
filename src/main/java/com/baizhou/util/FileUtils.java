package com.baizhou.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * @author Wiker Yong Email:<a href="mailto:wikeryong@gmail.com">wikeryong@gmail.com</a>
 * @version 1.0-SNAPSHOT
 * @date 2013-11-8 下午6:21:45
 */
@SuppressWarnings("resource")
public class FileUtils {

    /**
     * 获取文件MD5值
     *
     * @param file
     * @return
     */
    public static String getMd5ByFile(File file) throws IOException {
//        String value = null;
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream(file);
//            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
//                    file.length());
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            md5.update(byteBuffer);
//            BigInteger bi = new BigInteger(1, md5.digest());
//            value = bi.toString(16);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != in) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return value;
        return DigestUtils.md5Hex(new FileInputStream(file.getAbsoluteFile()));
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFileLength(File file)
            throws IOException {
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        return fis.available();
    }

    /**
     * 读取文件到二进制
     *
     * @param file
     * @return
     * @throws IOException
     * @author WikerYong Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-3-23 上午11:47:06
     */
    public static byte[] getBytesFromFile(File file)
            throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("不能读取文件: " + file.getName());
        }

        is.close();
        return bytes;
    }

    /**
     * 获取标准文件大小，如30KB，15.5MB
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileSize(File file)
            throws IOException {
        long size = getFileLength(file);
        DecimalFormat df = new DecimalFormat("###.##");
        float f;
        if (size < 1024 * 1024) {
            f = (float) ((float) size / (float) 1024);
            return (df.format(new Float(f).doubleValue()) + " KB");
        } else {
            f = (float) ((float) size / (float) (1024 * 1024));
            return (df.format(new Float(f).doubleValue()) + " MB");
        }

    }

    /**
     * 检查文件是否存在
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean existFile(String fileName)
            throws IOException {
        File file = new File(fileName);
//        if (!file.exists()) {
//            throw new IOException("文件未找到:" + fileName);
//        }
        return file.exists();
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void deleteFile(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
//            throw new IOException("文件未找到:" + fileName);
            return;
        }
        file.delete();
    }

    /**
     * 文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean Exist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 读取文件到字符串
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readFile(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("文件未找到:" + fileName);
        }

        //BufferedReader in = new BufferedReader(new FileReader(file));
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String str = "";
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        in.close();
        return sb.toString();
    }


    /**
     * 读取二进制数据
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static byte[] readByteData(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("文件未找到:" + fileName);
        }
        FileInputStream inSream = new FileInputStream(file);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();

//        return new String(data, charsetName);
        return data;
    }


    /**
     * 获取目录所有所有文件和文件夹
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static List<File> listFiles(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("文件未找到:" + fileName);
        }
        return Arrays.asList(file.listFiles());
    }

    public static List<File> listDeepFiles(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("文件未找到:" + fileName);
        }

        File[] files = file.listFiles();
        List<File> list = new ArrayList<>();
        for (File f : files) {
            listDeepFiles(f, list);
        }

        return list;
    }

    private static void listDeepFiles(File file, List<File> list)
            throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    listDeepFiles(f, list);
                } else {
                    list.add(f);
                }
            }
        } else {
            list.add(file);
        }
    }


    public static String GetExtension(File file) {
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        if (dot > -1) {
            String extension = name.substring(dot + 1, name.length());
            return extension;
        } else {
            return "";
        }
    }

    public static String GetNameWithoutExtension(File file) {
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        if (dot > -1) {
            String fileName = name.substring(0, dot);
            return fileName;
        } else {
            return name;
        }
    }

    /**
     * 获取child文件相对ParentDir的路径
     *
     * @param child
     * @param ParentDir
     * @return
     */
    public static String GetRelatviePath(File child, File ParentDir) {
        String fullPath = child.getAbsolutePath();
        String fullPathTopParent = ParentDir.getAbsolutePath();
        //获取当前文件在目录下的相对路径
        String relativePath = fullPath.substring(fullPath.indexOf(fullPathTopParent) + fullPathTopParent.length() + 1);
        return relativePath;
    }

    /**
     * 获取相对路径
     *
     * @param childFullPath
     * @param fullPathTopParent
     * @return
     */
    public static String GetRelatviePath(String childFullPath, String fullPathTopParent) {
        childFullPath = childFullPath.replace("/", File.separator);
        fullPathTopParent = fullPathTopParent.replace("/", File.separator);
        if (!fullPathTopParent.endsWith(File.separator)) fullPathTopParent += File.separator;
        String fullPath = childFullPath;
        //获取当前文件在目录下的相对路径
        String relativePath = fullPath.substring(fullPath.indexOf(fullPathTopParent) + fullPathTopParent.length());
        return relativePath;
    }

    /**
     * 创建目录
     *
     * @param dir
     */
    public static void mkdir(String dir) {
        String dirTemp = dir;
        File dirPath = new File(dirTemp);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 新建文件
     *
     * @param fileName String 包含路径的文件名 如:E:\phsftp\src\123.txt
     * @param content  String 文件内容
     */
    public static void createNewFile(String fileName, String content)
            throws IOException {
        String fileNameTemp = fileName;
        File filePath = new File(fileNameTemp);
        if (!filePath.exists()) {
            filePath.createNewFile();
        } else {
            deleteFile(fileName);
        }
        FileWriter fw = new FileWriter(filePath);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"));
        //PrintWriter pw = new PrintWriter(fw, "utf-8");
        String strContent = content;
        pw.println(strContent);
        pw.flush();
        pw.close();
        fw.close();
    }

    /***
     * 写二进制文件
     * @param filePath
     * @param bytes
     * @param offset
     * @param len
     * @throws IOException
     */
    public static void WriteBytesFile(String filePath, byte[] bytes, int offset, int len) throws IOException {
        String fileNameTemp = filePath;
        System.out.println("file path " + filePath + " bytes size: " + bytes.length);
        File fileTemp = new File(fileNameTemp);
        if (!fileTemp.exists()) {
            fileTemp.createNewFile();
        } else {
            deleteFile(fileNameTemp);
        }
        //将DataOutputStream与FileOutputStream连接可输出不同类型的数据
        //FileOutputStream类的构造函数负责打开文件kuka.dat，如果文件不存在，
        //则创建一个新的文件，如果文件已存在则用新创建的文件代替。然后FileOutputStream
        //类的对象与一个DataOutputStream对象连接，DataOutputStream类具有写
        //各种数据类型的方法。
        DataOutputStream out = new DataOutputStream(new FileOutputStream(filePath));
        out.write(bytes, offset, len);
        out.close();

    }

    public static void WriteTextFile(String filePath, String content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw);) {
            bw.write(content);
            bw.flush();

            bw.close();
            osw.close();
            fos.close();
        }

    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹路径
     */
    public static void delFolder(String folderPath) {
        // 删除文件夹里面所有内容
        delAllFile(folderPath);
        String filePath = folderPath;
        java.io.File myFilePath = new java.io.File(filePath);
        // 删除空文件夹
        myFilePath.delete();
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path 文件夹路径
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] childFiles = file.list();
        File temp = null;
        for (int i = 0; i < childFiles.length; i++) {
            // File.separator与系统有关的默认名称分隔符
            // 在UNIX系统上，此字段的值为'/'；在Microsoft Windows系统上，它为 '\'。
            if (path.endsWith(File.separator)) {
                temp = new File(path + childFiles[i]);
            } else {
                temp = new File(path + File.separator + childFiles[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separatorChar + childFiles[i]);// 先删除文件夹里面的文件
                delFolder(path + File.separatorChar + childFiles[i]);// 再删除空文件夹
            }
        }
    }

    public static String copyFileToDir(File file, String toFullDir) throws IOException {
        mkdir(toFullDir);
        String destFile = toFullDir + File.separator + (file.getName()).toString();//文件目标位置
        FileInputStream input = new FileInputStream(file);
        FileOutputStream output = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024 * 2];
        int len;
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
        output.flush();
        output.close();
        input.close();

        //设置文件的最后修改日期,保证zip后的md5不变
        File file2 = new File(destFile);
        file2.setLastModified(file.lastModified());
        return destFile;
    }

    public static String moveFiletoFile(String fromFilePath, String toFilePath) throws IOException {
        return moveFiletoFile(new File(fromFilePath), toFilePath);
    }

    public static String moveFiletoFile(File file, String destFile) throws IOException {
//        mkdir(toFullDir);
//        String destFile = toFullDir + File.separator + (file.getName()).toString();//文件目标位置
        FileInputStream input = new FileInputStream(file);
        FileOutputStream output = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024 * 2];
        int len;
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
        output.flush();
        output.close();
        input.close();

        //设置文件的最后修改日期,保证zip后的md5不变
        File file2 = new File(destFile);
        file2.setLastModified(file.lastModified());
        return destFile;
    }

    /**
     * 复制单个文件，传统方式
     *
     * @param srcFile     包含路径的源文件 如：E:/phsftp/src/abc.txt
     * @param fullDirDest 目标文件目录；若文件目录不存在则自动创建 如：E:/phsftp/dest
     * @throws IOException
     */
    public static String copyFileToDir(String srcFile, String fullDirDest)
            throws IOException {
        return copyFileToDir(new File(srcFile), fullDirDest);
    }

    /**
     * 复制文件
     *
     * @param f1 源文件
     * @param f2 目标文件
     * @throws Exception
     */
    public static void copyFile(File f1, File f2)
            throws Exception {
        FileInputStream input = new FileInputStream(f1);
        FileOutputStream output = new FileOutputStream(f2);
        byte[] buffer = new byte[1024 * 2];
        int len;
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
        output.flush();
        output.close();
        input.close();
        f2.setLastModified(f1.lastModified());
    }

    public static void copyFile(String f1, String f2) throws Exception {
        File file1 = new File(f1);
        if(!file1.exists()) return;
        File file2 = new File(f2);
        copyFile(file1, file2);
    }

    /**
     * 复制文件夹
     *
     * @param oldPath String 源文件夹路径 如：E:/phsftp/src
     * @param newPath String 目标文件夹路径 如：E:/phsftp/dest
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath)
            throws IOException {
        // 如果文件夹不存在 则新建文件夹
        mkdir(newPath);
        File file = new File(oldPath);
        String[] files = file.list();
        File temp = null;
        for (int i = 0; i < files.length; i++) {
            if (oldPath.endsWith(File.separator)) {
                temp = new File(oldPath + files[i]);
            } else {
                temp = new File(oldPath + File.separator + files[i]);
            }

            if (temp.isFile()) {
                FileInputStream input = new FileInputStream(temp);
                FileOutputStream output = new FileOutputStream(newPath + "/"
                        + (temp.getName()).toString());
                byte[] buffer = new byte[1024 * 2];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }
                output.flush();
                output.close();
                input.close();
            }
            if (temp.isDirectory()) {// 如果是子文件夹
                copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
            }
        }
    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath 包含路径的文件名 如：E:/phsftp/src/ljq.txt
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFile(String oldPath, String newPath)
            throws IOException {
        copyFileToDir(oldPath, newPath);
        deleteFile(oldPath);
    }

    /**
     * 移动文件到指定目录，不会删除文件夹
     *
     * @param oldPath 源文件目录 如：E:/phsftp/src
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFiles(String oldPath, String newPath)
            throws IOException {
        copyFolder(oldPath, newPath);
        delAllFile(oldPath);
    }

    /**
     * 移动文件到指定目录，会删除文件夹
     *
     * @param oldPath 源文件目录 如：E:/phsftp/src
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
    public static void moveFolder(String oldPath, String newPath)
            throws IOException {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    /**
     * 解压zip文件
     * 说明:本程序通过ZipOutputStream和ZipInputStream实现了zip压缩和解压功能.
     * 问题:由于java.util.zip包并不支持汉字,当zip文件中有名字为中文的文件时,
     * 就会出现异常:"Exception  in thread "main " java.lang.IllegalArgumentException
     * at java.util.zip.ZipInputStream.getUTF8String(ZipInputStream.java:285)
     *
     * @param srcDir  解压前存放的目录
     * @param destDir 解压后存放的目录
     * @throws Exception
     */
    public static void unZip(String srcDir, String destDir)
            throws IOException {
        int leng = 0;
        byte[] b = new byte[1024 * 2];
        /** 获取zip格式的文件 **/
        File[] zipFiles = new ExtensionFileFilter("zip").getFiles(srcDir);
        if (zipFiles != null && !"".equals(zipFiles)) {
            for (int i = 0; i < zipFiles.length; i++) {
                File file = zipFiles[i];
                /** 解压的输入流 * */
                ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null) {
                    File destFile = null;
                    if (destDir.endsWith(File.separator)) {
                        destFile = new File(destDir + entry.getName());
                    } else {
                        destFile = new File(destDir + File.separator + entry.getName());
                    }
                    /** 把解压包中的文件拷贝到目标目录 * */
                    FileOutputStream fos = new FileOutputStream(destFile);
                    while ((leng = zis.read(b)) != -1) {
                        fos.write(b, 0, leng);
                    }
                    fos.close();
                }
                zis.close();
            }
        }
    }

    /**
     * 压缩文件
     * 说明:本程序通过ZipOutputStream和ZipInputStream实现了zip压缩和解压功能.
     * 问题:由于java.util.zip包并不支持汉字,当zip文件中有名字为中文的文件时,
     * 就会出现异常:"Exception  in thread "main " java.lang.IllegalArgumentException
     * at java.util.zip.ZipInputStream.getUTF8String(ZipInputStream.java:285)
     *
     * @param srcDir  压缩前存放的目录
     * @param destDir 压缩后存放的目录
     * @throws Exception
     */
    public static void zip(String srcDir, String destDir)
            throws IOException {
        String tempFileName = null;
        srcDir = srcDir.replace("/", File.separator);
        destDir = destDir.replace("/", File.separator);

        byte[] buf = new byte[1024 * 2];
        int len;
        // 获取要压缩的文件
        File[] files = new File(srcDir).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    if (destDir.endsWith(File.separator)) {
                        tempFileName = destDir + file.getName() + ".zip";
                    } else {
                        tempFileName = destDir + File.separator + file.getName() + ".zip";
                    }
                    FileOutputStream fos = new FileOutputStream(tempFileName);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包

                    ZipEntry ze = new ZipEntry(file.getName());// 压缩包文件名
                    zos.putNextEntry(ze);// 写入新的ZIP文件条目并将流定位到条目数据的开始处

                    while ((len = bis.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                        zos.flush();
                    }
                    bis.close();
                    zos.close();

                }
            }
        }
    }

    public static void zipDir(String srcDir, String destDir)
            throws IOException {
        String tempFileName = null;
        srcDir = srcDir.replace("/", File.separator);
        destDir = destDir.replace("/", File.separator);

        byte[] buf = new byte[1024 * 2];
        int len;
        // 获取要压缩的文件
        File[] files = new File(srcDir).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    if (destDir.endsWith(File.separator)) {
                        tempFileName = destDir + file.getName() + ".zip";
                    } else {
                        tempFileName = destDir + File.separator + file.getName() + ".zip";
                    }
                    FileOutputStream fos = new FileOutputStream(tempFileName);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包

                    ZipEntry ze = new ZipEntry(file.getName());// 压缩包文件名
                    zos.putNextEntry(ze);// 写入新的ZIP文件条目并将流定位到条目数据的开始处

                    while ((len = bis.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                        zos.flush();
                    }
                    bis.close();
                    zos.close();

                }
            }
        }
    }

    /**
     * 读取数据
     *
     * @param inSream
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static String readData(InputStream inSream, String charsetName)
            throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return new String(data, charsetName);
    }


    /**
     * 一行一行读取文件，适合字符读取，若读取中文字符时会出现乱码
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<String> readFileLine(String path)
            throws IOException {
        List<String> datas = new ArrayList<>();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null) {
            datas.add(line);
        }
        br.close();
        fr.close();
        return datas;
    }

    public static void SaveJson(String path, Object jsonObject) throws IOException {
        String json = JSON.toJSONString(jsonObject);
// 保证创建一个新文件
        File file = new File(path);
        if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
            file.getParentFile().mkdirs();
        }
        if (file.exists()) { // 如果已存在,删除旧文件
            file.delete();
        }
        file.createNewFile();
        // 将格式化后的字符串写入文件
        Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        write.write(json);
        write.flush();
        write.close();
    }

}


class ExtensionFileFilter
        implements FileFilter {

    private String extension;

    public ExtensionFileFilter(String extension) {
        this.extension = extension;
    }

    public File[] getFiles(String srcDir) throws IOException {
        return (File[]) FileUtils.listFiles(srcDir).toArray();
    }

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        }

        String name = file.getName();
        // find the last
        int idx = name.lastIndexOf(".");
        if (idx == -1) {
            return false;
        } else if (idx == name.length() - 1) {
            return false;
        } else {
            return this.extension.equals(name.substring(idx + 1));
        }
    }
}