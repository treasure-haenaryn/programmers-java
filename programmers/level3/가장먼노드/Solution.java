import java.util.*;

/**
 * 가장 먼 노드
 * https://school.programmers.co.kr/learn/courses/30/lessons/49189
 * 분류: 그래프 (Lv3)
 *
 * [문제 요약]
 * 1~n번 노드로 이루어진 양방향 그래프가 주어진다.
 * 1번 노드에서 각 노드까지 최단 경로로 갈 때 지나는 간선 수를 거리로 본다.
 * 거리가 가장 먼 노드가 몇 개인지 반환한다.
 *
 * [제한사항]
 * - 2 ≤ n ≤ 20,000
 * - 1 ≤ 간선 수 ≤ 50,000, 간선은 양방향
 * - edge[i] = [a, b]: a번과 b번 노드 사이에 간선이 있음
 */
class Solution {

    // 인접리스트
    List<List<Integer>> graph;
    // 방문여부
    boolean[] visited;
    // depth 단위 갯수 관리
    Map<Integer, Integer> map;

    static class State {
        int to;
        int depth;

        public State(int to, int depth){
            this.to = to;
            this.depth = depth;
        }
    }

    public int solution(int n, int[][] edges) {
        // 인접리스트 초기화
        graph = new ArrayList<>();
        for(int i = 0 ; i <= n ; i++){
            graph.add(new ArrayList<>());
        }

        // 인접리스트 세팅
        for(int i=0;i< edges.length; i++){
            int[] edge = edges[i];
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        visited = new boolean[n+1];
        map = new HashMap<>();

        bfs(new State(1,0));


        int size = map.size();

        return map.get(size-1);
    }

    private void bfs(State state){
        Queue<State> queue = new ArrayDeque<>();
        queue.offer(state);
        visited[state.to] = true;
        map.merge(state.depth, 1, Integer::sum);

        while(!queue.isEmpty()){
            State cur = queue.poll();

            for(int next : graph.get(cur.to)){
                if(visited[next]) continue;
                visited[next] = true;
                State nextState = new State(next, cur.depth + 1);
                queue.offer(nextState);
                map.merge(nextState.depth, 1, Integer::sum);
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 6, new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}}, 3);
    }

    private static void test(Solution sol, int n, int[][] edge, int expected) {
        int result = sol.solution(n, edge);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] n=%d | expected=%d, actual=%d%n", status, n, expected, result);
    }
}
