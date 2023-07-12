import com.alibaba.fastjson.JSON;
import com.lc.structure.graph.ss.Graph;
import javafx.util.Pair;
import org.junit.Test;

import java.util.*;

public class BaseTest {
    @Test
    public void test() {
        System.out.println("这只是一个测试！");
    }


    @Test
    public void testStack() {
        // 这个test的目的是，切记：递归的过程中是可以记录一些东西的！！！
        Deque<Integer> stack = new LinkedList<>(Arrays.asList(1, 2, 3));
        System.out.println(stack);
        this.reverse(stack);
        System.out.println(stack);
    }

    private void reverse(Deque<Integer> stack) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        int bottom = this.getStackBottomElement(stack);
        this.reverse(stack);
        stack.offerLast(bottom);
    }

    private Integer getStackBottomElement(Deque<Integer> stack) {
        Integer result = stack.pollLast();
        if (stack.isEmpty()) {
            return result;
        }
        int bottom = this.getStackBottomElement(stack);
        stack.offerLast(result);
        return bottom;
    }
}
