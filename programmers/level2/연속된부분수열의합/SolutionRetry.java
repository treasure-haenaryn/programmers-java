import java.util.*;

/**
 * 문제 설명
 * 비내림차순으로 정렬된 수열이 주어질 때, 다음 조건을 만족하는 부분 수열을 찾으려고 합니다.
 * <p>
 * 기존 수열에서 임의의 두 인덱스의 원소와 그 사이의 원소를 모두 포함하는 부분 수열이어야 합니다.
 * 부분 수열의 합은 k입니다.
 * 합이 k인 부분 수열이 여러 개인 경우 길이가 짧은 수열을 찾습니다.
 * 길이가 짧은 수열이 여러 개인 경우 앞쪽(시작 인덱스가 작은)에 나오는 수열을 찾습니다.
 * 수열을 나타내는 정수 배열 sequence와 부분 수열의 합을 나타내는 정수 k가 매개변수로 주어질 때, 위 조건을 만족하는 부분 수열의 시작 인덱스와 마지막 인덱스를 배열에 담아 return 하는 solution 함수를 완성해주세요. 이때 수열의 인덱스는 0부터 시작합니다.
 * <p>
 * 제한사항
 * 5 ≤ sequence의 길이 ≤ 1,000,000
 * 1 ≤ sequence의 원소 ≤ 1,000
 * sequence는 비내림차순으로 정렬되어 있습니다.
 * 5 ≤ k ≤ 1,000,000,000
 * k는 항상 sequence의 부분 수열로 만들 수 있는 값입니다.
 * 입출력 예
 * sequence	k	result
 * [1, 2, 3, 4, 5]	7	[2, 3]
 * [1, 1, 1, 2, 3, 4, 5]	5	[6, 6]
 * [2, 2, 2, 2, 2]	6	[0, 2]
 */
public class SolutionRetry {

    public int[] solution(int[] sequence, int k) {

        // 가변 슬라이딩
        // 시간 복잡도 O(N)
        // 연속 구간이고 모든 원소가 양수라서 right를 늘리면 합이 증가하고 left를 늘리면 합이 감소한다.
        // 따라서 합을 k와 비교하며 구간을 조절하는 가변 슬라이딩 윈도우로 O(N)에 해결했다.

        int left = 0;
        int right = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;
        int[] answer = new int[2];

        for (right = 0; right < sequence.length; right++) {
            sum += sequence[right];

            while (sum > k) {
                sum -= sequence[left];
                left++;
            }

            int newMin = right - left + 1;
            if (sum == k && min > newMin) {
                min = newMin;
                answer = new int[]{left, right};
            }
        }

        return answer;
    }
}