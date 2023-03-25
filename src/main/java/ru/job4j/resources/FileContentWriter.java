package ru.job4j.resources;

import java.io.*;

public class FileContentWriter {

    private final File file;

    public FileContentWriter (File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] buffer = content.getBytes();
            out.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}