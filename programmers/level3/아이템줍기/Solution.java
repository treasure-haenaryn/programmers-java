import java.util.*;

/**
 * 아이템 줍기
 * https://school.programmers.co.kr/learn/courses/30/lessons/87694
 * 분류: 깊이/너비 우선 탐색(DFS/BFS) (Lv3)
 * <p>
 * [문제 요약]
 * 여러 직사각형이 겹쳐서 만들어진 다각형 지형이 있고, 캐릭터는 이 다각형의
 * 테두리(둘레)만을 따라 이동할 수 있다. 캐릭터 시작 위치에서 아이템 위치까지
 * 테두리를 따라 이동하는 최단 거리를 구한다.
 * <p>
 * [제한사항]
 * - rectangle의 원소는 각 직사각형의 [좌측 하단 x, 좌측 하단 y, 우측 상단 x, 우측 상단 y] 좌표 형태입니다
 * - rectangle은 1~4개의 직사각형, 각 좌표는 1~50 자연수
 * - 서로 다른 직사각형은 변이 겹치거나 완전히 포함되지 않음
 * - 캐릭터/아이템 위치는 항상 다각형 테두리 위의 점이며 서로 다름
 */
class Solution {

    private static int[] dy = {-1, 1, 0, 0};
    private static int[] dx = {0, 0, -1, 1};

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {

        int[][] map = new int[102][102];
        boolean[][] visited = new boolean[102][102];

        for (int[] ints : rectangle) {
            // 좌표 2배
            int lx = ints[0] * 2;
            int ly = ints[1] * 2;
            int rx = ints[2] * 2;
            int ry = ints[3] * 2;

            // 직사각형의 범위
            for (int i = lx; i <= rx; i++) {
                for (int j = ly; j <= ry; j++) {
                    // 이미 내부 좌표인 경우
                    if (map[j][i] == 2) continue;

                    // 외부 테두리인 경우
                    if (j == ly | j == ry | i == lx | i == rx) map[j][i] = 1;
                        // 내부인 경우
                    else map[j][i] = 2;
                }
            }
        }

        return bfs(map, visited, characterX * 2, characterY * 2, itemX * 2, itemY * 2);
    }

    private int bfs(int[][] map, boolean[][] visited, int characterX, int characterY, int itemX, int itemY) {
        int count = 0;
        Queue<int[]> queue = new ArrayDeque<int[]>();
        queue.offer(new int[]{characterX, characterY});
        visited[characterY][characterX] = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int j = 0; j < size; j++) {
                int[] cur = queue.poll();
                int x = cur[0];
                int y = cur[1];
                if ( x == itemX && y == itemY) return count/2;

                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];
                    if (ny < 0 || ny > map.length - 1 || nx < 0 || nx > map[0].length - 1) continue;
                    if (visited[ny][nx]) continue;
                    if (map[ny][nx] == 1) {
                        queue.offer(new int[]{nx, ny});
                        visited[ny][nx] = true;
                    }
                }
            }
            count++;
        }
        return 0;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[][]{{1, 1, 7, 4}, {3, 2, 5, 5}, {4, 3, 6, 9}, {2, 6, 8, 8}}, 1, 3, 7, 8, 17);
        // 입출력 예 #2
        test(sol, new int[][]{{1, 1, 8, 4}, {2, 2, 4, 9}, {3, 6, 9, 8}, {6, 3, 7, 7}}, 9, 7, 6, 1, 11);
        // 입출력 예 #3
        test(sol, new int[][]{{1, 1, 5, 7}}, 1, 1, 4, 7, 9);
        // 입출력 예 #4
        test(sol, new int[][]{{2, 1, 7, 5}, {6, 4, 10, 10}}, 3, 1, 7, 10, 15);
        // 입출력 예 #5
        test(sol, new int[][]{{2, 2, 5, 5}, {1, 3, 6, 4}, {3, 1, 4, 6}}, 1, 4, 6, 3, 10);
    }

    private static void test(Solution sol, int[][] rectangle, int characterX, int characterY,
                             int itemX, int itemY, int expected) {
        int result = sol.solution(rectangle, characterX, characterY, itemX, itemY);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
