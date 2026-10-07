import java.util.*;
import java.util.stream.Collectors;

/**
 * [카카오 인턴] 보석 쇼핑
 * https://school.programmers.co.kr/learn/courses/30/lessons/67258
 * 분류: 2020 카카오 인턴십 (Lv3)
 * <p>
 * [문제 요약]
 * 진열대에 보석이 일렬로 놓여 있고, 연속된 구간 하나를 통째로 산다.
 * 진열된 보석 종류를 전부 1개 이상 포함하는 구간 중 가장 짧은 구간을 찾는다.
 * 가장 짧은 구간이 여럿이면 시작 번호가 가장 작은 구간을 고른다.
 * [시작 진열대 번호, 끝 진열대 번호]를 반환한다 (진열대 번호는 1부터).
 * 정확성·효율성 테스트가 따로 채점된다.
 * <p>
 * [제한사항]
 * - 1 <= gems.length <= 100,000
 * - gems[i]는 (i+1)번 진열대의 보석 이름
 * - 보석 이름은 길이 1~10의 알파벳 대문자 문자열
 */
class Solution {

    public int[] solution(String[] gems) {
        int kind = new HashSet<>(Arrays.asList(gems)).size();
        Map<String, Integer> map = new HashMap<>();
        int left = 0, right = 0;
        int min = Integer.MAX_VALUE;
        int[] answer = new int[2];

        // 가변 슬라이딩 kind 기준
        for (right = 0; right < gems.length; right++) {
            String key = gems[right];
            map.merge(key, 1, Integer::sum);

            while (map.size() == kind) {

                int newMin = right - left + 1;
                if (min > newMin) {
                    min = newMin;
                    answer = new int[]{left + 1, right + 1};
                }

                String leftKey = gems[left];
                int value = map.get(leftKey) - 1;
                if (value == 0) {
                    map.remove(leftKey);
                } else {
                    map.put(leftKey, value);
                }

                left++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[]{"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"}, new int[]{3, 7});
        // 입출력 예 #2
        test(sol, new String[]{"AA", "AB", "AC", "AA", "AC"}, new int[]{1, 3});
        // 입출력 예 #3
        test(sol, new String[]{"XYZ", "XYZ", "XYZ"}, new int[]{1, 1});
        // 입출력 예 #4
        test(sol, new String[]{"ZZZ", "YYY", "NNNN", "YYY", "BBB"}, new int[]{1, 5});
    }

    private static void test(Solution sol, String[] gems, int[] expected) {
        int[] result = sol.solution(gems.clone());
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] gems=%s | expected=%s, actual=%s%n",
                status, Arrays.toString(gems), Arrays.toString(expected), Arrays.toString(result));
    }
}
