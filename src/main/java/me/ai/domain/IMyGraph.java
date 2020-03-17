package me.ai.domain;

public interface IMyGraph {
    int getEdgeValue(int node1, int node2);

    int getDegree(int node);

    Integer getN();

    Integer getNoEdges();
}
