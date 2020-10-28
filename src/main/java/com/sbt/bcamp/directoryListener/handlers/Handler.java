package com.sbt.bcamp.directoryListener.handlers;

import java.nio.file.Path;

public interface Handler extends Runnable {

    void set(Path path);
}
