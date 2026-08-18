import java.util.*;

class Solution {

    public int solution(String begin, String target, String[] words) {

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.add(begin);
        visited.add(begin);

        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String current = queue.poll();

                if (current.equals(target)) {
                    return count;
                }

                for (String word : words) {
                    if (!visited.contains(word)
                            && canConvert(current, word)) {
                        visited.add(word);
                        queue.add(word);
                    }
                }

            }
            count++;
        }

        return 0;
    }

    private boolean canConvert(String a, String b) {
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diff++;
            }
            if (diff > 1) {
                return false;
            }
        }
        return diff == 1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, "hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"}, 4);
        // 입출력 예 #2
        test(sol, "hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log"}, 0);
    }

    private static void test(Solution sol, String begin, String target, String[] words, int expected) {
        int result = sol.solution(begin, target, words);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}