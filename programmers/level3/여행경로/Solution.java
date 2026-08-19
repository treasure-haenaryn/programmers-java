import java.util.*;

/**
 * 여행경로
 * https://school.programmers.co.kr/learn/courses/30/lessons/43164
 * 분류: 깊이/너비 우선 탐색(DFS/BFS) (Lv3)
 * <p>
 * [문제 요약]
 * ICN 공항에서 출발해 주어진 항공권(tickets)을 모두 사용하는 여행 경로를 구한다.
 * 경로가 여러 개 존재할 수 있으면 알파벳 순서가 가장 앞서는 경로를 반환한다.
 * <p>
 * [제한사항]
 * - 공항은 알파벳 대문자 3글자
 * - 공항 수는 3개 이상 10,000개 이하
 * - 주어진 항공권은 모두 사용해야 함
 * - 모든 도시를 방문할 수 없는 경우는 주어지지 않음
 */
class Solution {

    public String[] solution(String[][] tickets) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (String[] ticket : tickets) {
            String from = ticket[0];
            String to = ticket[1];
            map.computeIfAbsent(from, k -> new PriorityQueue<>()).add(to);
        }
        return dfs(map, new ArrayList<>(), "ICN").toArray(String[]::new);
    }

    private List<String> dfs(Map<String, PriorityQueue<String>> map, List<String> path, String startingPoint) {
        path.add(startingPoint);
        if (map.get(startingPoint) != null && !map.get(startingPoint).isEmpty()) {
            String destination = map.get(startingPoint).poll();
            dfs(map, path, destination);
        }
        return path;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, new String[][]{{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}},
                new String[]{"ICN", "JFK", "HND", "IAD"});
        // 입출력 예 #2
        test(sol, new String[][]{{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL", "SFO"}},
                new String[]{"ICN", "ATL", "ICN", "SFO", "ATL", "SFO"});
    }

    private static void test(Solution sol, String[][] tickets, String[] expected) {
        String[] result = sol.solution(tickets);
        String status = Arrays.equals(result, expected) ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%s, actual=%s%n", status, Arrays.toString(expected), Arrays.toString(result));
    }
}
