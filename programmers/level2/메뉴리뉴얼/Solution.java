import java.util.*;

/**
 * 메뉴 리뉴얼
 * https://school.programmers.co.kr/learn/courses/30/lessons/72411
 * 분류: 완전탐색+해시 (Lv2)
 * <p>
 * [문제 요약]
 * 각 주문(orders)에 담긴 단품메뉴 알파벳 조합 중에서,
 * course에 주어진 개수(2개, 3개, ...)로 만들 수 있는 모든 조합을 뽑아 빈도를 센다.
 * 각 course 길이별로 "2번 이상 주문되었고, 그 길이에서 최다 빈도인" 조합들만
 * 코스요리 후보로 골라 사전순 정렬해 반환한다.
 * <p>
 * [제한사항]
 * - orders 길이: 2 이상 20 이하, 각 원소는 2~10자의 중복 없는 대문자 문자열
 * - course 길이: 1 이상 10 이하, 각 원소는 2 이상 10 이하의 자연수 (오름차순)
 * - 반환값: 알파벳 오름차순으로 정렬된 문자열 배열 (각 문자열 내부도 오름차순)
 */
class Solution {

    private static TreeMap<String, Integer> map;

    public String[] solution(String[] orders, int[] course) {
        List<String> list = new ArrayList<>();
        map = new TreeMap<>();

        Set<Integer> set = new HashSet<>(course.length);
        for (int c : course) {
            set.add(c);
        }

        // 각 주문에 대한 조합
        for (String order : orders) {
            char[] menu = order.toCharArray();
            Arrays.sort(menu);
            combination(menu, 0, new StringBuilder());
        }

        // 최대 길이
        Map<Integer, Integer> maxCountByLength = new HashMap<>();
        for (int length : course) {
            maxCountByLength.put(length, 0);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String menu = entry.getKey();
            int count = entry.getValue();
            int length = menu.length();

            // course에서 요구하는 길이가 아니면 제외
            if (!maxCountByLength.containsKey(length)) {
                continue;
            }

            // 최소 2명 이상 주문한 조합만 가능
            if (count < 2) {
                continue;
            }

            int currentMax = maxCountByLength.get(length);

            maxCountByLength.put(
                    length,
                    Math.max(currentMax, count));
        }


        // 각 길이의 최대 빈도와 같은 조합만 정답에 추가
        List<String> answer = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String menu = entry.getKey();
            int count = entry.getValue();
            int length = menu.length();

            if (!maxCountByLength.containsKey(length)) {
                continue;
            }

            int maxCount = maxCountByLength.get(length);

            if (count >= 2 && count == maxCount) {
                answer.add(menu);
            }
        }

        Collections.sort(answer);
        return answer.toArray(new String[0]);
    }

    public void combination(char[] order, int idx, StringBuilder sb) {
        for (int i = idx; i < order.length; i++) {
            sb.append(order[i]);
            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
            combination(order, i + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol,
                new String[]{"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"},
                new int[]{2, 3, 4},
                new String[]{"AC", "ACDE", "BCFG", "CDE"});
        // 입출력 예 #2
        test(sol,
                new String[]{"ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"},
                new int[]{2, 3, 5},
                new String[]{"ACD", "AD", "ADE", "CD", "XYZ"});
        // 입출력 예 #3
        test(sol,
                new String[]{"XYZ", "XWY", "WXA"},
                new int[]{2, 3, 4},
                new String[]{"WX", "XY"});
    }

    private static void test(Solution sol, String[] orders, int[] course, String[] expected) {
        String[] result = sol.solution(orders, course);
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%s, actual=%s%n", status, Arrays.toString(expected), Arrays.toString(result));
    }

}
