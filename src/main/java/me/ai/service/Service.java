package me.ai.service;

import me.ai.ga.GA;
import me.ai.repo.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Service {
    private Repository repo;

    public Service(Repository repo) {
        this.repo = repo;
    }

    public List<String> generate(int popSize, int nGenerations) {
        GA ga = new GA(repo.getGraph(), popSize);

        List<String> output = new ArrayList<>();

        for (int i = 1; i <= nGenerations; i++) {
            ga.oneGeneration();

            String s = "GEN " + i + " -- Fitness: " +
                    ga.getBestFitness() + " ; Nr: " +
                    ga.getBestCommunitiesNumber();
            output.add(s);
        }

        int[] com = ga.getBestCommunities();
        output.add("");
        output.add("Nod - Comunitate");
        for (int i = 0; i < com.length; i++)
            output.add(i + " - " + com[i]);
        return output;
    }
}
