import java.util.*;

/**
 * 무인도 여행 — 대안 풀이 (BFS + int[][] 변환)
 * https://school.programmers.co.kr/learn/courses/30/lessons/154540
 * 분류: 연습문제 (Lv2)
 * <p>
 * [원래 풀이와의 차이]
 * - 원래 풀이: 재귀 DFS → 섬이 클수록 재귀가 깊어짐 (100x100 전부 땅이면 깊이 최대 1만)
 *   → 기본 스택에서는 통과하지만, 스택이 작은 환경(-Xss256k)에서는 StackOverflowError 확인됨
 * - 대안 풀이: 큐를 쓰는 BFS → 재귀가 없어서 섬 크기와 무관하게 스택 걱정 없음
 * - 입력을 처음에 int[][]로 한 번만 변환 (바다 = 0) → 탐색 중 char/'0' 변환 실수가 생길 자리가 없음
 */
class Solution {
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int[] solution(String[] maps) {
        int n = maps.length;
        int m = maps[0].length();

        // 입력을 받자마자 숫자 격자로 변환: 'X' → 0, '1'~'9' → 1~9
        int[][] grid = new int[n][m];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                char ch = maps[r].charAt(c);
                grid[r][c] = (ch == 'X') ? 0 : ch - '0';
            }
        }

        boolean[][] visited = new boolean[n][m];
        List<Integer> islands = new ArrayList<>();

        // 모든 칸을 훑다가 아직 안 간 땅을 만나면 = 새 섬 발견 → 그 섬 전체를 BFS로 합산
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (grid[r][c] == 0 || visited[r][c]) continue;
                islands.add(bfs(grid, visited, r, c));
            }
        }

        if (islands.isEmpty()) return new int[]{-1};
        return islands.stream().mapToInt(Integer::intValue).sorted().toArray();
    }

    // (sr, sc)에서 시작해 연결된 땅을 전부 방문하며 식량 합을 반환
    private int bfs(int[][] grid, boolean[][] visited, int sr, int sc) {
        int n = grid.length, m = grid[0].length;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sr, sc});
        visited[sr][sc] = true;            // 시작 칸도 넣는 순간 방문 처리

        int total = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            total += grid[cur[0]][cur[1]]; // 꺼낸 칸의 식량을 섬 합계에 더함

            for (int i = 0; i < 4; i++) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;   // 맵 밖
                if (grid[nr][nc] == 0) continue;                        // 바다
                if (visited[nr][nc]) continue;                          // 이미 방문

                visited[nr][nc] = true;                                 // 넣는 순간 방문 처리
                queue.offer(new int[]{nr, nc});
            }
        }
        return total;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[]{"X591X", "X1X5X", "X231X", "1XXX1"}, new int[]{1, 1, 27});
        // 입출력 예 #2
        test(sol, new String[]{"XXX", "XXX", "XXX"}, new int[]{-1});
    }

    private static void test(Solution sol, String[] maps, int[] expected) {
        int[] result = sol.solution(maps.clone());
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] maps=%s | expected=%s, actual=%s%n",
                status, Arrays.toString(maps), Arrays.toString(expected), Arrays.toString(result));
    }
}
