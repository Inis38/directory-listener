package com.sbt.bcamp.directoryListener.handlers;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Path;

public class XmlHandler extends AbstractHandler {

    private static final Logger logger = Logger.getLogger(XmlHandler.class);

    @Override
    public void run() {
        read(path);
    }

    private void read(Path path) {
        int lineNumber = 0;
        long start = System.currentTimeMillis();

        logger.info("Обработка файла '" + path.getFileName().toString() + "' начата в " + getStartTime(start));

        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(path.toFile()))) {
            while (lineNumberReader.readLine() != null) {
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Файл '" + path.getFileName() + "' обработан. Колличество строк: " + lineNumber + ", время обработки: " + getReadTime(start) + " сек.");
    }
}
