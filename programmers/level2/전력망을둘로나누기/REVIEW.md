# 전력망을 둘로 나누기 — 코드 리뷰

- 문제: https://school.programmers.co.kr/learn/courses/30/lessons/86971 (Lv2, 완전탐색)
- 리뷰 날짜: 2026-10-08
- 최종 결과: 예제 3개 모두 PASS

## 핵심 아이디어

1. 송전탑 n개, 전선 n-1개, 모두 연결 → **트리** (사이클 없는 무방향 그래프)
2. 트리에서 간선 하나를 끊으면 **반드시 정확히 두 덩이**로 나뉨 (우회로가 없음)
3. 두 덩이를 모두 탐색할 필요 없음. **한쪽만 DFS로 세고** 반대쪽은 `n - 한쪽`
4. 모든 전선을 하나씩 끊어 보며 차이의 최솟값 (완전탐색 + DFS)

```
예제 #1, [4-7] 끊기
4 쪽: {1, 2, 3, 4, 5, 6} = 6개 / 7 쪽: 9 - 6 = 3개 / 차이 = 3
```

## 개념 정리

### 트리는 방향이 없다

- 루트 → 자식 모양은 한 노드를 루트로 정해 내려다본 관점일 뿐
- 전선 `[a, b]`는 양방향 → `graph[a].add(b)`, `graph[b].add(a)` 둘 다
- 한쪽만 넣으면 출발 위치에 따라 일부 노드에 못 감 (예: 4에서 3 쪽으로 못 감)

### visited는 노드 기준

- 구하는 값이 노드(송전탑) 개수 → 노드 visited `boolean[n+1]`
- 간선은 노드에 도달하는 통로일 뿐, 한 번 도착한 노드는 다시 안 감
- 간선 visited가 필요한 경우: 간선을 전부/한 번씩 써야 할 때 (예: 여행경로의 티켓)
- 끊은 전선은 visited가 아니라 DFS 안의 **건너뛰기 조건**으로 처리

```java
if ((cur == cutA && next == cutB) || (cur == cutB && next == cutA)) continue;
```

## 리뷰에서 나온 버그

| 위치 | 문제 | 수정 |
|---|---|---|
| `n = wires.length;` | 매개변수 `n`(송전탑 수)을 전선 수로 덮어씀 → `graph[n]` 미초기화 NPE | `n`은 건드리지 않고 `wires.length`를 직접 사용 |
| 초기화 루프 `i <= wires.length` | `graph[n]`이 null | `i <= n` |
| 전선 루프 `i <= wires.length` | `wires[n-1]` 범위 초과 | `i < wires.length` |
| `boolean[][] visited` | 노드 수만 세면 되므로 2차원 불필요 | `boolean[n + 1]`, 전선마다 새로 생성 |
| `Arrays.stream(visited)` | `boolean[]` 스트림 없음 (컴파일 에러) | 재귀 반환값으로 `count += dfs(...)` |
| `new ArrayList<>[n + 1]` | 제네릭 배열 생성 불가 (컴파일 에러) | `new ArrayList[n + 1]` 또는 `List<List<Integer>>` |
| `Math.min(min, graph.size() - dfs - 1)` | 반대쪽 크기를 구했을 뿐 차이가 아님, `min` 갱신 안 함 | 아래 참고 |
| `return -1;` | 구한 값과 무관하게 -1 | `return min;` |

## 크기 vs 차이

`전체 - 첫 번째 덩이`는 **두 번째 덩이의 크기**이지 차이가 아니다.

```
[3-4] 끊기: 첫 번째 {1, 2, 3} = 3개, 두 번째 = 9 - 3 = 6개
전체 - 첫 번째 = 6   ← 두 번째 덩이 크기
실제 차이     = |3 - 6| = 3
```

```java
int first = dfs(1, target, new boolean[n + 1]);  // 첫 번째 덩이
int second = n - first;                           // 두 번째 덩이
int diff = Math.abs(first - second);              // 문제가 묻는 차이
min = Math.min(min, diff);
```

## 기억할 포인트

- 트리 + 간선 하나 제거 = 두 덩이. 한쪽만 세고 나머지는 `n - 한쪽`
- visited 기준은 "무엇을 세는가"로 정한다 (노드 vs 간선)
- 문제가 묻는 값을 변수 이름으로 그대로 쓰면 "크기인지 차이인지" 헷갈리지 않는다
- `<` / `<=` 경계 실수가 이번에도 두 군데 → 루프마다 마지막 값 대입해 보기
