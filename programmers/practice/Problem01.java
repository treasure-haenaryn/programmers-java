/**
 * 문제 1
 * 정수 배열이 주어집니다.
 * arr = [2, 4, 1, 5, 3, 7, 2]
 *
 * 그리고 구간 질문이 여러 개 주어집니다.
 * queries = [
 *     [1, 3],
 *     [2, 5],
 *     [0, 6],
 *     [4, 6]
 * ]
 *
 * 각 [L, R]에 대해 L~R 사이의 모든 숫자의 합을 구하세요.
 * 예:
 * [1,3] → 4 + 1 + 5 = 10
 *
 * arr.length와 queries.length는 각각 최대 100,000입니다.
 */
public class Problem01 {

    // [생각의 확장]
    // 이 문제에서 구하는것은 무엇인지? 주어진 구간의 함
    // 가장 쉽게 접근한다면? 각 구간별로 순차적으로 합을 구함.
    // 시간 복잡도 제약에 걸리는가? N(N*Q) 최대 O(N^2)
    // 그럼 반복적인 계산이 보이는지? 누적합을 이용해 R +1 - L
    // O(N)

    public long[] solution(int[] arr, int[][] queries) {
        long[] answer = new long[queries.length];
        long[] prefix = new long[queries.length + 1];

        for (int i = 0; i < arr.length; i++) {
            prefix[i + 1] = prefix[i] + arr[i];
        }

        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            answer[i] = prefix[query[1 + 1]] - prefix[query[0]];
        }

        return answer;
    }
}