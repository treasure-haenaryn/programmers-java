import java.util.*;

/**
 * 네트워크
 * https://school.programmers.co.kr/learn/courses/30/lessons/43162
 * 분류: 깊이/너비 우선 탐색(DFS/BFS) (Lv3)
 *
 * [문제 요약]
 * 컴퓨터 n대와 각 컴퓨터 간 연결 정보(인접 행렬) computers가 주어진다.
 * 직접 연결되지 않아도 다른 컴퓨터를 거쳐 연결되면 같은 네트워크로 간주한다.
 * 전체 컴퓨터가 몇 개의 네트워크로 나뉘는지 그 개수를 반환한다.
 *
 * [제한사항]
 * - 1 <= n <= 200
 * - 각 컴퓨터는 0 ~ n-1 정수로 표현
 * - computers[i][j] = 1이면 i번과 j번 컴퓨터가 연결됨
 * - computers[i][i]는 항상 1
 */
class Solution {

    public int solution(int n, int[][] computers) {
        int answer = 0;
        boolean[] visited = new boolean[n];
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < computers.length; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < computers.length; i++) {
            for (int j = 0; j < computers[0].length; j++) {
                if(computers[i][j] == 1) {
                    graph.get(i).add(j);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if(!visited[i]) {
                answer++;
                dfs(graph, visited, i);
            }
        }
        return answer;
    }

    private void dfs(List<List<Integer>> graph, boolean[] visited, int node) {
        visited[node] = true;
        for(int next : graph.get(node)) {
            if(!visited[next]) {
                dfs(graph, visited, next);
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 3, new int[][]{
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}
        }, 2);
        // 입출력 예 #2
        test(sol, 3, new int[][]{
                {1, 1, 0},
                {1, 1, 1},
                {0, 1, 1}
        }, 1);
    }

    private static void test(Solution sol, int n, int[][] computers, int expected) {
        int result = sol.solution(n, computers);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}