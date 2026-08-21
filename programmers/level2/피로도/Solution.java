import java.util.*;

/**
 * 피로도
 * https://school.programmers.co.kr/learn/courses/30/lessons/87946
 * 분류: 완전탐색 (Lv2)
 *
 * [문제 요약]
 * 유저가 보유한 피로도 k로 여러 던전을 탐험한다.
 * 각 던전은 [최소 필요 피로도, 소모 피로도]를 가지며, 현재 피로도가 최소 필요 피로도 이상일 때만
 * 탐험할 수 있고 탐험하면 소모 피로도만큼 피로도가 줄어든다.
 * 던전을 방문하는 순서를 어떻게 정하느냐에 따라 탐험 가능한 던전 수가 달라지므로,
 * 가능한 모든 순서를 고려해 최대로 탐험할 수 있는 던전 수를 구한다.
 *
 * [제한사항]
 * - k: 1 이상 5,000 이하인 자연수
 * - 던전 개수: 1 이상 8 이하
 * - dungeons[i] = [최소 필요 피로도, 소모 피로도]
 * - 최소 필요 피로도는 항상 소모 피로도 이상
 * - 각 피로도 값은 1 이상 1,000 이하인 자연수
 * - 서로 다른 던전의 피로도 조합이 같을 수 있음
 */
class Solution {

    public int solution(int k, int[][] dungeons) {
        boolean[] visited = new boolean[dungeons.length];
        return dfs(k, dungeons, visited, 0);
    }

    private int dfs(int k, int[][] dungeons, boolean[] visited, int count) {
        int maxCount = count; // 현재 탐험한 던전 수 저장

        for (int i = 0; i < dungeons.length; i++) {
            if(!visited[i] && k >= dungeons[i][0]) {
                visited[i] = true;
                int result = dfs(k - dungeons[i][1], dungeons, visited, count + 1);
                maxCount = Math.max(maxCount, result);
                visited[i] = false; // 백트래킹
            }
        }
        return maxCount;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 80, new int[][]{{80, 20}, {50, 40}, {30, 10}}, 3);
    }

    private static void test(Solution sol, int k, int[][] dungeons, int expected) {
        int result = sol.solution(k, dungeons);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
