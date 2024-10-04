package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;



public class Main {
    public static void main(String[] args) throws IOException {
        createBackup("src/testDir");

        int[] array = {1,1,2,0,3,0,2,2,1};
        writeArray(array);

    }

    public static void createBackup (String path) throws IOException{
        File dirWithFiles = new File(path);
        File[] listOfFiles = dirWithFiles.listFiles();

        Path backupPath = Files.createDirectories(Paths.get(path + "/" + "backup"));

            for (File file : listOfFiles) {
                if(file.isFile()) {
                    Path sourcePath = Paths.get(file.getPath());
                    Path destPath = Paths.get(backupPath + "/" + "backup_" + file.getName());

                    try {
                        Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    public static void writeArray (int[] array) {
        if(array.length != 9) {
            throw new ArrayLengthException("В массиве должно быть 9 чисел!");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0 && array[i] != 1 && array[i] != 2 && array[i] != 3) {
                throw new NumberException("Число должно быть равно 0,1,2 или 3");
            }
        }
        byte[] bytes = new byte[3];


        for (int i = 0; i < array.length; i++) {
            int byteIndex = i / 4;
            int bitIndex = (3 - (i % 4)) * 2;
            bytes[byteIndex] |= (array[i] << bitIndex);
        }


        try (FileOutputStream fos = new FileOutputStream("task2.txt")) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}