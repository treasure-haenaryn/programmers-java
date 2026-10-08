import java.util.*;

/**
 * 소수 만들기
 * https://school.programmers.co.kr/learn/courses/30/lessons/12977
 * 분류: Summer/Winter Coding(~2018) (Lv1)
 *
 * [문제 요약]
 * 숫자 배열에서 서로 다른 3개를 골라 더한다.
 * 그 합이 소수가 되는 3개 조합이 몇 가지인지 반환한다.
 *
 * [제한사항]
 * - 3 ≤ nums 길이 ≤ 50
 * - 1 ≤ nums 원소 ≤ 1,000 (자연수, 중복 없음)
 */
class Solution {

    int[] arr;
    int[] selected;
    int answer;

    public int solution(int[] nums) {
        // TODO: 여기에 풀이 작성
        arr = nums;
        selected = new int[3];
        answer = 0;

        /**
         * 무엇을 구하는지?
         * 숫자 3개의 조합을 이용해서 소수가 나오는 경우의수
         *
         * 소수 판별은 어떻게?
         *
         */
        combination(0, 0, 0);
        return answer;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private void combination(int start, int depth, int sum) {
        if (depth == 3) {
            if(isPrime(sum)) answer++; // 소수 판별로 수정
            return;
        }

        for (int i = start; i < arr.length; i++) {
            selected[depth] = arr[i];
            combination(i + 1, depth + 1, sum + arr[i]);
        }

    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[]{1, 2, 3, 4}, 1);
        // 입출력 예 #2
        test(sol, new int[]{1, 2, 7, 6, 4}, 4);
    }

    private static void test(Solution sol, int[] nums, int expected) {
        int result = sol.solution(nums);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
