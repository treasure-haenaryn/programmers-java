
/**
 * ### 문제 5 — 조금 헷갈리게
 *
 * 정수 배열이 주어집니다.
 *
 * ```
 * arr = [5, 1, 3, 2, 6, 4]
 * ```
 *
 * 다음과 같은 질문이 `100,000개` 들어옵니다.
 *
 * ```
 * "index 2부터 index 4까지의 합은?"
 * "index 0부터 index 3까지의 합은?"
 * "index 1부터 index 5까지의 합은?"
 * ...
 * ```
 *
 * 각 질문의 구간 길이는 전부 다를 수 있습니다.
 *
 * ---
 *
 * 각 문제마다 **딱 두 가지만** 답해보세요.
 *
 * ```
 * 1번: ??? / 이유: ???
 * 2번: ??? / 이유: ???
 * 3번: ??? / 이유: ???
 * 4번: ??? / 이유: ???
 * 5번: ??? / 이유: ???
 * ```
 *
 * 선택지는:
 *
 * ```
 * A. 누적합
 * B. 슬라이딩 윈도우
 * C. 투 포인터
 */
public class Problem05 {


    /**
     * 1. 무엇을 구해야 하는가?
     * → 여러 개의 [L, R] 구간에 대한 합
     *
     * 2. 가장 단순하게 구현한다면?
     * → 각 query마다 L부터 R까지 반복문을 돌면서 합을 구한다.
     *
     * 3. 시간복잡도는?
     * → query 하나당 최악 O(N)
     * → query가 Q개
     * → O(N * Q)
     *
     * 4. N, Q가 각각 최대 100,000이면?
     * → 최악 약 100억 번
     * → 불가능
     *
     * 5. 반복해서 계산되는 부분은?
     * → query마다 같은 원소들의 합을 계속 다시 계산한다.
     *
     * 예)
     * [0, 4]의 합을 구할 때 arr[0]~arr[4] 계산
     * [1, 3]의 합을 구할 때 arr[1]~arr[3] 또 계산
     * [2, 4]의 합을 구할 때 arr[2]~arr[4] 또 계산
     *
     * 6. 미리 계산해 둘 수 있는 것은?
     * → 처음부터 각 위치까지의 누적합
     *
     * prefix[i] = 앞에서부터 i개의 원소의 합
     *
     * 7. [L, R]의 합은?
     * → prefix[R + 1] - prefix[L]
     *
     * 8. 시간복잡도는?
     * → 누적합 생성 O(N)
     * → query 하나 O(1)
     * → Q개 query O(Q)
     * → 전체 O(N + Q)
     *
     * 9. 공간복잡도는?
     * → prefix 배열 O(N)
     * → answer까지 포함하면 O(N + Q)
     */
    public long[] solution(int[] arr, int[][] queries) {

        // 인덱스 0 부터 3까지 합 : 0_based
        long[] prefix = new long[arr.length + 1];
        long[] answer = new long[queries.length];

        for (int i = 0; i < arr.length; i++) {
            prefix[i + 1] = prefix[i] + arr[i];
        }

        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            answer[i] = prefix[query[1]+1] - prefix[query[0]];
        }

        return answer;
    }
}