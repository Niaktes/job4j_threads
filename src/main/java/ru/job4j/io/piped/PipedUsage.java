package ru.job4j.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedUsage {

    public static void main(String[] args) throws IOException {
        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream();
        /*
        варианты без .connect()
        final PipedInputStream in = new PipedInputStream(out);
        ИЛИ
        final PipedOutputStream out = new PipedOutputStream(in);
        */
        /*
        А ещё их можно обернуть в буферизированные потоки:
        BufferedOutputStream buffIn = new BufferedInputStream(in);
        BufferedOutputStream buffOut = new BufferedOutputStream(out);
        Или даже в потоки объектов:
        ObjectInputStream objIn = new ObjectInputStream(in);
        ObjectOutputStream objOut = new ObjectOutputStream(out);
         */
        Thread firstThread = new Thread(() -> {
            try {
                out.write("Job4j".getBytes());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread secondThread = new Thread(() -> {
            try {
                int ch;
                while ((ch = in.read()) != -1) {
                    System.out.print((char) ch);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        in.connect(out);
        firstThread.start();
        secondThread.start();
    }

}