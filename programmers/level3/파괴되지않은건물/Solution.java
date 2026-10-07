import java.util.*;

/**
 * 파괴되지 않은 건물
 * https://school.programmers.co.kr/learn/courses/30/lessons/92344
 * 분류: 2022 KAKAO BLIND RECRUITMENT (Lv3)
 * <p>
 * [문제 요약]
 * N x M 맵의 각 칸에 내구도가 있는 건물이 하나씩 있다.
 * skill의 각 행은 직사각형 범위 (r1,c1)~(r2,c2)의 모든 건물 내구도를 degree만큼 깎거나(type 1, 공격) 올린다(type 2, 회복).
 * 모든 스킬을 적용한 뒤 내구도가 1 이상인(파괴되지 않은) 건물 수를 구한다.
 * 이미 0 이하인 건물도 계속 깎이고, 회복으로 1 이상이 되면 다시 살아난다.
 * 정확성·효율성 테스트가 따로 채점된다.
 * <p>
 * [제한사항]
 * - 1 <= N, M <= 1,000
 * - 1 <= board[i][j] <= 1,000
 * - 1 <= skill.length <= 250,000, skill[i] = [type, r1, c1, r2, c2, degree]
 * - type: 1(공격) 또는 2(회복), 1 <= degree <= 500
 * - 0 <= r1 <= r2 < N, 0 <= c1 <= c2 < M
 */
class Solution {

    public int solution(int[][] board, int[][] skills) {
        // 차분 배열을 2차 배열에 적용
        // 가로 압축 , 세로 압축으로 나눠서 고민

        int[][] diff = new int[board.length + 1][board[0].length + 1];

        // 오류
        // diff는 board의 복사본이 아니라 "변화량"을 기록하는 배열
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                diff[i][j] = board[i][j];
//            }
//        }

        /**
         * skill의 각 행은 [type, r1, c1, r2, c2, degree]형태를 가지고 있습니다.
         * type 1 , 공격 스킬
         * type 2 , 회복 스킬
         * r1, c1, r2, c2 범위
         * degree는 공격 혹은 회복 수치
         */

        int type, r1, c1, r2, c2, degree;

        for (int i = 0; i < skills.length; i++) {
            int[] skill = skills[i];
            type = skill[0];
            r1 = skill[1];
            c1 = skill[2];
            r2 = skill[3];
            c2 = skill[4];
            degree = skill[5];

//            // 공격
//            if (type == 1) {
//                diff[r1][c1] -= degree;
//                diff[r1][c2 + 1] += degree;
//                diff[r2 + 1][c1] += degree;
//                diff[r2 + 1][c2 + 1] -= degree;
//            }
//            // 회복
//            else {
//                diff[r1][c1] += degree;
//                diff[r1][c2 + 1] -= degree;
//                diff[r2 + 1][c1] -= degree;
//                diff[r2 + 1][c2 + 1] += degree;
//            }

            int value = type == 1 ? -degree : degree;

            diff[r1][c1] += value;
            diff[r1][c2 + 1] -= value;
            diff[r2 + 1][c1] -= value;
            diff[r2 + 1][c2 + 1] += value;
        }

        // 가로 누적
        for (int r = 0; r < diff.length; r++) {
            for (int c = 1; c < diff[0].length; c++) {
                diff[r][c] += diff[r][c - 1];
            }
        }

        // 세로 누적
        for (int c = 0; c < diff[0].length; c++) {
            for (int r = 1; r < diff.length; r++) {
                diff[r][c] += diff[r - 1][c];
            }
        }

        int answer = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] + diff[i][j] > 0) {
                    answer++;
                }
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol,
                new int[][]{{5, 5, 5, 5, 5}, {5, 5, 5, 5, 5}, {5, 5, 5, 5, 5}, {5, 5, 5, 5, 5}},
                new int[][]{{1, 0, 0, 3, 4, 4}, {1, 2, 0, 2, 3, 2}, {2, 1, 0, 3, 1, 2}, {1, 0, 1, 3, 3, 1}},
                10);
        // 입출력 예 #2
        test(sol,
                new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
                new int[][]{{1, 1, 1, 2, 2, 4}, {1, 0, 0, 1, 1, 2}, {2, 2, 0, 2, 0, 100}},
                6);
    }

    private static void test(Solution sol, int[][] board, int[][] skill, int expected) {
        int[][] boardCopy = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);
        int result = sol.solution(boardCopy, skill);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] board=%dx%d, skill=%d건 | expected=%d, actual=%d%n",
                status, board.length, board[0].length, skill.length, expected, result);
    }
}
