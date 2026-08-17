import java.util.*;

/**
 * 타겟 넘버
 * https://school.programmers.co.kr/learn/courses/30/lessons/43165
 * 분류: 깊이/너비 우선 탐색(DFS/BFS) (Lv2)
 *
 * [문제 요약]
 * 음이 아닌 정수 배열이 주어지면, 순서를 바꾸지 않고 각 원소 앞에
 * +/- 부호를 붙여 모두 더했을 때 target 값이 되는 경우의 수를 구한다.
 *
 * [제한사항]
 * - numbers의 길이는 2 이상 20 이하
 * - numbers의 각 원소는 1 이상 50 이하의 자연수
 * - target은 1 이상 1000 이하의 자연수
 */
class Solution {

    public int solution(int[] numbers, int target) {
        return dfs(numbers, target, 0, 0);
    }

    private int dfs(int[] numbers, int target, int idx, int sum) {
        if (numbers.length == idx) {
            if( sum == target) return 1;
            return 0;
        }

        return dfs(numbers, target, idx + 1, sum + numbers[idx]) + dfs(numbers, target, idx + 1, sum - numbers[idx]);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[]{1, 1, 1, 1, 1}, 3, 5);
        // 입출력 예 #2
        test(sol, new int[]{4, 1, 2, 1}, 4, 2);
    }

    private static void test(Solution sol, int[] numbers, int target, int expected) {
        int result = sol.solution(numbers, target);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
