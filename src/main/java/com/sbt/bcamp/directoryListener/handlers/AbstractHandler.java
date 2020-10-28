package com.sbt.bcamp.directoryListener.handlers;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract class AbstractHandler implements Handler {

    protected Path path;

    @Override
    public void set(Path path) {
        this.path = path;
    }

    protected String getReadTime(long start) {
        long finish = System.currentTimeMillis() - start;
        return String.format("%.3f", (double) finish / 1000);
    }

    protected String getStartTime(long start) {
        Date now = new Date(start);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        return format.format(now);
    }
}
