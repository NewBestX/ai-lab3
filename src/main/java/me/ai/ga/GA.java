package me.ai.ga;

import me.ai.domain.IMyGraph;

import java.util.ArrayList;
import java.util.List;

public class GA {
    private List<Chromosome> population;
    private IMyGraph g;
    private Chromosome bestChromosome;

    public GA(IMyGraph graph, int popSize) {
        g = graph;
        initPop(popSize);
    }

    private void initPop(int popSize) {
        population = new ArrayList<>();

        for (int i = 0; i < popSize; i++)
            population.add(new Chromosome(g.getN()));
    }

    private void evaluation() {
        double bestFitness = -1000000;
        for (Chromosome c : population) {
            double f = modularity(c.getCommunities());
            c.setFitness(f);

            if (f > bestFitness)
                bestChromosome = c;
        }
    }

    private void updateBestChromosome() {
        double bestFitness = -1000000;
        for (Chromosome c : population) {
            if (c.getFitness() > bestFitness)
                bestChromosome = c;
        }
    }

    private int worstChromosomeIndex() {
        double worstFitness = 1000000;
        int worst = 0;
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getFitness() < worstFitness)
                worst = i;
        }
        return worst;
    }

    private double modularity(int[] communities) {
        double M = 2 * g.getNoEdges();
        double Q = 0.0;

        for (int i = 0; i < g.getN(); i++) {
            for (int j = 0; j < g.getN(); j++) {
                if (communities[i] == communities[j])
                    Q += (g.getEdgeValue(i, j) - g.getDegree(i) * g.getDegree(j) / M);
            }
        }

        return Q * 1 / M;
    }

    private int selection() {
        int pos1 = Utils.getRandom().nextInt(population.size());
        int pos2 = Utils.getRandom().nextInt(population.size());

        if (population.get(pos1).getFitness() > population.get(pos2).getFitness())
            return pos1;
        return pos2;
    }

    public void oneGeneration() {
        List<Chromosome> newPop = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            Chromosome c1 = population.get(selection());
            Chromosome c2 = population.get(selection());

            Chromosome off = c1.crossover(c2);
            off.mutation();
            newPop.add(off);
        }

        population = newPop;
        evaluation();
    }

    public void oneGenerationElitism() {
        List<Chromosome> newPop = new ArrayList<>();
        if (bestChromosome != null)
            newPop.add(bestChromosome);
        else
            newPop.add(population.get(selection()));

        for (int i = 0; i < population.size() - 1; i++) {
            Chromosome c1 = population.get(selection());
            Chromosome c2 = population.get(selection());

            Chromosome off = c1.crossover(c2);
            off.mutation();
            newPop.add(off);
        }

        population = newPop;
        evaluation();
    }

    public void oneGenerationSteadyState() {
        for (int i = 0; i < population.size(); i++) {
            Chromosome c1 = population.get(selection());
            Chromosome c2 = population.get(selection());

            Chromosome off = c1.crossover(c2);
            off.mutation();
            off.setFitness(modularity(off.getCommunities()));

            int worst = worstChromosomeIndex();

            if (off.getFitness() > population.get(worst).getFitness())
                population.set(worst, off);
        }
        updateBestChromosome();
    }

    public double getBestFitness() {
        return bestChromosome.getFitness();
    }

    public int getBestCommunitiesNumber() {
        return bestChromosome.getCommunitiesNumber();
    }

    public int[] getBestCommunities() {
        return bestChromosome.getCommunities();
    }
}
