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
        /**
         * 1. 무엇을 구해야하는지?
         * → 광고 누적 시청 시간이 가장 많은 시작 구간
         *
         * 결국 구간을 시간으로 표현한게 전부.
         * 시간을 결국 숫자로 표현하면 시간*3600, 분*60, 초로 그래프? 표현
         *
         * 광고 구간은 결국 고정된 슬라이딩.
         *
         * 로그 별 구간 계산이 필요함..
         * 로그의 시작과 끝을 1,-1로 펴햔하고 결국 누적합을 통해서 전체 구간에 대한 값을 구할 수 있음
         */

        int playSec = toSec(play_time);
        int advSec = toSec(adv_time);

        // 차분 배열: [start, end) 구간을 표현하기 위해 end == playSec인 경우까지 저장
        int[] viewers = new int[playSec+1];

        for (String log : logs) {
            int start = toSec(log.split("-")[0]);
            int end = toSec(log.split("-")[1]);
            viewers[start] += 1;
            viewers[end] -= 1;
        }

        for (int i = 0; i < playSec; i++) {
            viewers[i + 1] += viewers[i];
        }


        // 누적 재생 시간은 long이 될 수 있음.
        long max = Long.MIN_VALUE;
        long sum = 0;
        int start = 0;
        int left = 0;

        for (int right = 0; right < viewers.length; right++) {
            sum += viewers[right];

            if (right - left + 1 == advSec) {
                if (sum > max) {
                    max = sum;
                    start = left;
                }
                sum -= viewers[left];
                left++;
            }
        }
        return toTime(start);
    }

    private int toSec(String time) {
        String[] split = time.split(":");
        return Integer.parseInt(split[0]) * 3600 + Integer.parseInt(split[1]) * 60 + Integer.parseInt(split[2]);
    }

    private String toTime(int sec) {
        int hour = sec/3600;
        int minute = (sec % 3600) / 60;
        int second = sec % 60;
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
