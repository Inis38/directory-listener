package com.sbt.bcamp.directoryListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTest {
    public Path createFile(String fileName) {
        Path path = Paths.get(Main.FOLDER_NAME + "/" + fileName);
        try {
            String str = "1\n2\n3\n4\n5";
            Files.write(path, str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public void deleteFile(Path path) throws IOException {
        Files.delete(path);
    }
}
