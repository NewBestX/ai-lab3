package me.ai.repo;

import me.ai.domain.IMyGraph;
import me.ai.domain.MyGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.gml.GmlImporter;
import org.jgrapht.util.SupplierUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class Repository {
    private IMyGraph g;

    public Repository(String fileName) {
        this.g = new MyGraph(readFile(fileName));
    }

    public IMyGraph getGraph() {
        return g;
    }

    private Graph<Integer, DefaultEdge> readFile(String fileName) {
        Path p = Paths.get("./src/data/" + fileName);
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(new Supplier<Integer>() {
            private int n = 0;

            @Override
            public Integer get() {
                return n++;
            }
        }, SupplierUtil.createSupplier(DefaultEdge.class), false);

        GmlImporter<Integer, DefaultEdge> importer = new GmlImporter<>();
        try {
            importer.importGraph(g, Files.newBufferedReader(p));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return g;
    }
}
