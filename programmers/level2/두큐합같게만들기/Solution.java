import java.util.*;

/**
 * 두 큐 합 같게 만들기
 * https://school.programmers.co.kr/learn/courses/30/lessons/118667
 * 분류: 2022 KAKAO TECH INTERNSHIP (Lv2)
 *
 * [문제 요약]
 * 길이가 같은 두 큐가 있다. 한쪽 큐의 맨 앞 원소를 꺼내 다른 큐의 맨 뒤에 넣는 것을 작업 1회로 친다.
 * 두 큐의 원소 합이 같아지도록 만드는 데 필요한 최소 작업 횟수를 구한다.
 * 어떤 방법으로도 합을 같게 만들 수 없다면 -1을 반환한다.
 *
 * [제한사항]
 * - 1 ≤ queue1 길이 = queue2 길이 ≤ 300,000
 * - 1 ≤ 각 원소 ≤ 10^9
 * - 합 계산 시 오버플로우 주의 (long 사용 고려)
 */
class Solution {

    public int solution(int[] queue1, int[] queue2) {

        /**
         * 무엇을 구헤야하는지?
         * 두큐의 총 합이 같아지는 최소 작업 횟수.
         *
         * 각 큐의 합을 구하고 그 합의 합을 반으로 나누면 구해야될 큐의 합.(K)
         *
         *  A큐를 기준으로 K보다 작으면 Insert, B큐는 POP
         *  A큐를 기준으로 K보다 크면 POP, B큐는 Insert
         *
         *  같메 만들수 없는 경우 -1... 어떻게 이걸알지?
         *
         */


        // 10억
        // TODO: 여기에 풀이 작성

        long sum1 = 0;
        long sum2 = 0;
        ArrayDeque<Integer> q1 = new ArrayDeque<>();
        ArrayDeque<Integer> q2 = new ArrayDeque<>();

        for (int num : queue1) {
            sum1 += num;
            q1.offer(num);
        }

        for (int num : queue2) {
            sum2 += num;
            q2.offer(num);
        }

        long total = sum1 + sum2;
        if(total % 2 != 0) {
           return -1;
        }
        long k = total / 2;

        int count = 0;
        int limit = queue1.length* 4;

        while (sum1 != k) {
            if (count > limit) return -1;
            if(sum1 > k) {
                int num = q1.poll();
                q2.offer(num);
                sum1 -= num;
                sum2 += num;
            }else{
                int num = q2.poll();
                q1.offer(num);
                sum2 -= num;
                sum1 += num;
            }
            count++;
        }

        return count;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[]{3, 2, 7, 2}, new int[]{4, 6, 5, 1}, 2);
        // 입출력 예 #2
        test(sol, new int[]{1, 2, 1, 2}, new int[]{1, 10, 1, 2}, 7);
        // 입출력 예 #3
        test(sol, new int[]{1, 1}, new int[]{1, 5}, -1);
    }

    private static void test(Solution sol, int[] queue1, int[] queue2, int expected) {
        int result = sol.solution(queue1, queue2);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
