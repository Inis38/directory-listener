package com.sbt.bcamp.directoryListener;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(MockitoJUnitRunner.class)
public class FileDistributorTest {

    @Mock
    Appender mockAppender;

    FileTest fileTest = new FileTest();

    @Test
    public void xmlFileDistributorTest() throws InterruptedException, IOException {

        Logger logger = Logger.getLogger(FileDistributor.class);
        logger.addAppender(mockAppender);

        File folder = new File(Main.FOLDER_NAME);
        folder.mkdir();
        String fileName = "test.xml";

        Path path = fileTest.createFile(fileName);
        FileDistributor fileDistributor = new FileDistributor(path);
        Thread.sleep(1000);

        ArgumentCaptor<LoggingEvent> eventArgumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(mockAppender, times(1)).doAppend(eventArgumentCaptor.capture());

        String message = eventArgumentCaptor.getAllValues().get(0).getMessage().toString();

        fileTest.deleteFile(path);

        Assert.assertTrue(message.contains(fileName));
        Assert.assertTrue(message.contains("расширение 'xml'"));
    }

    @Test
    public void jsonFileDistributorTest() throws InterruptedException, IOException {

        Logger logger = Logger.getLogger(FileDistributor.class);
        logger.addAppender(mockAppender);

        File folder = new File(Main.FOLDER_NAME);
        folder.mkdir();
        String fileName = "test.json";

        Path path = fileTest.createFile(fileName);
        FileDistributor fileDistributor = new FileDistributor(path);
        Thread.sleep(1000);

        ArgumentCaptor<LoggingEvent> eventArgumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(mockAppender, times(1)).doAppend(eventArgumentCaptor.capture());

        String message = eventArgumentCaptor.getAllValues().get(0).getMessage().toString();

        fileTest.deleteFile(path);

        Assert.assertTrue(message.contains(fileName));
        Assert.assertTrue(message.contains("расширение 'json'"));
    }

    @Test
    public void txtFileDistributorTest() throws InterruptedException, IOException {

        Logger logger = Logger.getLogger(FileDistributor.class);
        logger.addAppender(mockAppender);

        File folder = new File(Main.FOLDER_NAME);
        folder.mkdir();
        String fileName = "test.txt";

        Path path = fileTest.createFile(fileName);
        FileDistributor fileDistributor = new FileDistributor(path);
        Thread.sleep(1000);

        ArgumentCaptor<LoggingEvent> eventArgumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(mockAppender, times(2)).doAppend(eventArgumentCaptor.capture());

        String message = eventArgumentCaptor.getAllValues().get(0).getMessage().toString();
        String message2 = eventArgumentCaptor.getAllValues().get(1).getMessage().toString();

        Assert.assertTrue(message.contains(fileName));
        Assert.assertTrue(message.contains("расширение 'txt'"));
        Assert.assertTrue(message2.contains("удаляем"));
    }

}