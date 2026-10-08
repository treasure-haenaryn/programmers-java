import java.util.*;

/**
 * 무인도 여행
 * https://school.programmers.co.kr/learn/courses/30/lessons/154540
 * 분류: 연습문제 (Lv2)
 *
 * [문제 요약]
 * 격자 지도의 각 칸은 'X'(바다) 또는 1~9 숫자(땅, 식량)이다.
 * 상하좌우로 이어진 땅 칸들이 하나의 섬이 되고, 섬의 칸 숫자를 모두 더한 값이 그 섬에서 머물 수 있는 날 수다.
 * 모든 섬의 날 수를 오름차순으로 정렬한 배열을 반환한다. 섬이 하나도 없으면 [-1]을 반환한다.
 *
 * [제한사항]
 * - 3 <= maps.length <= 100
 * - 3 <= maps[i].length() <= 100, 지도는 직사각형
 * - maps[i]는 'X' 또는 '1'~'9'로만 이루어진 문자열
 */
class Solution {

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    boolean[][] visited;
    char[][] map;
    int n, m;

    public int[] solution(String[] maps) {
        n = maps.length;
        m = maps[0].length();
        visited = new boolean[n][m];
        map = new char[n][m];
        ArrayList<Integer> list = new ArrayList<>();

        // [수정] 한 글자씩 복사할 필요 없이 행 단위로 바로 대입 (toCharArray()가 새 char[]를 만들어 줌)
        for (int i = 0; i < n; i++) {
//            char[] chars = maps[i].toCharArray();
//            for (int j = 0; j < m; j++) {
//                map[i][j] = chars[j];
//            }
            map[i] = maps[i].toCharArray();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(!visited[i][j] && map[i][j] != 'X') {
                    int size = dfs(new int[]{i, j});
                    list.add(size);
                }
            }
        }

        // [수정] size() == 0 → isEmpty() (의도가 더 잘 드러남)
        if (list.isEmpty()) {
            return new int[]{-1};
        }

        // 모든 섬을 찾고 오름 차순으로 정렬
        // 섬을 찾을때는 DFS를 이용
//        list.sort(Comparator.naturalOrder());
        return list.stream().mapToInt(Integer::intValue).sorted().toArray();
    }

    /*
    private int dfs(int[] node, int sum) {
        int r = node[0];
        int c = node[1];
        // 방문 처리
        visited[r][c] = true;
        // 합
        sum += map[r][c] - '0';

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            // 범위 조건
            if(nr < 0 || nr >= n || nc < 0 | nc >= m) continue;
            // 'X'는 진입 불가
            if(map[nr][nc] == 'X') continue;
            // 방문 여부
            if(visited[nr][nc]) continue;
            dfs(new int[]{nr, nc}, sum);
        }

        return sum;
    }
    */

    private int dfs(int[] node) {
        int r = node[0];
        int c = node[1];
        // 방문 처리
        visited[r][c] = true;
        // 합
        int total = map[r][c] - '0'; // 자기 자신 값

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            // 범위 조건
            if(nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
            // 'X'는 진입 불가
            if(map[nr][nc] == 'X') continue;
            // 방문 여부
            if(visited[nr][nc]) continue;
            total += dfs(new int[]{nr, nc}); // 자식의 모아온 합을 더 함
        }

        return total; // 내꺼  + 자식 전체
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
