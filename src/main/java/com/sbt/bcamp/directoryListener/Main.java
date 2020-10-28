package com.sbt.bcamp.directoryListener;

import java.io.File;
import java.io.IOException;

public class Main {

    public static final String FOLDER_NAME = "folder";

    public static void main(String[] args) throws IOException, InterruptedException {

        File folder = new File(FOLDER_NAME);
        folder.mkdir();
        FileWatcher watcher = new FileWatcher(folder);
        watcher.addListener(new FileAdapter()).watch();
    }
}
