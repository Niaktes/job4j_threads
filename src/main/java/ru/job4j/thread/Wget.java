package ru.job4j.thread;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;
    private final String targetFileName;

    public Wget(String url, int speed, String targetFileName) {
        this.url = url;
        this.speed = speed;
        this.targetFileName = targetFileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(targetFileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead = 0;
            while (bytesRead != -1) {
                long start = System.currentTimeMillis();
                bytesRead = in.read(dataBuffer, 0, 1024);
                if (bytesRead != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                long elapsedTime = System.currentTimeMillis() - start;
                if (elapsedTime * speed < 1000) {
                    Thread.sleep(1000 - elapsedTime);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        argumentsValidation(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }

    private static void argumentsValidation(String[] arguments) {
        if (arguments.length != 3) {
            throw new IllegalArgumentException("Wrong number of arguments! There must be only two arguments.");
        }
        if (arguments[0].isBlank() || !arguments[0].startsWith("https://")) {
            throw new IllegalArgumentException("First argument must be \"https://\" address");
        }
        if (arguments[1].isBlank() || !arguments[1].chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Second argument must be numeric between 1 and 100");
        }
        if (Integer.parseInt(arguments[1]) < 1 || Integer.parseInt(arguments[1]) > 10) {
            throw new IllegalArgumentException("Second argument is download speed and must be between 1 and 100 KBytes/s");
        }
        if (arguments[2].isBlank()) {
            throw new IllegalArgumentException("You must specify the name of the target file!");
        }
    }

}