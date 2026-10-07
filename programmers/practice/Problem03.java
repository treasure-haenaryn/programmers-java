/**
 * ### 문제 3
 * <p>
 * **오름차순으로 정렬된 배열**이 주어집니다.
 * <p>
 * ```
 * arr = [1, 2, 4, 5, 7, 9, 11]
 * target = 13
 * ```
 * <p>
 * 서로 다른 두 숫자를 골라 합이 `target`이 되는지 확인하세요.
 * <p>
 * 예를 들어:
 * <p>
 * ```
 * 2 + 11 = 13
 * ```
 * <p>
 * 배열의 길이는 최대 `1,000,000`입니다.
 */
class Problem03 {
    /*
        1. 무엇을 구하는가?
        → 서로 다른 두 수의 합이 target인 경우가 존재하는지 확인

        2. 가장 단순하게?
        → 모든 두 수의 조합을 확인

        3. 시간복잡도?
        → O(N²)

        4. N=1,000,000이면?
        → 불가능

        5. 개선할 단서는?
        → 배열이 오름차순으로 정렬되어 있음
        → 모든 조합을 확인할 필요가 없음

        6. 어떻게 개선?
        → 양 끝에 left/right를 놓는다.

        sum == target → true
        sum < target  → left++
        sum > target  → right--

        7. 시간복잡도?
        → 각 포인터가 한 방향으로 최대 N번 이동
        → O(N)
     */

    public boolean solution(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        long sum = 0;

        while(left < right) {
            sum = (long) arr[left] + arr[right];
            if(sum == target) return true;
            else if(sum < target) left++;
            else right--;
        }
        return false;
    }
}