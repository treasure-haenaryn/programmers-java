import java.util.*;

/**
 * 광고 삽입
 * https://school.programmers.co.kr/learn/courses/30/lessons/72414
 * 분류: 2021 KAKAO BLIND RECRUITMENT (Lv3)
 * <p>
 * [문제 요약]
 * 길이 play_time인 동영상에 길이 adv_time인 광고를 한 구간 끼워 넣는다.
 * 시청자들의 재생 기록(logs, 각 "시작-종료")이 주어질 때, 광고 구간과 겹치는 시청 시간의 총합(누적 재생시간)이
 * 최대가 되는 광고 시작 시각을 "HH:MM:SS"로 반환한다. 최대인 곳이 여럿이면 가장 이른 시각을 반환한다.
 * 재생 시간 = 종료 시각 - 시작 시각 (00:00:01~00:00:10 재생은 9초).
 * <p>
 * [제한사항]
 * - play_time, adv_time: "HH:MM:SS" (00:00:01 ~ 99:59:59), adv_time <= play_time
 * - 1 <= logs.length <= 300,000
 * - logs[i]: "H1:M1:S1-H2:M2:S2" (길이 17), 시작 < 종료, 둘 다 play_time 이내
 * - 누적 재생시간이 int 범위를 넘을 수 있음 → long 사용 권장
 */

class Solution {

    public String solution(String play_time, String adv_time, String[] logs) {
        // 풀이
        // 구간의 누적함을 먼저 도출해야함.
        // 분과 초는 60 단위... 이걸 초단위로 변경한뒤 배열 갯수로 빼야될듯?
        // 1시간은 3600초
        // 1분은 60초

        // logs의 각 값을 숫자로 표현 해당 구간 + 1

        // adv_time 기준으로 투포인터로 하나씩 이동하면서 최대합 구하기


        int playSec = toSec(play_time);
        int advSec = toSec(adv_time);

        // viewers[t] = t초 ~ t+1초 사이(1초 칸)에 영상을 보고 있던 시청자 수
        // 종료 시각이 play_time과 같을 수 있으므로 (playSec + 1) 크기로 잡는다
        long[] viewers = new long[playSec + 1];

        // [1단계] 차분 배열 표시 — 로그 하나당 딱 2칸만 기록 (버스: 승차 +1, 하차 -1)
        // 로그 "start-end"는 start초 칸부터 end-1초 칸까지 시청 → end에서 -1
        for (String log : logs) {
            String[] se = log.split("-");
            int start = toSec(se[0]);
            int end = toSec(se[1]);
            viewers[start] += 1;
            viewers[end] -= 1;
        }

        // [2단계] 앞에서부터 누적 — 표시된 +1/-1이 이어지면서 각 초의 시청자 수가 됨
        // 이 반복이 끝나면 viewers[t]는 "변화량"이 아니라 "그 1초 동안의 시청자 수"
        for (int t = 1; t < playSec; t++) {
            viewers[t] += viewers[t - 1];
        }

        // [3단계] advSec 길이의 창을 밀면서 viewers 합이 최대인 시작 시각 찾기 (합은 long)
        long max = Long.MIN_VALUE;
        long sum = 0;
        int left = 0;
        int bestStart = 0;
        // 광고 [s, s+advSec)는 영상 안에 있어야 하므로 right는 playSec-1까지만
        for (int right = 0; right < playSec; right++) {
            sum += viewers[right];

            if (right - left + 1 == advSec) {
                if (sum > max) {
                    max = sum;
                    bestStart = left;
                }
                sum -= viewers[left];
                left++;
            }
        }

        return toTime(bestStart);
    }

    // "HH:MM:SS" → 초
    private int toSec(String time) {
        String[] hms = time.split(":");
        return Integer.parseInt(hms[0]) * 3600
                + Integer.parseInt(hms[1]) * 60
                + Integer.parseInt(hms[2]);
    }

    private String toTime(long sec) {

        int hour = Math.toIntExact(sec / 3600);
        int minute = Math.toIntExact((sec % 3600) / 60);
        int second = Math.toIntExact(sec % 60);

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, "02:03:55", "00:14:15",
                new String[]{"01:20:15-01:45:14", "00:40:31-01:00:00", "00:25:50-00:48:29", "01:30:59-01:53:29", "01:37:44-02:02:30"},
                "01:30:59");
        // 입출력 예 #2
        test(sol, "99:59:59", "25:00:00",
                new String[]{"69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00"},
                "01:00:00");
        // 입출력 예 #3
        test(sol, "50:00:00", "50:00:00",
                new String[]{"15:36:51-38:21:49", "10:14:18-15:36:51", "38:21:49-42:51:45"},
                "00:00:00");
    }

    private static void test(Solution sol, String play_time, String adv_time, String[] logs, String expected) {
        String result = sol.solution(play_time, adv_time, logs.clone());
        String status = expected.equals(result) ? "PASS" : "FAIL";
        System.out.printf("[%s] play_time=%s, adv_time=%s, logs=%d건 | expected=%s, actual=%s%n",
                status, play_time, adv_time, logs.length, expected, result);
    }
}
