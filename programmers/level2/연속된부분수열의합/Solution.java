import java.util.*;

/**
 * 연속된 부분 수열의 합
 * https://school.programmers.co.kr/learn/courses/30/lessons/178870
 * 분류: 연습문제 (Lv2)
 *
 * [문제 요약]
 * 오름차순(같은 값 허용)으로 정렬된 수열에서, 연속된 구간 중 원소 합이 정확히 k인 구간을 찾는다.
 * 그런 구간이 여럿이면 길이가 가장 짧은 구간을, 길이까지 같으면 시작 위치가 가장 앞선 구간을 고른다.
 * 고른 구간의 [시작 인덱스, 끝 인덱스]를 반환한다 (인덱스는 0부터).
 *
 * [제한사항]
 * - 5 <= sequence.length <= 1,000,000
 * - 1 <= sequence의 원소 <= 1,000
 * - sequence는 비내림차순 정렬
 * - 5 <= k <= 1,000,000,000
 * - 합이 k인 구간은 항상 존재함
 */


/*

비내림차순으로 정렬된 수열이 주어질 때, 다음 조건을 만족하는 부분 수열을 찾으려고 합니다.

기존 수열에서 임의의 두 인덱스의 원소와 그 사이의 원소를 모두 포함하는 부분 수열이어야 합니다.
부분 수열의 합은 k입니다.
합이 k인 부분 수열이 여러 개인 경우 길이가 짧은 수열을 찾습니다.
길이가 짧은 수열이 여러 개인 경우 앞쪽(시작 인덱스가 작은)에 나오는 수열을 찾습니다.
수열을 나타내는 정수 배열 sequence와 부분 수열의 합을 나타내는 정수 k가 매개변수로 주어질 때,
위 조건을 만족하는 부분 수열의 시작 인덱스와 마지막 인덱스를 배열에 담아 return 하는 solution 함수를 완성해주세요. 이때 수열의 인덱스는 0부터 시작합니다.

 */
class Solution {


    public int[] solution(int[] sequence, int k) {

        // 구간을 고르라는 것은 결국 연속된 구간이며 이는 BFS나 조합이 아닌 투포인터로 풀어야함.
        // 투포인터는 하나씩 인덱스를 증가하면서 조건보다 크면 왼쪽의 인덱스를 제외하면서 조간을 맞추는 알고리즘.
        // 전체 루프 돌면서
        // 구간의 누적합을 만들고
        // 값이 k보다 크면 왼쪽 뺴고 누적합도 빼기
        // 왼쪽 인덱스 저장
        // 누적합 저장
        // 투 포인터로 전체 쭉 진행 해야 최소 구간 알수 있음

        // 반례
        // 1. 만약 K가 되는 값이 없으면?
/*
“무엇을 찾아야 하지?”
→ 합이 k인 연속 구간을 찾아야 해.
→ 여러 개면 가장 짧은 구간, 길이도 같으면 가장 앞쪽 구간이 정답이야.

“시작점과 끝점을 전부 골라보면 될까?”
→ 모든 구간을 확인하면 O(n²)이야.
→ 원소가 최대 100만 개라서, 구간을 하나씩 전부 확인하는 방법은 어려워.

“구간을 움직이며 이전 계산을 재사용할 수 있을까?”
→ 오른쪽 원소를 포함하면 그 값을 더하고, 왼쪽 원소를 제외하면 그 값을 빼면 돼.
→ left, right, 현재 구간의 합 sum을 관리하자.

“합에 따라 포인터의 이동 방향을 결정할 수 있을까?”
→ 모든 원소가 양수라서 오른쪽을 늘리면 합이 커지고, 왼쪽을 줄이면 합이 작아져.
→ 따라서 합이 작으면 오른쪽을 늘리고, 크면 왼쪽을 줄이는 투 포인터를 사용할 수 있어.

“오른쪽 원소를 더했는데 합이 k보다 크면?”
→ 왼쪽 원소를 빼고 left를 증가시키자.
→ 한 번 빼도 클 수 있으니 sum > k인 동안 반복해야 해.

“왼쪽 원소를 제외해도 정답을 놓치지 않을까?”
→ 현재 합이 이미 k보다 크면, 같은 시작점에서 오른쪽을 더 늘려도 합은 더 커져.
→ 그 시작점으로는 앞으로 정답을 만들 수 없으니 제외해도 돼.

“줄인 뒤에는 무엇을 확인하지?”
→ 합이 k보다 작으면 다음 오른쪽 원소를 더하자.
→ 합이 정확히 k라면 정답 후보야. 길이 right - left + 1을 기존 정답과 비교하자.

“후보를 찾았으면 바로 반환할까?”
→ 뒤에서 더 짧은 구간이 나올 수 있으니 탐색을 계속하자.
→ 기존 정답보다 짧을 때만 갱신하면, 같은 길이에서는 먼저 찾은 앞쪽 구간이 유지돼.

“반복문 안에 while이 있는데 느리지 않을까?”
→ right도 left도 뒤로 돌아가지 않고 각각 최대 n번 이동해.
→ 전체 시간 복잡도는 O(n)이야.
*/


        int len = sequence.length;
        int sum = 0;
        int left = 0;
        int minLength = Integer.MAX_VALUE;
        int[] answer = new int[2];
        for (int i = 0; i < len; i++) {
            sum += sequence[i];

            while (sum > k) {
                sum -= sequence[left];
                left++;
            }

            int span = i - left;
            if (sum == k && minLength > span) {
                minLength = span;
                answer = new int[]{left, i};
            }
        }


        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[]{1, 2, 3, 4, 5}, 7, new int[]{2, 3});
        // 입출력 예 #2
        test(sol, new int[]{1, 1, 1, 2, 3, 4, 5}, 5, new int[]{6, 6});
        // 입출력 예 #3
        test(sol, new int[]{2, 2, 2, 2, 2}, 6, new int[]{0, 2});
    }

    private static void test(Solution sol, int[] sequence, int k, int[] expected) {
        int[] result = sol.solution(sequence.clone(), k);
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] sequence=%s, k=%d | expected=%s, actual=%s%n",
                status, Arrays.toString(sequence), k, Arrays.toString(expected), Arrays.toString(result));
    }
}
