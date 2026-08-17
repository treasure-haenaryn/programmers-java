import java.util.*;

/**
 * 게임 맵 최단거리
 * https://school.programmers.co.kr/learn/courses/30/lessons/1844
 * 분류: 깊이/너비 우선 탐색(DFS/BFS) (Lv2)
 *
 * [문제 요약]
 * n x m 격자 맵(0=벽, 1=통로)에서 (1,1)에서 시작해 상하좌우로만 이동해
 * (n,m)까지 가는 최단 칸 수를 구한다. 도달 불가능하면 -1을 반환한다.
 *
 * [제한사항]
 * - maps는 n x m 크기의 2차원 배열 (1 <= n, m <= 100)
 * - n과 m이 모두 1인 경우는 없다
 * - 배열 값은 0(벽) 또는 1(통로)
 */
class Solution {

    private static int[] dy = {1, -1, 0, 0};
    private static int[] dx = {0, 0, 1, -1};

    private static class Node {
        int y;
        int x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    public int solution(int[][] maps) {
        return bfs(maps);
    }

    private static int bfs(int [][] maps){
        int[][] dist = new int[maps.length][maps[0].length];
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[0].length; j++) {
                dist[i][j] = -1;
            }
        }

        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(0,0));
        dist[0][0] = 1;

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int y = node.y;
            int x = node.x;

            for (int i = 0; i < 4; i++) {
                int ny = y + dy[i];
                int nx = x + dx[i];
                if (ny < 0 || ny >= maps.length || nx < 0 || nx >= maps[0].length || maps[ny][nx] == 0 || dist[ny][nx] != -1) continue;
                queue.offer(new Node(ny, nx));
                dist[ny][nx] = dist[y][x] + 1;
            }
        }

        return dist[maps.length-1][maps[0].length-1];
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[][]{
                {1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 0, 1},
                {0, 0, 0, 0, 1}
        }, 11);
        // 입출력 예 #2
        test(sol, new int[][]{
                {1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 0, 0},
                {0, 0, 0, 0, 1}
        }, -1);
    }

    private static void test(Solution sol, int[][] maps, int expected) {
        int result = sol.solution(maps);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
