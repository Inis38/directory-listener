package com.sbt.bcamp.directoryListener;

import java.io.File;

public class FileAdapter implements FileListener {

    @Override
    public void onCreated(FileEvent event) {

        File file = event.getFile();
        new FileDistributor(file.toPath());
    }
}
