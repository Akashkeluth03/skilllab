import java.util.*;

public class AssignmentDP {

    public static int minCost(int[][] cost) {
        int n = cost.length;
        int size = 1 << n;

        int[] dp = new int[size];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int mask = 0; mask < size; mask++) {
            int worker = Integer.bitCount(mask);

            for (int task = 0; task < n; task++) {
                if ((mask & (1 << task)) == 0) {
                    int newMask = mask | (1 << task);
                    dp[newMask] = Math.min(
                        dp[newMask],
                        dp[mask] + cost[worker][task]
                    );
                }
            }
        }

        return dp[size - 1];
    }
}