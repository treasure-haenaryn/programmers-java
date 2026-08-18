import java.util.*;

/**
 * 단어 변환
 * https://school.programmers.co.kr/learn/courses/30/lessons/43163
 * 분류: 깊이/너비 우선 탐색(DFS/BFS) (Lv3)
 *
 * [문제 요약]
 * 시작 단어 begin을 목표 단어 target으로 바꾸려 한다.
 * 한 번에 한 글자만 바꿀 수 있고, 바뀐 단어는 반드시 words 목록에 있어야 한다.
 * target에 도달하기까지 필요한 최소 변환 횟수를 반환한다. 도달 불가능하면 0을 반환한다.
 *
 * [제한사항]
 * - 모든 단어는 소문자 알파벳으로만 구성, 길이는 3~10자로 동일
 * - words의 단어 개수는 3~50개, 중복 없음
 * - begin과 target은 서로 다름
 */
class Solution {

    public int solution(String begin, String target, String[] words) {
        Map<String, List<String>> path = new HashMap<>();
        path.put(begin, new ArrayList<>());
        for (int i = 0; i < words.length; i++) {
            path.put(words[i], new ArrayList<>());
        }
        if(!path.containsKey(target)) {
            return 0;
        }

        path.forEach((key, value) -> {
            char[] keyChars = key.toCharArray();
            for (int i = 0; i < words.length; i++) {
                int count = 0;
                char[] chars = words[i].toCharArray();
                for (int j = 0; j < keyChars.length; j++) {
                    if(count > 1){
                        break;
                    }
                    if(keyChars[j] != chars[j]) {
                        count++;
                    }
                }
                if(count == 1) {
                    value.add(words[i]);
                }
            }
        });
        return bfs(path, begin, target);
    }


    private int bfs(Map<String, List<String>> path, String begin, String target) {
        int count = 0;
        Set<String> set = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(begin);
        set.add(begin);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();

                if (cur.equals(target)) {
                    return count;
                }

                for (String next : path.get(cur)) {
                    if (!set.contains(next)) {
                        set.add(next);
                        queue.add(next);
                    }
                }
            }
            count++;
        }
        return 0;
    }


    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, "hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"}, 4);
        // 입출력 예 #2
        test(sol, "hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log"}, 0);
    }

    private static void test(Solution sol, String begin, String target, String[] words, int expected) {
        int result = sol.solution(begin, target, words);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
