package com.sbt.bcamp.directoryListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileWatcher implements Runnable {

    protected List<FileListener> listeners = new ArrayList<>();
    private final File folder;
    protected static final List<WatchService> watchServices = new ArrayList<>();

    public FileWatcher(File folder) {
        this.folder = folder;
    }

    public void watch() {
        if (folder.exists()) {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(folder.getAbsolutePath());
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            watchServices.add(watchService);
            boolean poll = true;
            while (poll) {
                poll = pollEvents(watchService);
            }
        } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected boolean pollEvents(WatchService watchService) throws InterruptedException {
        WatchKey key = watchService.take();
        Path path = (Path) key.watchable();
        for (WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(event.kind(), path.resolve((Path) event.context()).toFile());
        }
        return key.reset();
    }

    protected void notifyListeners(WatchEvent.Kind<?> kind, File file) {
        FileEvent event = new FileEvent(file);
        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
            for (FileListener listener : listeners) {
                listener.onCreated(event);
            }
        }
        if (file.isDirectory()) {
            new FileWatcher(file).setListeners(listeners).watch();
        }
    }

    public FileWatcher addListener(FileListener listener) {
        listeners.add(listener);
        return this;
    }

    public List<FileListener> getListeners() {
        return listeners;
    }

    public FileWatcher setListeners(List<FileListener> listeners) {
        this.listeners = listeners;
        return this;
    }
}
