import java.util.*;

/**
 * 퍼즐 조각 채우기
 * https://school.programmers.co.kr/learn/courses/30/lessons/84021
 * 분류: BFS/DFS (Lv3)
 * <p>
 * [문제 요약]
 * 정사각형 게임 보드(0=빈칸, 1=채워진칸)와 퍼즐 조각이 그려진 테이블(0=빈칸, 1=조각)이 주어진다.
 * 테이블에서 조각들을 하나씩 꺼내 게임 보드의 빈 공간에 회전만 시켜서(뒤집기 불가) 끼워 넣는다.
 * 조각을 끼울 때 그 조각과 인접한 칸에 빈칸이 남으면 안 된다는 규칙을 지키면서
 * 채울 수 있는 조각들로 게임 보드를 최대한 채웠을 때, 채워진 총 칸의 수를 구한다.
 * <p>
 * [제한사항]
 * - 게임 보드/테이블 모두 3 이상 50 이하의 정사각 2차원 배열
 * - 각 원소는 0 또는 1
 * - 퍼즐 조각은 1~6개의 칸이 인접(상하좌우)하게 연결된 형태
 * - 게임 보드에는 빈칸이 1개 이상, 테이블에는 조각 칸이 1개 이상 존재
 */
class Solution {

    private static final int[] dr = {1, -1, 0, 0};
    private static final int[] dc = {0, 0, 1, -1};

    private static class Point implements Comparable<Point> {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            if (this.x == o.x) return Integer.compare(this.y, o.y);
            return Integer.compare(this.x, o.x);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point p = (Point) o;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public int solution(int[][] game_board, int[][] table) {
        int answer = 0;
        boolean[][] boardVisited = new boolean[game_board.length][game_board[0].length];
        boolean[][] tableVisited = new boolean[table.length][table[0].length];
        List<List<Point>> boardSpaces = new ArrayList<>();
        List<List<Point>> tablePiecese = new ArrayList<>();

        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[0].length; j++) {
                if (!boardVisited[i][j] && game_board[i][j] == 0)
                    bfs(game_board, boardVisited, i, j, boardSpaces, 0);
            }
        }

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (!tableVisited[i][j] && table[i][j] == 1)
                    bfs(table, tableVisited, i, j, tablePiecese, 1);
            }
        }


        boolean[] usedPuzzle = new boolean[tablePiecese.size()];

        // 4단계: 빈칸 덩어리마다 맞는 퍼즐 조각 대어보기
        for (List<Point> space : boardSpaces) {
            for (int i = 0; i < tablePiecese.size(); i++) {
                if (usedPuzzle[i]) continue; // 이미 사용된 조각 건너뜀

                List<Point> puzzle = tablePiecese.get(i);

                // 크기(칸 수)가 다르면 애초에 맞지 않음
                if (space.size() != puzzle.size()) continue;

                // 4회 회전해 보며 일치 여부 확인
                if (isMatch(space, puzzle)) {
                    usedPuzzle[i] = true; // 사용 처리
                    answer += space.size(); // 채운 칸 수 추가
                    break; // 다음 빈칸으로
                }
            }
        }

        return answer;
    }

    private void bfs(int[][] map, boolean[][] visited, int x, int y, List<List<Point>> pieces, int target) {
        List<Point> piece = new ArrayList<>();
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{x, y});
        visited[x][y] = true;
        piece.add(new Point(x, y));


        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curX = cur[0];
            int curY = cur[1];

            for (int i = 0; i < 4; i++) {
                int newX = curX + dr[i];
                int newY = curY + dc[i];

                if(newX < 0 || newX > map.length -1 || newY < 0 || newY > map[0].length -1) continue;
                if(visited[newX][newY]) continue;
                if(map[newX][newY] != target) continue;
                queue.offer(new int[]{newX, newY});
                visited[newX][newY] = true;
                piece.add(new Point(newX, newY));
            }

        }

        pieces.add(rebase(piece));
    }

    // 2단계: (0,0) 기점으로 좌표 당기기 (정규화)
    private List<Point> rebase(List<Point> block) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        // x, y의 최솟값 찾기
        for (Point p : block) {
            minX = Math.min(minX, p.x);
            minY = Math.min(minY, p.y);
        }

        List<Point> rebased = new ArrayList<>();
        for (Point p : block) {
            rebased.add(new Point(p.x - minX, p.y - minY));
        }

        // 좌표 일치 비교를 위해 항상 정렬
        Collections.sort(rebased);
        return rebased;
    }

    // 3단계: 90도 회전
    private List<Point> rotate(List<Point> block) {
        List<Point> rotated = new ArrayList<>();
        for (Point p : block) {
            // (x, y) -> (y, -x) 로 회전
            rotated.add(new Point(p.y, -p.x));
        }
        // 회전 후 음수 좌표가 생기므로 다시 (0,0) 원점으로 당김
        return rebase(rotated);
    }

    // 매칭 확인 (0도, 90도, 180도, 270도 회전 검사)
    private boolean isMatch(List<Point> space, List<Point> puzzle) {
        List<Point> currentPuzzle = puzzle;

        for (int r = 0; r < 4; r++) {
            if (space.equals(currentPuzzle)) {
                return true;
            }
            currentPuzzle = rotate(currentPuzzle); // 90도 회전
        }
        return false;
    }



    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol,
                new int[][]{
                        {1, 1, 0, 0, 1, 0},
                        {0, 0, 1, 0, 1, 0},
                        {0, 1, 1, 0, 0, 1},
                        {1, 1, 0, 1, 1, 1},
                        {1, 0, 0, 0, 1, 0},
                        {0, 1, 1, 1, 0, 0}
                },
                new int[][]{
                        {1, 0, 0, 1, 1, 0},
                        {1, 0, 1, 0, 1, 0},
                        {0, 1, 1, 0, 1, 1},
                        {0, 0, 1, 0, 0, 0},
                        {1, 1, 0, 1, 1, 0},
                        {0, 1, 0, 0, 0, 0}
                },
                14
        );

        // 입출력 예 #2
        test(sol,
                new int[][]{
                        {0, 0, 0},
                        {1, 1, 0},
                        {1, 1, 1}
                },
                new int[][]{
                        {1, 1, 1},
                        {1, 0, 0},
                        {0, 0, 0}
                },
                0
        );
    }

    private static void test(Solution sol, int[][] game_board, int[][] table, int expected) {
        int result = sol.solution(game_board, table);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}


