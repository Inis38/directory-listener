package com.sbt.bcamp.directoryListener;

import com.sbt.bcamp.directoryListener.handlers.DeleteHandler;
import com.sbt.bcamp.directoryListener.handlers.Handler;
import com.sbt.bcamp.directoryListener.handlers.JsonHandler;
import com.sbt.bcamp.directoryListener.handlers.XmlHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FileDistributor {

    private static final Logger logger = Logger.getLogger(FileDistributor.class);
    private final Path path;

    public FileDistributor(Path path) {
        this.path = path;
        String fileExtension = getExtension(path.getFileName().toString());
        String fileCreationTime = getFileCreationTime(path);
        logger.info("Обнаружен новый файл '" + path.getFileName() + "', расширение '" + fileExtension + "', дата создания " + fileCreationTime);
        Map<String, Supplier<Handler>> instance = initializeInstance();

        Supplier<Handler> supplier = instance.get(fileExtension);

        if (supplier != null) {
            process(supplier.get());
        } else {
            process(new DeleteHandler());
        }
    }

    private void process(Handler handler) {
        handler.set(path);
        Thread thread = new Thread(handler);
        thread.start();
    }

    private String getExtension(String fileName) {
        if (!fileName.contains(".")) {
            return "";
        }
        int start = fileName.lastIndexOf(".");
        return fileName.substring(start + 1);
    }

    private String getFileCreationTime(Path path) {
        BasicFileAttributes attributes;
        Date fileCreationTime;
        SimpleDateFormat format;
        try {
            attributes = Files.readAttributes(path, BasicFileAttributes.class);
            fileCreationTime = new Date(attributes.creationTime().toMillis());
            format = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm:ss");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return format.format(fileCreationTime);
    }

    private Map<String, Supplier<Handler>> initializeInstance() {
        Map<String, Supplier<Handler>> instance = new HashMap<>();
        instance.put("json", JsonHandler::new);
        instance.put("xml", XmlHandler::new);
        return instance;
    }
}
