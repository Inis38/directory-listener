package com.sbt.bcamp.directoryListener.handlers;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonHandler extends AbstractHandler {

    private static final Logger logger = Logger.getLogger(JsonHandler.class);

    @Override
    public void run() {
        read(path);
    }

    private void read(Path path) {
        int lineNumber = 0;
        long start = System.currentTimeMillis();

        logger.info("Обработка файла '" + path.getFileName().toString() + "' начата в " + getStartTime(start));

        try {
            lineNumber = Files.readAllLines(path).size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Файл '" + path.getFileName() + "' обработан. Колличество строк: " + lineNumber + ", время обработки: " + getReadTime(start) + " сек.");
    }
}
