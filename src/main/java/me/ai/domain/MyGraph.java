package me.ai.domain;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class MyGraph implements IMyGraph{
    private Graph<Integer, DefaultEdge> graph;

    public MyGraph(Graph<Integer, DefaultEdge> graph) {
        this.graph = graph;
    }

    public int getEdgeValue(int node1, int node2) {
        if (graph.getEdge(node1, node2) == null)
            return 0;
        return 1;
    }

    public int getDegree(int node) {
        return graph.degreeOf(node);
    }

    public Integer getN() {
        return graph.vertexSet().size();
    }

    public Integer getNoEdges() {
        return graph.edgeSet().size();
    }
}


//    private int[][] a;
//    private Integer n;
//    private Integer noEdges;
//    private int[] degrees;
//
//    public MyGraph(int nrNodes, int[][] matrix) {
//        n = nrNodes;
//        a = matrix;
//
//        init();
//    }
//
//    private void init() {
//        degrees = new int[n];
//        noEdges = 0;
//        for (int i = 0; i < n; i++) {
//            int d = 0;
//            for (int j = 0; j < n; j++) {
//                if (a[i][j] == 1)
//                    d++;
//                if (j > i)
//                    noEdges += a[i][j];
//            }
//            degrees[i] = d;
//        }
//    }