import java.util.*;

/**
 * 숫자 게임
 * https://school.programmers.co.kr/learn/courses/30/lessons/12987
 * 분류: Summer/Winter Coding(~2018) (Lv3)
 * <p>
 * [문제 요약]
 * A팀과 B팀이 N명씩 1:1로 한 번씩 맞붙고, 숫자가 큰 쪽 팀이 1점을 얻는다 (같으면 무승부).
 * A팀의 출전 순서(A 배열)는 이미 공개되어 있고, B팀은 이를 보고 자기 팀 출전 순서를 자유롭게 정할 수 있다.
 * B팀이 얻을 수 있는 최대 승점을 구한다.
 * <p>
 * [제한사항]
 * - A.length == B.length
 * - 1 <= A.length <= 100,000
 * - 1 <= A[i], B[i] <= 1,000,000,000
 */
class Solution {

    public int solution(int[] A, int[] B) {
        /*
            무승부 승점 0점
            B가 가질수 있는 최대 승점
            최소한의 크기로 B가 이겨야 최대한 많은 승리를 가져올 수 있음. 왜? 범위가 클수록 그 사이에 커버할 있는 수가 있는데 기회를 날리는것.

            B를 Map<Integer, Integer>로 만들어서 숫자별 갯수를 가지고 A와 비교..
            근데 반드시 이게 크기가1 차이나는게 아닐텐데..
            가장 차이가 적은 수를 구해야해나?

            매번 하나의 수로.. 체크하기엔 효율이 안나는데

            근데 A가 문제에선 고정이지만 순서는 상관없는거 아닌가? 어짜피 순서가 중요한건 아니니.
            그렇게 생각하니 결국 A의 작은 수를 하나씩 격파 해나가면 되겠네
            A와 B의 각  Map을 만들어서 A의 특정 값만큼 Bdml 값을 계속 감소

            그러다 B의 키가 없어지는 순산 로프 종료
            그럼 1인 값은 B에 저장할 필요가 없음ㄹ
            반대로 A의 키보다 작은 B가 있으면 다 삭제 를 해야하는데..

            aMap,bMap 둘다 키에 따라 정렬되어 있어야할거 같애.

            근데 또 Map으로 접근하니 Map은 키를 가지고 찾는 알고리즘에 적합한데 이건 키의범위가 필요한거 같아서 또 안맞는것 같기도해.
            배열 길이가 10만개인데. 조건에 따라 완전 2중 포문은 아니지만. 그래도 효율이 안좋은거 같애,.
         */

        // 굳이 Map이 아닌 정렬된 배열로 포인터로 옮기면서 처리하면 된다는건가?

        // A,B의 정렬
//        Arrays.sort(A);
//        Arrays.sort(B);
//
//        // B의 인덱스
//        int i = 0;
//        int wins = 0;
//        // 포문 하나에서 각각 체크
//        for (int j = 0; j < B.length && i < A.length; j++) {
//            if (B[j] > A[i]) {
//                wins++;
//                i++;
//            }
//        }


        TreeMap<Integer, Integer> bMap = new TreeMap<>();
        int wins = 0;
        for (int b : B) {
            bMap.merge(b, 1, Integer::sum);
        }

        for (int j = 0; j < A.length; j++) {
            Integer higherKey = bMap.higherKey(A[j]);

            if (higherKey == null) continue;

            wins++;

            int remain = bMap.get(higherKey) - 1;

            if (remain == 0) {
                bMap.remove(higherKey);
            } else {
                bMap.put(higherKey, remain);
            }
        }

        return wins;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new int[]{5, 1, 3, 7}, new int[]{2, 2, 6, 8}, 3);
        // 입출력 예 #2
        test(sol, new int[]{2, 2, 2, 2}, new int[]{1, 1, 1, 1}, 0);
    }

    private static void test(Solution sol, int[] A, int[] B, int expected) {
        int result = sol.solution(A.clone(), B.clone());
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] A=%s, B=%s | expected=%d, actual=%d%n",
                status, Arrays.toString(A), Arrays.toString(B), expected, result);
    }
}
