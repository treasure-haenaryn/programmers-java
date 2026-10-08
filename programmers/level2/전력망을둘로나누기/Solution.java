import java.util.*;

/**
 * 전력망을 둘로 나누기
 * https://school.programmers.co.kr/learn/courses/30/lessons/86971
 * 분류: 완전탐색 (Lv2)
 *
 * [문제 요약]
 * 송전탑 n개(1~n번)가 전선 n-1개로 하나의 트리처럼 연결되어 있다.
 * 전선 하나를 끊으면 전력망이 두 덩어리로 나뉜다.
 * 두 덩어리의 송전탑 개수 차이(절댓값)가 가장 작아지도록 끊었을 때, 그 차이를 반환한다.
 *
 * [제한사항]
 * - 2 <= n <= 100
 * - wires.length == n - 1, wires[i] = [v1, v2] (1 <= v1 < v2 <= n)
 * - 입력은 항상 하나의 트리 형태
 */
class Solution {

    List<List<Integer>> graph;
    public int solution(int n, int[][] wires) {

        // 송전탑 n개 전선 n-1개 → 노드 n개 간선 n-1개
        // 즉 2차원 배열보다는 인접리스트로 접근


        // 하나를 끊어 두덩어리로 만든다..
        // 지금 생각으론 DFS를 간선 기준으로 나눠 전부 확인...
        // 그럼 N 100 개여서 DFS는 O(2*(N+N-1)*100)대략 이정도 문제 없을듯

        // 갯수 차이가 가장 작아지도록
        // 송전탑은 1부터 시작
        graph = new ArrayList<>(n+1);

        // 인접리스트 초기화
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }


        // 인접리스트 세팅
        for (int i = 0; i < wires.length; i++) {
            graph.get(wires[i][0]).add(wires[i][1]);
            graph.get(wires[i][1]).add(wires[i][0]);
        }


        int min = Integer.MAX_VALUE;

         for (int i = 0; i < wires.length; i++) {
             int[] target = wires[i];

             int dfs = dfs(1, target, new boolean[graph.size()]);
             // int diff = Math.min(min, graph.size() - dfs-1);
             // 수정: 두 덩이의 차이 |한쪽 - (n - 한쪽)| 를 구하고 min 갱신
             int diff = Math.abs(dfs - (n - dfs));
             min = Math.min(min, diff);
         }


        // return -1;
        // 수정: 갱신한 최솟값 반환
        return min;
    }

    private int dfs(int cur, int[] target, boolean[] visited) {
        List<Integer> curGraph = graph.get(cur);
        visited[cur] = true;
        int count = 1;                          // 나 자신

        for (int next : curGraph) {
            if(visited[next] ||  (target[0] == cur && target[1] == next) || (target[0] == next && target[1] == cur) ) {
                continue;
            }
            count += dfs(next, target, visited);
        }

        return count;
    }


    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 9, new int[][]{{1, 3}, {2, 3}, {3, 4}, {4, 5}, {4, 6}, {4, 7}, {7, 8}, {7, 9}}, 3);
        // 입출력 예 #2
        test(sol, 4, new int[][]{{1, 2}, {2, 3}, {3, 4}}, 0);
        // 입출력 예 #3
        test(sol, 7, new int[][]{{1, 2}, {2, 7}, {3, 7}, {3, 4}, {4, 5}, {6, 7}}, 1);
    }

    private static void test(Solution sol, int n, int[][] wires, int expected) {
        int[][] wiresCopy = Arrays.stream(wires).map(int[]::clone).toArray(int[][]::new);
        int result = sol.solution(n, wiresCopy);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] n=%d, wires=%s | expected=%d, actual=%d%n",
                status, n, Arrays.deepToString(wires), expected, result);
    }
}
