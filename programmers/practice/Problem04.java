/**
 * ### 문제 4
 * <p>
 * 모두 **양수**인 배열이 주어집니다.
 * <p>
 * ```
 * arr = [2, 3, 1, 2, 4, 3]
 * target = 7
 * ```
 * <p>
 * 합이 `target 이상`이 되는 **연속된 부분 배열 중 길이가 가장 짧은 것**을 구하세요.
 * <p>
 * 예를 들어:
 * <p>
 * ```
 * [2,3,1,2] → 합 8, 길이 4
 * [3,1,2,4] → 합 10, 길이 4
 * [4,3]     → 합 7, 길이 2
 *
 * 정답 = 2
 * ```
 */
public class Problem04 {

    /**
     * 1. 무엇을 구해야하는가?
     * → 합이 target 이상이 되는 연속된 수 중 가장 짧은 길이
     * <p>
     * 2. 단순하게 구현한다면?
     * → 길이 별로 반목문을 돌려 연속된 수의 합을 구해 가장 짧은 길이를 구한다.
     * <p>
     * 3. 최악의 경우는?
     * <p>
     * <p>
     * 7. 반복해서 계산되는 부분은 무엇인가?
     * → 구간을 한 칸 이동할 때 K-1개의 원소가 겹치는데, 이 원소들의 합을 매번 다시 계산한다.
     * <p>
     * 8, 어떻게 개선할 수 있을까?
     * → 구간의 합을 그대로 사용하여 target 값보다 크면 왼쪽을 제외하고 target 값보다 작으면 오른쪽을 포함한다.  가변 슬라이딩
     * <p>
     * 9. 개선한 방법의 시간 복잡도는?
     * → O(N)
     */

    public int solution(int[] arr, int target) {
        int left = 0;
        int right = 0;
        int sum = 0;
        int answer = Integer.MAX_VALUE;

        for (right = 0; right < arr.length; right++) {
            sum += arr[right];

            while (sum >= target) {
                answer = Math.min(answer, right - left + 1);

                sum -= arr[left];
                left++;
            }
        }
        return answer == Integer.MAX_VALUE ? 0 : answer;
    }

}