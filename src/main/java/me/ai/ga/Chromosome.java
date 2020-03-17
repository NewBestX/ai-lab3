package me.ai.ga;

import java.util.Arrays;
import java.util.Objects;

class Chromosome {
    private int[] communities;
    private double fitness;
    private int communitiesNumber;

    public Chromosome(int n) {
        initCommunities(n);
        this.fitness = 0D;
    }

    public Chromosome(int[] communities) {
        this.communities = communities;
        this.fitness = 0D;
    }

    private void initCommunities(int n) {
        communities = new int[n];
        int nr = 1; // Numarul de comunitati existente
        communities[0] = 1;

        for (int i = 1; i < n; i++) {
            int community = Utils.getRandomCommunity(nr + 1);
            communities[i] = community;
            if (community > nr) // Daca nodul i a fost pus intr-o comunitate noua, crestem nr
                nr++;
        }
        communitiesNumber = nr;
    }

    public void mutation() {
        int pos = Utils.getRandom().nextInt(communities.length);
        communities[pos] = Utils.getRandomCommunity(communities.length);
        sequentialCommunities2();
    }

    public Chromosome crossover2(Chromosome other) {
        int pos = Utils.getRandom().nextInt(communities.length);
        int[] newCommunities = new int[communities.length];

        for (int i = 0; i < pos; i++)
            newCommunities[i] = communities[i];

        for (int i = pos; i < communities.length; i++)
            newCommunities[i] = other.getCommunities()[i];

        Chromosome off = new Chromosome(newCommunities);
        off.sequentialCommunities2();
        return off;
    }

    public Chromosome crossover(Chromosome other) {
        int[] newCommunities = new int[communities.length];

        for (int i = 0; i < communities.length; i++) {
            newCommunities[i] = Utils.getRandom().nextInt(2) == 0 ? communities[i] : other.getCommunities()[i];
        }

        Chromosome off = new Chromosome(newCommunities);
        off.sequentialCommunities2();
        return off;
    }

    /**
     * Functie care asigura o distributie continua a comunitatilor, de la 1 la numarul total de comunitati (elimina comunitatile fara niciun nod)
     */
    private void sequentialCommunities() {
        int n = communities.length;
        int[] f = new int[n + 1];
        int nr = 0;

        for (int value : communities) {
            if (f[value] == 0) {
                nr++;
                f[value] = 1;
            }
        }
        communitiesNumber = nr;

        for (int i = 1; i <= nr; i++) {
            if (f[i] == 0) {
                int com = findMaxCommunity(); // Comunitatea care va fi inlocuita cu i

                for (int j = 0; j < n; j++) {
                    if (communities[j] == com)
                        communities[j] = i;
                }
            }
        }
    }

    private int findMaxCommunity() {
        int max = 0;
        for (int value : communities)
            if (value > max)
                max = value;
        return max;
    }

    private void sequentialCommunities2() {
        int n = communities.length;

        int currentCommunity = 1;
        boolean appeared = false;

        for (int i = 0; i < n; i++) {
            if (communities[i] < currentCommunity)
                continue;
            if (communities[i] == currentCommunity) {
                appeared = true;
                continue;
            }
            if (appeared) {
                currentCommunity++;

                if (communities[i] == currentCommunity)
                    continue;

                appeared = false;
            }
            if (communities[i] > currentCommunity) {
                swapCommunityNumber(communities[i], currentCommunity);
                appeared = true;
            }
        }
        communitiesNumber = currentCommunity;
    }

    private void swapCommunityNumber(int a, int b) {
        for (int i = 0; i < communities.length; i++) {
            if (communities[i] == a)
                communities[i] = -1;
        }
        for (int i = 0; i < communities.length; i++) {
            if (communities[i] == b)
                communities[i] = a;
        }
        for (int i = 0; i < communities.length; i++) {
            if (communities[i] == -1)
                communities[i] = b;
        }
    }

    public int[] getCommunities() {
        return communities;
    }

    public void setCommunities(int[] communities) {
        this.communities = communities;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getCommunitiesNumber() {
        return communitiesNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return Double.compare(that.fitness, fitness) == 0 &&
                Arrays.equals(communities, that.communities);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fitness);
        result = 31 * result + Arrays.hashCode(communities);
        return result;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "communities=" + Arrays.toString(communities) +
                ", fitness=" + fitness +
                '}';
    }
}
