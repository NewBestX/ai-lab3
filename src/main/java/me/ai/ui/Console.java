package me.ai.ui;

import me.ai.service.Service;

import java.util.Scanner;

public class Console {
    private Service serv;

    public Console(Service serv) {
        this.serv = serv;
    }

    public void run() {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Numar de generatii: ");
        int nGen = Integer.parseInt(keyboard.nextLine());

        System.out.println("Dimensiunea populatiei: ");
        int pop = Integer.parseInt(keyboard.nextLine());

        serv.generate(pop, nGen).forEach(System.out::println);
    }
}
