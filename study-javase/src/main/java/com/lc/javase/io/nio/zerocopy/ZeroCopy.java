package com.lc.javase.io.nio.zerocopy;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author gujixian
 * @since 2023/5/8
 */
public class ZeroCopy {
    public static void main(String[] args) throws Exception {
        String filePath = System.getProperty("user.dir") + "\\study-javase\\src\\main\\java\\com\\lc\\javase\\io\\nio\\zerocopy\\";
        mmap(filePath);
        sendfile(filePath);
    }

    private static void mmap(String filePath) throws Exception{
        String sourceFile = filePath + "a.txt";
        String targetFile = filePath + "b.txt";
        RandomAccessFile source = new RandomAccessFile(sourceFile, "rw");
        // 获取文件通道
        FileChannel sourceChannel = source.getChannel();
        sourceChannel.write(new ByteBuffer[]{ByteBuffer.wrap("1234567890\n".getBytes()), ByteBuffer.wrap("abcdefghij".getBytes())});
        // 将文件映射到内存
        MappedByteBuffer mbb = sourceChannel.map(FileChannel.MapMode.READ_WRITE, 0, sourceChannel.size());
        // 这个 Channel 也可以换成 Socket
        FileChannel targetChannel = FileChannel.open(Paths.get(targetFile), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        // 数据传输
        targetChannel.write(mbb);
        sourceChannel.close();
        targetChannel.close();
    }

    private static void sendfile(String filePath) throws Exception {
        String sourceFile = filePath + "b.txt";
        String targetFile = filePath + "c.txt";
        FileChannel sourceChannel = FileChannel.open(Paths.get(sourceFile), StandardOpenOption.READ);
        long len = sourceChannel.size();
        long position = sourceChannel.position();
        // 这个 Channel 也可以换成 Socket
        FileChannel targetChannel = FileChannel.open(Paths.get(targetFile), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //数据传输
        sourceChannel.transferTo(position, len, targetChannel);
        sourceChannel.close();
        targetChannel.close();
    }
}
