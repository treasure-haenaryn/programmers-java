import java.util.*;

/**
 * 옹알이 (1)
 * https://school.programmers.co.kr/learn/courses/30/lessons/120956
 * 분류: 코딩테스트 입문 (Lv1)
 *
 * [문제 요약]
 * "aya", "ye", "woo", "ma" 네 가지 발음을 각각 최대 한 번씩 조합해 만든 단어인지 판별한다.
 * 문자열 배열이 주어졌을 때, 그중 이 조건을 만족하는(발음 가능한) 단어의 개수를 반환한다.
 *
 * [제한사항]
 * - babbling 배열 길이: 1 이상 100 이하
 * - 각 문자열 길이: 1 이상 15 이하
 * - 각 발음("aya", "ye", "woo", "ma")은 하나의 문자열에서 최대 1회만 등장
 * - 소문자 알파벳으로만 구성
 */
class Solution {

    // 할 수 있는 발음
    private String[] enable = {"aya", "ye", "woo", "ma"};
    private boolean[] visited = new boolean[4];
    // "aya", "ye", "woo", "ma" 을 이용해 만들 수 있는 문자열
    Set<String> set = new HashSet<>();

    public int solution(String[] babblings) {
        int answer = 0;
        combination("");
        for (String babbling : babblings) {
            if(set.contains(babbling)) answer++;
        }
        return answer;
    }

    private void combination(String path) {
        if (!path.isEmpty()) {
            set.add(path);
        }
        for (int i = 0; i < enable.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                combination(path + enable[i]);
                visited[i] = false;
            }
        }
    }


    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[]{"aya", "yee", "u", "maa", "wyeoo"}, 1);
        // 입출력 예 #2
        test(sol, new String[]{"ayaye", "uuuma", "ye", "yemawoo", "ayaa"}, 3);
    }

    private static void test(Solution sol, String[] babbling, int expected) {
        int result = sol.solution(babbling);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
