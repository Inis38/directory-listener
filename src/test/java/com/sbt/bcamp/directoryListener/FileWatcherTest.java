package com.sbt.bcamp.directoryListener;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileWatcherTest {

    FileTest fileTest = new FileTest();

    @Test
    public void fileWatcherTest() throws InterruptedException, IOException {

        File folder = new File(Main.FOLDER_NAME);
        folder.mkdir();
        final Map<String, String> map = new HashMap<>();

        FileWatcher watcher = new FileWatcher(folder);
        watcher.addListener(event -> map.put("file.created", event.getFile().getName()))
                .watch();
        Assert.assertEquals(1, watcher.getListeners().size());

        Thread.sleep(1000);

        Path path = fileTest.createFile("fileWatcher.xml");
        Thread.sleep(10000);
        fileTest.deleteFile(path);

        Assert.assertEquals(path.getFileName().toString(), map.get("file.created"));
    }


}