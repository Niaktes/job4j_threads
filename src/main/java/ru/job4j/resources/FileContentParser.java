package ru.job4j.resources;

import java.io.*;
import java.util.function.Predicate;

public class FileContentParser {

    private final File file;

    public FileContentParser(File file) {
        this.file = file;
    }

    public String getContent() {
        return getContent(c -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(c -> Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN);
    }

    private String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                Character character = (char) data;
                if (filter.test(character)) {
                    output.append(character);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

}