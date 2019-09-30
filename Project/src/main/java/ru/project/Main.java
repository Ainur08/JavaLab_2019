package ru.project;

import ru.downl.Threads;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            Threads thread = new Threads(i, args[i]);
            thread.start();
        }
    }
}
