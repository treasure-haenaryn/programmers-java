import java.util.*;

/**
 * 폰켓몬
 * https://school.programmers.co.kr/learn/courses/30/lessons/1845
 * 분류: 해시 (Lv1)
 *
 * [문제 요약]
 * N마리의 폰켓몬 중 절반(N/2마리)을 골라 도감에 등록한다.
 * 같은 번호는 같은 종류이며, 고를 수 있는 서로 다른 종류 수의 최댓값을 구한다.
 *
 * [제한사항]
 * - nums 길이: 1 이상 10,000 이하의 짝수
 * - 폰켓몬 번호(종류): 1 이상 200,000 이하의 자연수
 */
class Solution {

    public int solution(int[] nums) {
        return (int) Math.min(Arrays.stream(nums).distinct().count(), nums.length / 2);
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[]{3, 1, 2, 3}, 2);
        // 입출력 예 #2
        test(sol, new int[]{3, 3, 3, 2, 2, 4}, 3);
        // 입출력 예 #3
        test(sol, new int[]{3, 3, 3, 2, 2, 2}, 2);
    }

    private static void test(Solution sol, int[] nums, int expected) {
        int result = sol.solution(nums);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
