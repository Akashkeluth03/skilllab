import java.util.*;

public class Bridges {

    private int time = 0;

    public List<List<Integer>> findBridges(int n, List<List<Integer>> connections) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (List<Integer> edge : connections) {
            graph[edge.get(0)].add(edge.get(1));
            graph[edge.get(1)].add(edge.get(0));
        }

        int[] disc = new int[n];
        int[] low = new int[n];

        Arrays.fill(disc, -1);

        for (int i = 0; i < n; i++)
            if (disc[i] == -1)
                dfs(i, -1, graph, disc, low, result);

        return result;
    }

    private void dfs(int u, int parent, List<Integer>[] graph,
                     int[] disc, int[] low, List<List<Integer>> result) {

        disc[u] = low[u] = time++;

        for (int v : graph[u]) {
            if (v == parent) continue;

            if (disc[v] == -1) {
                dfs(v, u, graph, disc, low, result);
                low[u] = Math.min(low[u], low[v]);

                if (low[v] > disc[u])
                    result.add(Arrays.asList(u, v));
            } else {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
}