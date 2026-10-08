import java.util.*;

/**
 * 거리두기 확인하기 - BFS(거리 제한) 풀이
 * https://school.programmers.co.kr/learn/courses/30/lessons/81302
 * 분류: 2021 카카오 채용연계형 인턴십 (Lv2)
 *
 * [접근]
 * 각 P에서 BFS로 퍼지되, O만 밟고 거리 2까지만 탐색한다.
 * 그 안에서 다른 P를 만나면 위반.
 * - 거리는 dist 배열로 관리 → depth 매개변수 없이 "몇 걸음째인지" 알 수 있음
 * - X는 큐에 넣지 않음 (파티션은 막힌 칸)
 * - 거리 2인 칸에서는 더 퍼지지 않음 (3 이상은 상관없음)
 *
 * [DFS 풀이와 차이]
 * - BFS는 가까운 칸부터 퍼지므로 "거리 d까지만"이라는 조건을 dist 값 하나로 자연스럽게 표현
 * - 위반을 찾으면 그 자리에서 false 반환 → 바깥 반복문도 바로 종료
 */
class Solution {

    private static final int SIZE = 5;
    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];

        for (int room = 0; room < places.length; room++) {
            answer[room] = isSafe(places[room]) ? 1 : 0;
        }
        return answer;
    }

    // 대기실 하나의 모든 P가 거리두기를 지키는지
    private boolean isSafe(String[] place) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (place[r].charAt(c) == 'P' && !bfs(place, r, c)) {
                    return false;   // 위반 하나라도 있으면 이 대기실은 끝
                }
            }
        }
        return true;
    }

    // (sr, sc)의 P에서 O만 밟고 거리 2 안에 다른 P가 없으면 true
    private boolean bfs(String[] place, int sr, int sc) {
        int[][] dist = new int[SIZE][SIZE];
        for (int[] row : dist) Arrays.fill(row, -1);   // -1 = 아직 방문 안 함

        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sr, sc});
        dist[sr][sc] = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];

            // 거리 2인 칸에서는 더 퍼지지 않음
            if (dist[r][c] == 2) continue;

            for (int d = 0; d < 4; d++) {
                int nr = r + DR[d];
                int nc = c + DC[d];

                if (nr < 0 || nr >= SIZE || nc < 0 || nc >= SIZE) continue;
                if (dist[nr][nc] != -1) continue;          // 출발 P 포함, 이미 본 칸

                char next = place[nr].charAt(nc);
                if (next == 'X') continue;                 // 파티션은 못 지나감
                if (next == 'P') return false;             // 거리 1 또는 2에 다른 P → 위반

                dist[nr][nc] = dist[r][c] + 1;             // O만 큐에 넣음
                queue.offer(new int[]{nr, nc});
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[][]{
                {"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"},
                {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"},
                {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"},
                {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"},
                {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}
        }, new int[]{1, 0, 1, 1, 1});

        // 추가: 위반이 마지막 줄에만 있는 경우 (기존 풀이 리뷰의 반례)
        String[] lastRow = {"OOOOO", "OOOOO", "OOOOO", "OOOOO", "PPOOO"};
        test(sol, new String[][]{lastRow, lastRow, lastRow, lastRow, lastRow}, new int[]{0, 0, 0, 0, 0});

        // 추가: 대각선 - 두 길 모두 X면 OK, 하나라도 O면 위반
        test(sol, new String[][]{
                {"PXOOO", "XPOOO", "OOOOO", "OOOOO", "OOOOO"},
                {"PXOOO", "OPOOO", "OOOOO", "OOOOO", "OOOOO"},
                {"POOOO", "OOOOO", "OOOOO", "OOOOO", "OOOOO"},
                {"POPOO", "OOOOO", "OOOOO", "OOOOO", "OOOOO"},
                {"PXPOO", "OOOOO", "OOOOO", "OOOOO", "OOOOP"}
        }, new int[]{1, 0, 1, 0, 1});
    }

    private static void test(Solution sol, String[][] places, int[] expected) {
        int[] result = sol.solution(places);
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%s, actual=%s%n", status, Arrays.toString(expected), Arrays.toString(result));
    }
}
