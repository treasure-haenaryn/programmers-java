/**
 * 숫자의 표현
 * https://school.programmers.co.kr/learn/courses/30/lessons/12924
 * 분류: 연습문제 (Lv2)
 *
 * [문제 요약]
 * 자연수 n을 1개 이상의 연속된 자연수의 합으로 나타내는 방법이 몇 가지인지 구한다.
 * 예: 15 = 1+2+3+4+5 = 4+5+6 = 7+8 = 15 → 4가지 (n 자기 자신 하나만 쓰는 경우도 포함).
 *
 * [제한사항]
 * - n은 10,000 이하의 자연수
 */

class Solution {

    /*
        무엇을 반환하는지?
        :자연수 n을 1개 이상의 연속된 수의 합으로 나태날수 있는 방법의 가짓수

        알고리즘 접근
        : 연속된 구간으로 접근해야 하기 때문에 투 포은터로 접근

        자연수 n을 루프를 돌면서 접근할 것이기 때문에 자연적으로 오름 차순되어 있음
        : 자연수이기때문에 로프는 1부터

        모든 수가 양수이기 때문에 합을 더하면서 이동 방향을 결정 할 수 있음.

        오른쪽의 합을 계속 더하고 만약 크면 왼쪽 인덱스 제거 하고 값도 뺀다.

        결과적으로 상태 관리가 필요한 값은
        1. 왼쪽 인덱스
        2. 오른쪽 인덱스 : 루프를 통해 자동으로 관리됨.
        3. 합
        4. 전체 갯수
     */

    public int solution(int n) {
        int sum = 0;
        int left = 1;
        int answer = 0;

        for(int right = 1 ; right <= n ; right++){
            sum += right;

            while (sum > n){
                sum -= left;
                left++;
            }

            if(sum == n){
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 15, 4);
    }

    private static void test(Solution sol, int n, int expected) {
        int result = sol.solution(n);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] n=%d | expected=%d, actual=%d%n", status, n, expected, result);
    }
}
