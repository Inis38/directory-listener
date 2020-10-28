package com.sbt.bcamp.directoryListener.handlers;

import com.sbt.bcamp.directoryListener.FileDistributor;
import com.sbt.bcamp.directoryListener.FileTest;
import com.sbt.bcamp.directoryListener.Main;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HandlerTest {

    @Mock
    Appender mockAppender;

    FileTest fileTest = new FileTest();

    @Test
    public void jsonHandlerTest() throws IOException {
        Logger logger = Logger.getLogger(JsonHandler.class);
        logger.addAppender(mockAppender);

        File folder = new File(Main.FOLDER_NAME);
        folder.mkdir();
        String fileName = "test.json";

        Path path = fileTest.createFile(fileName);
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.set(path);
        jsonHandler.run();

        ArgumentCaptor<LoggingEvent> eventArgumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(mockAppender, times(2)).doAppend(eventArgumentCaptor.capture());

        String message = eventArgumentCaptor.getAllValues().get(0).getMessage().toString();
        String message2 = eventArgumentCaptor.getAllValues().get(1).getMessage().toString();

        fileTest.deleteFile(path);

        Assert.assertTrue(message.contains(fileName));
        Assert.assertTrue(message2.contains("Колличество строк: 5"));
    }

    @Test
    public void xmlHandlerTest() throws IOException {
        Logger logger = Logger.getLogger(XmlHandler.class);
        logger.addAppender(mockAppender);

        File folder = new File(Main.FOLDER_NAME);
        folder.mkdir();
        String fileName = "test.xml";

        Path path = fileTest.createFile(fileName);
        XmlHandler xmlHandler = new XmlHandler();
        xmlHandler.set(path);
        xmlHandler.run();

        ArgumentCaptor<LoggingEvent> eventArgumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(mockAppender, times(2)).doAppend(eventArgumentCaptor.capture());

        String message = eventArgumentCaptor.getAllValues().get(0).getMessage().toString();
        String message2 = eventArgumentCaptor.getAllValues().get(1).getMessage().toString();

        fileTest.deleteFile(path);

        Assert.assertTrue(message.contains(fileName));
        Assert.assertTrue(message2.contains("Колличество строк: 5"));
    }

}