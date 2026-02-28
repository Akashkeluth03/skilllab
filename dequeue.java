import java.util.*;

public class SlidingWindowMax {

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) return new int[0];

        Deque<Integer> dq = new ArrayDeque<>();
        int[] result = new int[nums.length - k + 1];
        int ri = 0;

        for (int i = 0; i < nums.length; i++) {

            // Remove elements outside window
            while (!dq.isEmpty() && dq.peekFirst() <= i - k)
                dq.pollFirst();

            // Remove smaller elements from back
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i])
                dq.pollLast();

            dq.offerLast(i);

            // Record answer
            if (i >= k - 1)
                result[ri++] = nums[dq.peekFirst()];
        }

        return result;
    }
}