package com.sbt.bcamp.directoryListener;

import java.io.File;

public class Main {

    public static final String FOLDER_NAME = "folder";

    public static void main(String[] args) {

        File folder = new File(FOLDER_NAME);
        folder.mkdir();
        FileWatcher watcher = new FileWatcher(folder);
        watcher.addListener(new FileAdapter()).watch();
    }
}
