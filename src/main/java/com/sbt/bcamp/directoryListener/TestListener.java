package com.sbt.bcamp.directoryListener;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestListener {

    private static final Logger logger = Logger.getLogger(TestListener.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        File folder = new File("target/source");
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            Files.copy(file.toPath(),
                    new File(Main.FOLDER_NAME + "/" + file.getName()).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            logger.info("Файл '" + file.getName() + "' скопирован.");
            Thread.sleep(5000);
        }
        deleteFiles();
    }

    private static void deleteFiles() throws IOException, InterruptedException {
        Thread.sleep(10000);
        Files.walk(Paths.get(Main.FOLDER_NAME))
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        logger.info("Папка очищена");
    }
}
