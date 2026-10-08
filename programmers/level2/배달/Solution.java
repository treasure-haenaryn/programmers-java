import java.util.*;

/**
 * 배달
 * https://school.programmers.co.kr/learn/courses/30/lessons/12978
 * 분류: Summer/Winter Coding(~2018) (Lv2)
 *
 * [문제 요약]
 * 1~N번 마을이 양방향 도로로 연결되어 있고, 도로마다 지나는 데 걸리는 시간이 다르다.
 * 1번 마을의 음식점에서 출발해 K 시간 이하로 도착할 수 있는 마을이 몇 개인지 구한다.
 * (1번 마을 자신도 포함)
 *
 * [제한사항]
 * - 1 ≤ N ≤ 50
 * - 1 ≤ road 길이 ≤ 2,000, road[i] = [a, b, c] (a ≠ b, 1 ≤ c ≤ 10,000)
 * - 같은 두 마을 사이에 도로가 여러 개 있을 수 있음
 * - 1 ≤ K ≤ 500,000
 * - 모든 마을은 서로 오갈 수 있는 경로가 존재
 */
class Solution {

    List<List<Edge>> graph;
    int k;

    static class Edge {
        int to;
        int cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    static class State {
        int node;
        long distance;

        public State(int node, long distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public int solution(int N, int[][] road, int K) {
        graph = new ArrayList<>();
        k = K;
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < road.length; i++) {
            int from = road[i][0];
            int to = road[i][1];
            int cost = road[i][2];

            // 양방향
            graph.get(from).add(new Edge(to, cost));
            graph.get(to).add(new Edge(from, cost));
        }

        /**
         * 무엇을 구해야하는지?
         * → 1번 마을 K 시간 이내에 배달이 가능한 마을의 갯수
         *
         * - 자기 자신도 포함
         *
         * 마을 N : 노드, 도로 : 간선
         * 가중치 있음.
         * 시간 이므로 가중치는 양수만 있음
         *
         * 다익스트라 알고리즘 사용
         *
         * 모든 마을은 연결되어있음.
         */
        return dijkstra(1);
    }

    int dijkstra( int start) {
        // graph.get(1) ~ graph.get(vertexCount)를 사용하고 graph.get(0)은 비워 둔 경우
        // graph.size()는 vertexCount + 1이다.
        int vertexCount = graph.size() - 1;

        // dist[i]: start에서 i번 정점까지 현재까지 발견한 최소 누적 비용
        // 경로를 여러 개 저장하는 배열이 아니라, 정점마다 최선의 비용 하나를 저장한다.
        long[] dist = new long[vertexCount + 1];
        Arrays.fill(dist, Long.MAX_VALUE); // 아직 도달 방법을 모르는 상태 = 무한대

        // distance가 작은 State를 먼저 꺼내는 최소 힙
        // 정점 번호나 Edge.cost가 아니라 시작점부터의 누적 비용이 정렬 기준이다.
        PriorityQueue<State> pq = new PriorityQueue<>(
                (a ,b) -> Long.compare(a.distance, b.distance)
        );

        dist[start] = 0;                 // 시작점에서 시작점까지의 비용은 0
        pq.offer(new State(start, 0));    // 탐색 후보에 시작 상태 추가

        while (!pq.isEmpty()) {
            State current = pq.poll();   // 현재 가장 누적 비용이 작은 후보
            int now = current.node;      // 이번에 이웃을 확인할 정점
            long nowDistance = current.distance;

            // PQ에 남아 있던 오래된 후보는 무시한다.
            // 예: (5번, 비용 10)을 넣은 뒤, (5번, 비용 4)를 새로 찾은 경우
            if (nowDistance != dist[now]) continue;

            // now 정점에서 출발하는 모든 간선을 확인한다.
            for (Edge edge : graph.get(now)) {
                int next = edge.to; // now에서 한 번에 갈 수 있는 이웃 정점

                // start → ... → now → next 경로의 새 누적 비용
                long nextDistance = nowDistance + edge.cost;

                // 기존에 알고 있던 start → next 비용보다 짧을 때만 갱신한다.
                if (nextDistance < dist[next] && nextDistance <= k) {
                    dist[next] = nextDistance;
                    pq.offer(new State(next, nextDistance));
                }
            }
        }


        // 다익스트라에서 갱신 횟수 ≠ 도달한 노드 수예요. "몇 개인지"는 탐색 중에 세지 말고, 끝난 뒤 dist 배열을 기준으로 세는 게 안전
        int count = 0;
        for (int i = 1; i <= vertexCount; i++) {
            if (dist[i] <= k) count++;
        }
        // dist[i]에는 start에서 i번 정점까지의 최단 비용이 들어 있다.
        // 끝까지 Long.MAX_VALUE인 정점은 start에서 도달할 수 없는 정점이다.
        return count;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 5, new int[][]{{1, 2, 1}, {2, 3, 3}, {5, 2, 2}, {1, 4, 2}, {5, 3, 1}, {5, 4, 2}}, 3, 4);
        // 입출력 예 #2
        test(sol, 6, new int[][]{{1, 2, 1}, {1, 3, 2}, {2, 3, 2}, {3, 4, 3}, {3, 5, 2}, {3, 5, 3}, {5, 6, 1}}, 4, 4);
    }

    private static void test(Solution sol, int N, int[][] road, int K, int expected) {
        int result = sol.solution(N, road, K);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] N=%d, K=%d | expected=%d, actual=%d%n", status, N, K, expected, result);
    }
}
