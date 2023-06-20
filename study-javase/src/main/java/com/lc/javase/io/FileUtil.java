package com.lc.javase.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author gujixian
 * @since 2023/6/20
 */
public class FileUtil {
    private static final String PATH = "E:\\Download\\XX";
    public static void main(String[] args) throws Exception {
        rename();
        move();
    }

    private static void rename() {
        File dir = new File(PATH);
        File[] subDirs = dir.listFiles(File::isDirectory);
        for (File subDir : subDirs) {
            String name = subDir.getName();
            File[] files = subDir.listFiles();
            if (files == null || files.length <= 0) {
                continue;
            }
            for (File file : files) {
                String fileName = file.getName();
                int dotIndex = fileName.lastIndexOf('.');
                String newName = name + fileName.substring(dotIndex);
                file.renameTo(new File(file.getParent(), newName));
            }
        }
    }

    private static void move() throws IOException {
        Path sourceDir = Paths.get(PATH);

        File[] pathList = sourceDir.toFile().listFiles(File::isDirectory);

        for (File sourceFile : pathList) {
            try {
                File[] files = sourceFile.listFiles();
                if (files == null || files.length <= 0) {
                    continue;
                }
                for (File file : files) {
                    Path targetPath = Paths.get("E:\\Download\\target\\");
                    Files.move(file.toPath(), targetPath.resolve(file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
