import java.util.*;

/**
 * 1부터 N까지 숫자 중 합이 10이 되는 조합 구하기
 *
 * - 백트래킹을 활용
 * - 숫자 조합은 오름차순으로 정렬
 * - 같은 숫자는 한번만 선택
 * - N은 1이상 10이하의 정수
 */
class Problem00 {

    private ArrayList<ArrayList<Integer>> result;
    private int n;

    private void backtrack(int sum, ArrayList<Integer> selectedNums, int start) {
        if (sum == 10) {
            result.add(selectedNums);
            return;
        }

        for (int i = start; i <= n; i++) {
            if (sum + i <= 10) {
                ArrayList<Integer> list = new ArrayList<>(selectedNums);
                list.add(i);
                backtrack(sum+i, list, i+1);
            }
        }
    }

    public ArrayList<ArrayList<Integer>> solution(int N) {
        result = new ArrayList<>();
        n = N;

        backtrack(0, new ArrayList<>(), 1);

        return result;
    }

    public static void main(String[] args) {
        Problem00 sol = new Problem00();

        System.out.println(sol.solution(5));
        System.out.println(sol.solution(2));
        System.out.println(sol.solution(7));
    }
}