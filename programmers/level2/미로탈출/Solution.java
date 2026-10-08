import java.util.*;

/**
 * 미로 탈출
 * https://school.programmers.co.kr/learn/courses/30/lessons/159993
 * 분류: 연습문제 (Lv2)
 * <p>
 * [문제 요약]
 * 격자 미로에서 시작 지점(S)을 출발해 먼저 레버(L) 칸에 들른 뒤 출구(E) 칸으로 가야 탈출한다.
 * 상하좌우로 한 칸 이동에 1초가 걸리고, 벽(X)은 지나갈 수 없다.
 * 레버를 당기기 전에도 출구 칸을 지나갈 수 있고, 모든 칸은 여러 번 지나갈 수 있다.
 * 탈출에 걸리는 최소 시간을 구하고, 탈출할 수 없으면 -1을 반환한다.
 * <p>
 * [제한사항]
 * - 5 <= maps.length <= 100
 * - 5 <= maps[i].length() <= 100
 * - maps[i]는 S(시작), E(출구), L(레버), O(통로), X(벽)로만 이루어짐
 * - S, E, L은 각각 정확히 1개씩, 서로 다른 칸에 있음
 */
class Solution {

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    int[][] grid;
    Node start, lever, end;

    static class Node {
        int r,c, dist;

        public Node(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }

    public int solution(String[] maps) {
        int n = maps.length;
        int m = maps[0].length();
        grid = new int[n][m];

        for (int i = 0; i < n; i++) {
            char[] chars = maps[i].toCharArray();
            for (int j = 0; j < m; j++) {
                if (chars[j] == 'S') start = new Node(i, j,0);
                else if (chars[j] == 'L') lever = new Node(i, j,0);
                else if (chars[j] == 'E') end = new Node(i, j,0);
                grid[i][j] = (chars[j] == 'X') ? 0 : 1;
            }
        }

        int toLever = bfs(start, lever, n, m);
        if(toLever == -1) return -1;

        int toEnd = bfs(lever, end, n, m);
        if(toEnd == -1) return -1;
        return toLever + toEnd;
    }

    private int bfs(Node from, Node to, int n, int m) {
        boolean[][] visited = new boolean[n][m];
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.offer(from);
        visited[from.r][from.c] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int r = current.r;
            int c = current.c;

            if(r == to.r && c == to.c ) return current.dist;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if(nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
                if(grid[nr][nc] == 0) continue;
                if(visited[nr][nc]) continue;
                queue.offer(new Node(nr, nc, current.dist +1));
                visited[nr][nc] = true;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[]{"SOOOL", "XXXXO", "OOOOO", "OXXXX", "OOOOE"}, 16);
        // 입출력 예 #2
        test(sol, new String[]{"LOOXS", "OOOOX", "OOOOO", "OOOOO", "EOOOO"}, -1);
    }

    private static void test(Solution sol, String[] maps, int expected) {
        int result = sol.solution(maps.clone());
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] maps=%s | expected=%d, actual=%d%n",
                status, Arrays.toString(maps), expected, result);
    }
}
