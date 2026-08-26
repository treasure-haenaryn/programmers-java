import java.util.*;

/**
 * 단체사진 찍기
 * https://school.programmers.co.kr/learn/courses/30/lessons/1835
 * 분류: 완전탐색 (Lv2)
 *
 * [문제 요약]
 * 카카오프렌즈 8명을 일렬로 세우는 모든 순서(순열) 중에서,
 * 주어진 조건(예: 두 사람이 나란히/특정 거리 이상·이하로 서야 함)을
 * 전부 만족하는 배치의 개수를 구한다.
 *
 * [제한사항]
 * - n(조건 개수): 1 이상 100 이하
 * - data의 각 원소는 5글자 문자열 "X~Y=k" 형태
 *   - X, Y: 사람을 나타내는 알파벳 (A, C, F, J, M, N, R, T 중 하나)
 *   - 두 번째 글자는 항상 '~'
 *   - 네 번째 글자(연산자): '=', '<', '>' 중 하나
 *   - 다섯 번째 글자(거리): 0 이상 6 이하의 정수
 */
class Solution {

    private static int count = 0;
    private static boolean[] visited = new boolean[8];
    private static char[] names = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};

    public int solution(int n, String[] data) {
        count = 0;
        permutate(data, new ArrayList<>());
        return count;
    }

    public void permutate(String[] data,List<Character> current){
        if(current.size() == 8){
            for(String condition : data){
                for(char c : condition.toCharArray()){
                    char first = condition.charAt(0);
                    char second = condition.charAt(2);
                    char operator = condition.charAt(3);
                    int distance = condition.charAt(4) - '0';
                    int firstIdx = current.indexOf(first);
                    int secondIdx = current.indexOf(second);
                    int between = Math.abs(firstIdx - secondIdx) - 1;

                    if (operator == '=') {
                        if (between != distance) {
                            return;
                        }
                    } else if (operator == '<') {
                        if (between >= distance) {
                            return;
                        }
                    } else if (operator == '>') {
                        if (between <= distance) {
                            return;
                        }
                    }
                }
            }
            count++;
            return;
        }

        for (int i = 0; i < 8; i++) {
            if(!visited[i]){
                visited[i] = true;
                current.add(names[i]);
                permutate(data, current);
                current.remove(current.size() - 1);
                visited[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 입출력 예 #1
        test(sol, 2, new String[]{"N~F=0", "R~T>2"}, 3648);
        // 입출력 예 #2
        test(sol, 2, new String[]{"M~C<2", "C~M>1"}, 0);
    }

    private static void test(Solution sol, int n, String[] data, int expected) {
        int result = sol.solution(n, data);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] expected=%d, actual=%d%n", status, expected, result);
    }
}
