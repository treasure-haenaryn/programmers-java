import java.util.*;

/**
 * 거리두기 확인하기
 * https://school.programmers.co.kr/learn/courses/30/lessons/81302
 * 분류: 2021 카카오 채용연계형 인턴십 (Lv2)
 *
 * [문제 요약]
 * 5x5 대기실 5개가 주어지고, 각 칸은 응시자(P), 빈 테이블(O), 파티션(X) 중 하나다.
 * 두 응시자의 맨해튼 거리가 2 이하이면 거리두기 위반이다.
 * 단, 두 자리 사이가 파티션으로 막혀 있으면 거리가 2 이하여도 허용된다.
 * 대기실마다 모두 지키면 1, 한 명이라도 어기면 0을 담아 반환한다.
 *
 * [제한사항]
 * - places: 대기실 5개, 각 대기실은 길이 5인 문자열 5개 (5x5)
 * - 각 문자는 'P', 'O', 'X' 중 하나
 * - 맨해튼 거리: |r1 - r2| + |c1 - c2|
 */
class Solution {

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    int[] answer = new int[5];

    public int[] solution(String[][] places) {
        // TODO: 여기에 풀이 작성

        // 각 P를 기준으로 상하좌우에 P가 있는지 보고
        // 없고 빈 테이블이다?
        // 또 거기 기준으로 상하좌우에서 P가 없으면 위반되지 않음
        // 이것두 DFS, BFS 둘다 될거같애.


        // 각 맵을 만들어야함.

        Arrays.fill(answer, 1);
        char[][] map = new char[5][5];

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {
                map[j] = places[i][j].toCharArray();
            }

//            for (int j = 0; j < 5; j++) {
//                int idx = i;
//                if(map[i][j] == 'P'){
//                    dfs(map, idx, new int[]{i, j}, new boolean[5][5], 0);
//                }
//            }
            for (int r = 0; r < 5 && answer[i] == 1; r++) {
                for (int c = 0; c < 5 && answer[i] == 1; c++) {
                    if (map[r][c] == 'P') {
                        dfs(map, i, new int[]{r, c}, new boolean[5][5], 0);
                    }
                }
            }

        }
        return answer;
    }


    private void dfs(char[][] map, int idx, int[] start, boolean[][] visited, int depth) {
        if(depth ==2) return;

        int r = start[0];
        int c = start[1];
        visited[r][c] = true;

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if(nr < 0 || nr >= 5 || nc < 0 || nc >= 5) continue;
            if(visited[nr][nc]) continue;

            // 상하좌우에 P나오면
            if(map[nr][nc] == 'P') {
                answer[idx] = 0;
                return;
            }
            if(map[nr][nc] == 'X') continue;

            dfs(map, idx, new int[]{nr, nc}, visited, depth + 1);
        }
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
    }

    private static void test(Solution sol, String[][] places, int[] expected) {
        int[] result = sol.solution(places);
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%s, actual=%s%n", status, Arrays.toString(expected), Arrays.toString(result));
    }
}
