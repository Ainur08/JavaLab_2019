package com.company;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class Threads extends Thread {
    private int count;
    private String adress;
    private String type;

    public Threads(int i, String adress) {
        this.count = i + 1;
        this.adress = adress;
        String[] string = adress.split("/");
        type = string[string.length - 1];
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread N" + count + " started");
            URL url = new URL(adress);
            URLConnection connection = url.openConnection(); //устанавливаем соединение

            InputStream inputStream = connection.getInputStream();
            Files.copy(inputStream, new File(type).toPath());
            System.out.println("Thread N" + count + " finished");
        } catch (IOException e) {
            throw new IllegalArgumentException("Неверно введена(ы) ссылка(и) или файл уже существует");
        }
    }
}
