package com.sbt.bcamp.directoryListener;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileWatcherTest {

    private static final Logger logger = Logger.getLogger(FileWatcherTest.class);

    @Test
    public void testWatch() throws InterruptedException, IOException {
        File folder = new File("json");
        final Map<String, String> map = new HashMap<>();
        FileWatcher watcher = new FileWatcher(folder);
        watcher.addListener(new FileListener() {
            @Override
            public void onCreated(FileEvent event) {
                map.put("file.created", event.getFile().getName());
                System.out.printf("File %s created.\n", event.getFile().getName());
            }
        }).watch();
        Assert.assertEquals(1, watcher.getListeners().size());
        Thread.sleep(5000);
        File file = new File(folder + "/test.txt");
        if (file.exists()) {
            Files.delete(file.toPath());
            Thread.sleep(5000);
        }
        Files.createFile(file.toPath());
        Thread.sleep(10000);
        Assert.assertEquals(file.getName(), map.get("file.created"));
    }
}