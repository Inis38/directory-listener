package com.sbt.bcamp.directoryListener;

import java.util.EventListener;

public interface FileListener extends EventListener {

    void onCreated(FileEvent event);
}
