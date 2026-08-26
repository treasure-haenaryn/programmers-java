import java.util.*;

/**
 * 카펫
 * https://school.programmers.co.kr/learn/courses/30/lessons/42842
 * 분류: 완전탐색 (Lv2)
 *
 * [문제 요약]
 * 중앙은 노란색, 테두리 1줄은 갈색으로 칠해진 격자 모양 카펫이 있다.
 * 갈색 격자 수(brown)와 노란색 격자 수(yellow)가 주어질 때
 * 카펫의 가로, 세로 크기를 순서대로 배열에 담아 반환한다.
 *
 * [제한사항]
 * - brown: 8 이상 5,000 이하인 자연수
 * - yellow: 1 이상 2,000,000 이하인 자연수
 * - 카펫의 가로 길이는 세로 길이와 같거나 더 길다
 */
class Solution {

    public int[] solution(int brown, int yellow) {
        int target = brown + yellow;

        for (int height = 3; height * height <= target; height++) {
            if (target % height == 0) {
                int width = target / height;

                if ((width - 2) * (height - 2) == yellow) {
                    return new int[]{width, height};
                }
            }
        }

        return new int[]{0, 0};
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 10, 2, new int[]{4, 3});
        // 입출력 예 #2
        test(sol, 8, 1, new int[]{3, 3});
        // 입출력 예 #3
        test(sol, 24, 24, new int[]{8, 6});
    }

    private static void test(Solution sol, int brown, int yellow, int[] expected) {
        int[] result = sol.solution(brown, yellow);
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%s, actual=%s%n", status, Arrays.toString(expected), Arrays.toString(result));
    }
}
