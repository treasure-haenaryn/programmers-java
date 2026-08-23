import java.util.*;

/**
 * 소수 찾기
 * https://school.programmers.co.kr/learn/courses/30/lessons/42839
 * 분류: 완전탐색 (Lv2)
 *
 * [문제 요약]
 * 숫자가 적힌 종이 조각들(문자열 numbers의 각 자리)을 한 개 이상 골라
 * 순서대로 이어붙여 만들 수 있는 모든 수 중, 서로 다른 소수가 몇 개인지 구한다.
 * 앞자리가 0인 조합(예: "011")은 뒤의 자릿수만 유효한 수로 취급한다.
 *
 * [제한사항]
 * - numbers는 길이 1 이상 7 이하의 문자열
 * - numbers는 0~9 숫자로만 구성
 */
class Solution {

    boolean[] visited;

    public int solution(String numbers) {
        visited = new boolean[numbers.length()];
        Set<Integer> numberSet = new HashSet<>();
        permute(numbers, numberSet, "");

        int answer = 0;
        for (int n : numberSet) {
            if (isPrime(n)) answer++;
        }
        return answer;
    }

    void permute(String numbers, Set<Integer> numberSet, String current) {
        if (!current.isEmpty()) {
            numberSet.add(Integer.parseInt(current));
        }
        for (int i = 0; i < numbers.length(); i++) {
            if (visited[i]) continue;
            visited[i] = true;
            permute(numbers, numberSet, current + numbers.charAt(i));
            visited[i] = false;
        }
    }

    boolean isPrime(int n) {
        if(n<2) return false;
        for (int i = 2; (long)i * i <= n; i++) {
            if(n % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, "17", 3);
        // 입출력 예 #2
        test(sol, "011", 2);
    }

    private static void test(Solution sol, String numbers, int expected) {
        int result = sol.solution(numbers);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
