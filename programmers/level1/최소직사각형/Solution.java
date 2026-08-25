import java.util.*;

/**
 * 최소직사각형
 * https://school.programmers.co.kr/learn/courses/30/lessons/86491
 * 분류: 완전탐색 (Lv1)
 *
 * [문제 요약]
 * 여러 명함의 가로/세로 길이가 주어진다.
 * 각 명함은 회전(가로/세로를 바꿔치기)해서 넣을 수 있다.
 * 모든 명함을 겹쳐서 수납할 수 있는 가장 작은 지갑의 넓이를 구한다.
 *
 * [제한사항]
 * - sizes 길이: 1 이상 10,000 이하
 * - sizes의 원소: [w, h] (w, h는 1 이상 1,000 이하의 자연수)
 */
class Solution {

    public int solution(int[][] sizes) {
        int maxW = Integer.MIN_VALUE;
        int maxH = Integer.MIN_VALUE;

        for(int[] size : sizes) {
            int nW = Math.min(size[0], size[1]);
            int nH = Math.max(size[0], size[1]);

            maxW = Math.max(nW, maxW);
            maxH = Math.max(nH, maxH);
        }

        return maxW * maxH;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[][]{{60, 50}, {30, 70}, {60, 30}, {80, 40}}, 4000);
        // 입출력 예 #2
        test(sol, new int[][]{{10, 7}, {12, 3}, {8, 15}, {14, 7}, {5, 15}}, 120);
        // 입출력 예 #3
        test(sol, new int[][]{{14, 4}, {19, 6}, {6, 16}, {18, 7}, {7, 11}}, 133);
    }

    private static void test(Solution sol, int[][] sizes, int expected) {
        int result = sol.solution(sizes);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
