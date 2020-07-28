package cn.bo.project.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author zhangbo
 * @Date 2020/7/28 22:29
 * @Description
 * @PackageName cn.bo.project.util
 **/
public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 创建文件夹
     * @param folderPath
     * @return
     */
    public static boolean createFolder(String folderPath) {
        boolean ret = true;
        try {
            File myFilePath = new File(folderPath);
            if ((!myFilePath.exists()) && (!myFilePath.isDirectory())) {
                myFilePath.mkdirs();
            }
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new RuntimeException("文件未找到");
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                log.info("reading...");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new RuntimeException("读取文件错误");
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new RuntimeException("读取文件错误");
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new RuntimeException("读取文件错误");
            }
        }
    }
}