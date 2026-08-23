import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        HashMap<String, Integer> completions = new HashMap<>();
        Arrays.stream(completion).forEach(c -> completions.put(c, completions.getOrDefault(c, 0) + 1));

        for (String p : participant) {
            if(completions.getOrDefault(p,0)==0) {
                return p;
            }
            completions.put(p, completions.get(p) - 1);
        }
        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[]{"leo", "kiki", "eden"}, new String[]{"eden", "kiki"}, "leo");
        // 입출력 예 #2
        test(sol, new String[]{"marina", "josipa", "nikola", "vinko", "filipa"}, new String[]{"josipa", "filipa", "marina", "nikola"}, "vinko");
        // 입출력 예 #3
        test(sol, new String[]{"mislav", "stanko", "mislav", "ana"}, new String[]{"stanko", "ana", "mislav"}, "mislav");
    }

    private static void test(Solution sol, String[] participant, String[] completion, String expected) {
        String result = sol.solution(participant, completion);
        String status = result.equals(expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%s, actual=%s%n", status, expected, result);
    }
}