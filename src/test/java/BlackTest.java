import org.example.DrawerExample1;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackTest {
    static DrawerExample1 d = new DrawerExample1();
    @Test
    public void test1() {
        assertEquals(d.queryBridgeWords("not_in_graph1", "not_in_graph1"), "NO1");
    }
    @Test
    public void test2() {
        assertEquals(d.queryBridgeWords("not_in_graph", "new"), "NO2");
    }
    @Test
    public void test3() {
        assertEquals(d.queryBridgeWords("to", "not_in_graph"), "NO3");
    }
    @Test
    public void test4() {
        assertEquals(d.queryBridgeWords("to", "new"), "NO5");
    }
    @Test
    public void test5() {
        assertEquals(d.queryBridgeWords("new", "room"), "big");
    }
    @Test
    public void test6() {
        assertEquals(d.queryBridgeWords("create", "new"), "a enough two");
    }
}

