import java.util.*;

/**
 * 두 큐 합 같게 만들기 - 투 포인터(하나의 배열) 버전
 * https://school.programmers.co.kr/learn/courses/30/lessons/118667
 *
 * queue1 뒤에 queue2를 이어 붙여 원형 배열로 보고,
 * q1 = [start, end) 구간의 경계만 움직인다. (원소는 실제로 옮기지 않음)
 * - q1에서 pop → start++
 * - q2에서 pop 후 q1에 insert → end++
 */
class SolutionTwoPointer {

    public int solution(int[] queue1, int[] queue2) {
        int n = queue1.length;
        int len = n * 2;

        // [q1 | q2] 를 하나의 배열로
        int[] arr = new int[len];
        long sum1 = 0;
        long total = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = queue1[i];
            arr[i + n] = queue2[i];
            sum1 += queue1[i];
            total += queue1[i] + (long) queue2[i];
        }

        if (total % 2 != 0) return -1;
        long k = total / 2;

        int start = 0;   // q1의 맨 앞
        int end = n;     // q1의 맨 뒤 다음 칸 (= q2의 맨 앞)
        int limit = n * 4;

        for (int count = 0; count <= limit; count++) {
            if (sum1 == k) return count;

            if (sum1 > k) {
                // q1에서 pop → 시작 경계를 한 칸 이동
                sum1 -= arr[start % len];
                start++;
            } else {
                // q2에서 pop → q1에 insert → 끝 경계를 한 칸 이동
                sum1 += arr[end % len];
                end++;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        SolutionTwoPointer sol = new SolutionTwoPointer();

        // 입출력 예 #1
        test(sol, new int[]{3, 2, 7, 2}, new int[]{4, 6, 5, 1}, 2);
        // 입출력 예 #2
        test(sol, new int[]{1, 2, 1, 2}, new int[]{1, 10, 1, 2}, 7);
        // 입출력 예 #3
        test(sol, new int[]{1, 1}, new int[]{1, 5}, -1);
    }

    private static void test(SolutionTwoPointer sol, int[] queue1, int[] queue2, int expected) {
        int result = sol.solution(queue1, queue2);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
