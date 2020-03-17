package me.ai;

import me.ai.repo.Repository;
import me.ai.service.Service;
import me.ai.ui.Console;

public class Main {

    public static void main(String[] args) {
        Repository repo = new Repository("dolphins.gml");
        Service s = new Service(repo);
        Console c = new Console(s);

        c.run();
    }
}
