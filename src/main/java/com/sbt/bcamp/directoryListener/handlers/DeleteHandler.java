package com.sbt.bcamp.directoryListener.handlers;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteHandler implements Handler {

    private static final Logger logger = Logger.getLogger(DeleteHandler.class);

    private Path path;

    @Override
    public void set(Path path) {
        this.path = path;
    }

    @Override
    public void run() {
        logger.info("Расширение файла '" + path.getFileName().toString() + "' не соответсвует ожидаемому, удаляем.");
        try {
            Files.delete(path);
        } catch (IOException e) {
            logger.error("Не удалось удалить файл.");
        }
    }
}
