import java.util.*;

/**
 * 카카오프렌즈 컬러링북
 * https://school.programmers.co.kr/learn/courses/30/lessons/1829
 * 분류: 2017 카카오코드 예선 (Lv2)
 * <p>
 * [문제 요약]
 * m x n 그림의 각 칸에 색상 값이 있고, 상하좌우로 이어진 같은 색 칸들을 하나의 영역으로 본다.
 * 값이 0인 칸은 색칠하지 않는 칸이라 영역으로 세지 않는다.
 * [영역의 개수, 가장 큰 영역의 칸 수]를 반환한다.
 * <p>
 * [제한사항]
 * - 1 ≤ m, n ≤ 100
 * - picture 원소: 0 이상 2^31 - 1 이하
 * - 값이 같아도 상하좌우로 이어지지 않으면 다른 영역
 */
class Solution {

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    int M, N;
    int[][] pictures;
    boolean[][] visited;
    int maxArea;
    int count;

    public int[] solution(int m, int n, int[][] picture) {
        N = m;
        M = n;
        pictures = picture;
        visited = new boolean[N][M];
        maxArea = Integer.MIN_VALUE;
        count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int type = pictures[i][j];
                if (!visited[i][j] && type != 0) {
                    int size = bfs(type, new int[]{i, j});
                    maxArea = Math.max(maxArea, size);
                    count++;
                }
            }
        }

        return new int[]{count, maxArea};
    }

    private int bfs(int type, int[] start) {
        int size = 1;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                if(visited[nr][nc]) continue;
                if(pictures[nr][nc] != type) continue;
                queue.offer(new int[]{nr, nc});
                visited[nr][nc] = true;
                size++;
            }
        }
        return size;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 6, 4, new int[][]{{1, 1, 1, 0}, {1, 2, 2, 0}, {1, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 3}, {0, 0, 0, 3}}, new int[]{4, 5});
    }

    private static void test(Solution sol, int m, int n, int[][] picture, int[] expected) {
        int[] result = sol.solution(m, n, picture);
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%s, actual=%s%n", status, Arrays.toString(expected), Arrays.toString(result));
    }
}
