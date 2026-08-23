import java.util.*;

/**
 * 완주하지 못한 선수
 * https://school.programmers.co.kr/learn/courses/30/lessons/42576
 * 분류: 해시 (Lv1)
 *
 * [문제 요약]
 * 마라톤 참가자 목록(participant)과 완주자 목록(completion)이 주어진다.
 * completion은 participant보다 정확히 1명 적으며, 완주하지 못한 단 한 명의
 * 이름을 반환한다. 동명이인이 존재할 수 있다.
 *
 * [제한사항]
 * - participant 길이: 1 이상 100,000 이하
 * - completion 길이 = participant 길이 - 1
 * - 이름은 1~20자의 알파벳 소문자
 * - 동명이인이 있을 수 있음
 */
class Solution {

    public String solution(String[] participant, String[] completion) {
        String answer = "";

        Map<String, Integer> participantMap = new HashMap<>();
        Map<String, Integer> completionMap = new HashMap<>();

        for(String name : participant) {
            participantMap.put(name, participantMap.getOrDefault(name, 0) + 1);
        }
        for(String name : completion) {
            completionMap.put(name, completionMap.getOrDefault(name, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : participantMap.entrySet()) {
            String name = entry.getKey();
            int participantCount = entry.getValue();
            int completionCount = completionMap.get(name) == null ? 0 : completionMap.get(name);
            if(participantCount != completionCount) {
                answer = name;
                break;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[]{"leo", "kiki", "eden"}, new String[]{"eden", "kiki"}, "leo");
        // 입출력 예 #2
        test(sol, new String[]{"marina", "josipa", "nikola", "vinko", "filipa"}, new String[]{"josipa", "filipa", "marina", "nikola"}, "vinko");
        // 입출력 예 #3
        test(sol, new String[]{"mislav", "stanko", "mislav", "ana"}, new String[]{"stanko", "ana", "mislav"}, "mislav");
    }

    private static void test(Solution sol, String[] participant, String[] completion, String expected) {
        String result = sol.solution(participant, completion);
        String status = result.equals(expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%s, actual=%s%n", status, expected, result);
    }
}
