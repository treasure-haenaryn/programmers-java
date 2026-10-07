public class Solution {
    /*
문제 — 가장 바쁜 시간대

어떤 서비스의 **시간별 접속자 수**가 배열 `users`로 주어집니다.

운영팀은 서버 점검을 위해 연속된 `k`시간 중 **접속자 수의 총합이 가장 많은 시간대**를 알고 싶습니다.

가장 접속자 수의 총합이 큰 **구간의 시작 인덱스**를 반환하세요.

최댓값을 가지는 구간이 여러 개라면 **가장 빠른 시작 인덱스**를 반환합니다.

예를 들어:

```text id="41frsp"
users = [3, 1, 4, 1, 5, 9, 2]
k = 3
```

`k = 3`이므로 연속된 3개의 값을 하나의 구간으로 봅니다.

```text id="w6lqu8"
index     0  1  2  3  4  5  6
users    [3, 1, 4, 1, 5, 9, 2]
```

반환해야 하는 것은 **최대 합 자체가 아니라 그 구간의 시작 인덱스**입니다.

제한사항은 다음과 같습니다.

```text id="m3xguh"
1 ≤ users.length ≤ 1,000,000
1 ≤ k ≤ users.length
0 ≤ users[i] ≤ 1,000,000
```

작성할 메서드는:

```java id="8awdo8"
public int solution(int[] users, int k) {

}
```


```text id="ah5cw9"
무엇을 구해야 하는가?
→ 길이가 K인 연속 구간 중 합이 가장 큰 구간의 시작 인덱스.

가장 단순하게 풀면?
→ 모든 시작 위치에서 K개의 원소를 직접 더해본다.

단순 풀이 시간복잡도?
→ O(N × K)
시작 위치를 약 N번 확인하고, 매번 K개를 더하기 때문입니다.

최대 입력에서 가능한가?
→ 불가능할 수 있다.
N이 100만이고 K도 큰 값이 될 수 있기 때문에 O(NK)는 너무 큽니다.

5. 반복해서 계산되는 부분은 무엇인가?
: 구간이 한 칸 이동할 때 겹치는 원소들의 합을 계속 다시 계산한다.

6. 어떻게 개선할 수 있을까?
: 누적합을 이용해서 투포인트로 처리

7. 개선한 방법의 시간복잡도는?
: O(N +1) = Q(N)
```

그리고 이번 문제에는 하나 더 생각할 게 있습니다.

**경계값을 직접 찾아보세요.**

예를 들어 `k`가 가질 수 있는 가장 작은 값이나 가장 큰 값일 때 문제가 없는지도 생각해보면 됩니다.

     */
    public int solution(int[] users, int k) {

        // 슬라이딩 윈도우 방식
        int max = Integer.MIN_VALUE;
        int sum = 0;
        int left = 0;
        int start = 0;

        for (int right = 0; right < users.length; right++) {
            sum += users[right];
            if (right - left + 1 == k) {
                if (sum > max) {
                    max = sum;
                    start = left;
                }
                sum -= users[left];
                left++;
            }
        }

        // 누적합

        int[] prefix = new int[users.length+1];

        for (int i = 0; i < users.length; i++) {
            prefix[i + 1] = prefix[i] + users[i];
        }



        return 0;
    }
}
