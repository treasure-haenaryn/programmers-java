import java.util.*;

/**
 * 삼총사
 * https://school.programmers.co.kr/learn/courses/30/lessons/131705
 * 분류: 연습문제 (Lv1)
 *
 * [문제 요약]
 * 학생마다 정수 번호가 하나씩 있고, 서로 다른 학생 3명의 번호 합이 0이면 삼총사라고 부른다.
 * 학생 번호 배열이 주어질 때 삼총사가 되는 3명 조합의 개수를 반환한다.
 *
 * [제한사항]
 * - 3 ≤ number 길이 ≤ 13
 * - -1,000 ≤ number 원소 ≤ 1,000
 * - 서로 다른 학생의 번호가 같을 수 있음
 */
class Solution {

    int answer = 0;
    int[] selected;
    int[] arr;



    public int solution(int[] number) {
        // 순서 상관 없이 N 명 중 M명의 조합
        arr = number;
        selected = new int[3];
        answer = 0;
        combination(0, 0);
        return answer;
    }

    void combination(int start, int depth) {
        // 1. 종료 조건: M개를 골랐는가?
        if (depth == 3) {
            if (selected[0] + selected[1] + selected[2] == 0) answer++;
            return;
        }

        // 3. 이번 위치에 넣을 후보를 고른다
        for (int i = start; i < arr.length; i++) {
            selected[depth] = arr[i];

            // 4. 다음에는 현재 것 뒤에서부터 고른다
            combination(i + 1, depth + 1);
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[]{-2, 3, 0, 2, -5}, 2);
        // 입출력 예 #2
        test(sol, new int[]{-3, -2, -1, 0, 1, 2, 3}, 5);
        // 입출력 예 #3
        test(sol, new int[]{-1, 1, -1, 1}, 0);
    }

    private static void test(Solution sol, int[] number, int expected) {
        int result = sol.solution(number);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
