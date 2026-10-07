/*

### 연습문제 — 구간별 판매량

한 상점의 날짜별 판매량이 정수 배열 `1``에 주어집니다.

여러 개의 구간을 나타내는 `queries`가 주어질 때, 각 구간의 **판매량 합계**를 구하세요.

`queries[i] = [L, R]`이며, `L`번째 인덱스부터 `R`번째 인덱스까지 **모두 포함**합니다. 인덱스는 **0-based**입니다.

예를 들어,

```text
sales = [2, 4, 1, 5, 3]

queries = [
    [1, 3],
    [0, 4],
    [2, 2]
]
```

첫 번째 질문 `[1, 3]`은:

```text
index    0   1   2   3   4
sales   [2,  4,  1,  5,  3]
             └───────┘

4 + 1 + 5 = 10
```

두 번째 `[0, 4]`는 전체 구간이므로:

```text
2 + 4 + 1 + 5 + 3 = 15
```

세 번째 `[2, 2]`는 하나의 원소만 포함하므로:

```text
1
```

따라서 결과는:

```text
[10, 15, 1]
```

제한사항은 다음과 같다고 가정해보세요.

```text
1 ≤ sales.length ≤ 100,000
1 ≤ queries.length ≤ 100,000
0 ≤ L ≤ R < sales.length
1 ≤ sales[i] ≤ 1,000,000
```

여기서 중요한 조건이 `sales.length`와 `queries.length`가 **각각 최대 10만**이라는 겁니다.


```text
1. 문제에서 결국 뭘 구하라는 건가?
: 각 주어진 구간의 합

2. 가장 단순하게 풀면 어떻게 할 수 있을까?
: 단순하게 풀면 각 주어진 구간의 합을 하나씩 더가는 구조

3. 그 방법의 시간복잡도는?
: N*Q > N^2

4. 최대 입력이 들어왔을 때 괜찮을까?
: 100억이 되서 시간 복잡도 제약에 걸림

5. 반복해서 계산하고 있는 것이 있는가?
: 목적 구간까지의 합을 구한뒤에 시작 구간의 합을 빼면되는 구조

6. 그 반복 계산을 미리 해둘 수 있는가?
: 각 위치까지의 누적 합을 미리 구한다.
```


① prefix를 만드는 데 몇 번 연산?
: N번 연산

② query 하나를 처리하는 데 몇 번 연산?
: O(1)

③ query가 Q개면?
: N+Q

④ 그래서 전체 시간복잡도는?
: N

왜 누적합이 N^2의 계산을 빠르게 하는지 알수 있음.
 */

/*
sales = [2, 4, 1, 5, 3]

queries = [
    [1, 3],
    [0, 4],
    [2, 2]
]

기대 결과
[10, 15, 1]
 */
class Solution {
    public long[] solution(int[] sales, int[][] queries) {
        // 직접 작성
        long[] answer = new long[queries.length];
        long[] prefix = new long[sales.length + 1];

        for (int i = 0; i < sales.length; i++) {
            prefix[i + 1] = prefix[i] + sales[i];
        }

        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            answer[i] = prefix[query[1] + 1] - prefix[query[0]];
        }

        return answer;
    }
}