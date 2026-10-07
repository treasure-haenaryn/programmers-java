import java.util.*;

/**
 * 연속 부분 수열 합의 개수
 * https://school.programmers.co.kr/learn/courses/30/lessons/131701
 * 분류: 연습문제 (Lv2)
 *
 * [문제 요약]
 * 자연수로 이루어진 수열의 처음과 끝을 이어 원형 수열로 본다.
 * 이 원형 수열에서 연속하는 부분 수열(길이 1 ~ 전체)의 합을 모두 구했을 때,
 * 서로 다른 합이 몇 가지인지 반환한다.
 *
 * [제한사항]
 * - 3 ≤ elements 길이 ≤ 1,000
 * - 1 ≤ elements 원소 ≤ 1,000
 */
class Solution {

    public int solution(int[] elements) {
        // 원형은 배열을 연달다 붙이고 최대 n-1 까지만 체크하면됨. n은 따로
        Set<Integer> set = new HashSet<>();
        int total = 0;
        int[] circle = new int[elements.length*2];
        for (int i = 0; i < elements.length; i++) {
            circle[i] = elements[i];
            circle[i+elements.length] = elements[i];
            total += elements[i];
        }
        set.add(total);

        // 슬라이딩 길이
        for (int k = 1; k < elements.length; k++) {
            int sum = 0;
            int left = 0;

            // 보완1. right가 2n 끝까지 감
            // for (int right = 0; right < circle.length; right++) {
            // "시작 위치는 0 ~ n-1"을 적용
            for (int right = 0; right < elements.length + k -1; right++) {
                sum += circle[right];

                // 보완2. set.add(sum)의 위치
                // set.add(sum);
                // 지금은 창 크기가 k에 도달하기 전에도 합을 넣고 있어요.
                // 예를 들어 k=3이면 처음에 [7], [7,9]의 합도 들어갑니다. 이 값들도 실제로 있는 연속 부분
                // 수열의 합이라 답은 틀리지 않지만, "길이 k짜리 합만 모은다"는 의도와 다르게 동작해서 읽기 어려워요.
                if(right - left + 1 ==  k) {
                    set.add(sum);
                    sum -= circle[left++];
                }
            }
        }
        return set.size();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[]{7, 9, 1, 1, 4}, 18);
    }

    private static void test(Solution sol, int[] elements, int expected) {
        int result = sol.solution(elements);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
