import java.util.*;

/**
 * 리코쳇 로봇
 * https://school.programmers.co.kr/learn/courses/30/lessons/169199
 * 분류: 연습문제 (Lv2)
 * <p>
 * [문제 요약]
 * 격자 보드에서 로봇(R)이 상하좌우 중 한 방향으로 움직이면, 장애물(D)이나 보드 끝에 부딪힐 때까지 미끄러진다.
 * 이렇게 미끄러져 멈추는 것을 이동 1회로 센다.
 * 목표 지점(G)에 정확히 멈추기 위한 최소 이동 횟수를 구하고, 도달할 수 없으면 -1을 반환한다.
 * <p>
 * [제한사항]
 * - 3 ≤ board 길이(행) ≤ 100, 3 ≤ 각 문자열 길이(열) ≤ 100, 모든 행 길이 동일
 * - 문자: '.'(빈칸), 'D'(장애물), 'R'(시작), 'G'(목표)
 * - R과 G는 각각 한 번씩만 등장
 */
class Solution {

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    int N, M;
    boolean[][] visited;
    char[][] map;
    Node start, end;


    // BFS에서 카운트는 따로 가지고 다녀야함..
    static class Node {
        int r, c;
        int count;

        Node(int r, int c, int count) {
            this.r = r;
            this.c = c;
            this.count = count;
        }
    }

    public int solution(String[] board) {
        N = board.length;
        M = board[0].length();
        visited = new boolean[N][M];
        map = new char[N][M];

        for (int i = 0; i < N; i++) {
            map[i] = board[i].toCharArray();
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 'R') {
                    start = new Node(i, j, 0);
                } else if (map[i][j] == 'G') {
                    end = new Node(i, j, 0);
                }
            }
        }

        return bfs(start, end);
    }

    private int bfs(Node start, Node end) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(start);
        visited[start.r][start.c] = true;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int r = cur.r;
            int c = cur.c;
            int count = cur.count;
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                boolean isWall = false;
                // 각 방향 별로 끝까지 혹은 방문 안한 혹은 벽까지
                // 허.. 미끄럼 도중 방문 여부는 빼는게 맞겠네
//                while(nr >= 0 && nr < N && nc >= 0 && nc < M && !visited[nr][nc] && map[nr][nc] != 'D') {
                while (true) {
                    if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                        if (nr < 0) {
                            nr = 0;
                        }
                        if (nr >= N) {
                            nr = N - 1;
                        }
                        if (nc < 0) {
                            nc = 0;
                        }
                        if (nc >= M) {
                            nc = M - 1;
                        }
                        break;
                    }
                    else if (map[nr][nc] == 'D') {
                        nr -= dr[i];
                        nc -= dc[i];
                        break;
                    }
                    nr += dr[i];
                    nc += dc[i];
                    // 이것도 미끄럼 도중 방문 여부는 도착이아님..
//                    if(nr == end[0] && nc == end[1]) return c
                }

                // 단 인덱스 범위를 벗어 나면 안돼고 각 끝 지점에 해당하는 지점 방문 처리
                // 둘다 동시에 벗어날수도 있구나..
                if (nr == end.r && nc == end.c) return count + 1;
                // 방문칸 추가..
                if (visited[nr][nc]) continue;
                visited[nr][nc] = true;
                queue.offer(new Node(nr, nc, count + 1));
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[]{"...D..R", ".D.G...", "....D.D", "D....D.", "..D...."}, 7);
        // 입출력 예 #2
        test(sol, new String[]{".D.R", "....", ".G..", "...D"}, -1);
    }

    private static void test(Solution sol, String[] board, int expected) {
        int result = sol.solution(board);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
