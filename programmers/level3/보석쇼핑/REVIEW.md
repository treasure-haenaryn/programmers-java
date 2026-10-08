# 보석 쇼핑 — 코드 리뷰

- 문제: https://school.programmers.co.kr/learn/courses/30/lessons/67258 (Lv3, 2020 카카오 인턴십)
- 리뷰 날짜: 2026-10-07
- 리뷰 시점 결과: 예제 #1 FAIL (`expected=[3, 7], actual=[1, 7]`) → 이후 수정 완료

## 잘한 점

- 전체 종류 수를 `HashSet`으로 먼저 구함
- 구간 안 보석별 개수를 `Map`으로 관리하고, 0이 되면 `remove`해서 `map.size()`로 종류 수 비교
- 가변 길이 슬라이딩 윈도우로 접근

## 버그 1. 왼쪽이 아니라 오른쪽 보석을 뺌

```java
int value = map.get(key) - 1;   // key = gems[right]
```

`key`는 방금 오른쪽에서 넣은 보석이다. 창을 왼쪽에서 줄이려면 `gems[left]`를 빼야 한다.

## 버그 2. 한 번만 줄이고 멈춤 (`if`)

모든 종류가 모인 순간에도 왼쪽에 없어도 되는 보석이 여러 개 있을 수 있다.
조건을 만족하는 동안 계속 줄이면서 최소 길이를 갱신해야 하므로 `while`이어야 한다.

## 예제 #1 추적

`[DIA, RUBY, RUBY, DIA, DIA, EMERALD, SAPPHIRE, DIA]`, 종류 4개

```
right=6 (SAPPHIRE): 4종류 → 구간 [0,6] → answer = [1, 7]
  기존 코드: SAPPHIRE를 빼버리고(버그 1) 한 번만 줄임(버그 2)
  → 3종류만 남고 다시 4종류가 안 모여서 [1, 7]로 끝남

올바르게 줄이면:
  [0,6] 길이 7 → DIA 빼도 OK
  [1,6] 길이 6 → RUBY 빼도 OK (RUBY 하나 더 있음)
  [2,6] 길이 5 → RUBY 빼면 0개 → 종료
  → 최소 구간 [2,6] = 답 [3, 7]
```

## 수정

```java
while (map.size() == kind) {                 // if → while
    int newMin = right - left + 1;
    if (min > newMin) {
        min = newMin;
        answer = new int[]{left + 1, right + 1};
    }

    String leftKey = gems[left];             // key → gems[left]
    int value = map.get(leftKey) - 1;
    if (value == 0) map.remove(leftKey);
    else map.put(leftKey, value);
    left++;
}
```

검증: 예제 4개 모두 PASS

## 기억할 포인트: 가변 길이 슬라이딩 윈도우 템플릿

```
for (right ...) {
    오른쪽 원소(arr[right]) 넣기
    while (조건 만족) {      ← if가 아니라 while
        정답 갱신
        왼쪽 원소(arr[left]) 빼기, left++
    }
}
```

- 넣는 건 `arr[right]`, 빼는 건 `arr[left]`
