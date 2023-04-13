import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SpringArray {

    public static Spring equivalentSpring(String springExpr) {
        Stack<Spring> seriesStack = new Stack<>();
        Stack<Spring> parallelStack = new Stack<>();
        for (int i = 0; i < springExpr.length(); i++) {
            char c = springExpr.charAt(i);
            if (c == '{') {
                seriesStack.push(new Spring());
            } else if (c == '[') {
                parallelStack.push(new Spring());
            } else if (c == '}') {
                Spring s = seriesStack.pop();
                if (!seriesStack.empty()) {
                    seriesStack.peek().inSeries(s);
                }
            } else if (c == ']') {
                Spring s = parallelStack.pop();
                if (!parallelStack.empty()) {
                    parallelStack.peek().inParallel(s);
                }
            }
        }
        while (!parallelStack.empty()) {
            seriesStack.peek().inParallel(parallelStack.pop());
        }
        if (seriesStack.empty()) {
            return new Spring();
        }
        return seriesStack.pop();
    }

    public static Spring equivalentSpring(String springExpr, Spring[] springs) {
        Map<Character, Spring> map = new HashMap<>();
        for (int i = 0; i < springExpr.length(); i++) {
            char c = springExpr.charAt(i);
            if (c == '{' || c == '[') {
                continue;
            } else if (c == '}' || c == ']') {
                Spring s = map.get(c);
                if (c == '}') {
                    for (int j = i + 1; j < springExpr.length(); j++) {
                        char d = springExpr.charAt(j);
                        if (d != ' ') {
                            if (d == '{' || d == '[') {
                                Spring s2 = equivalentSpring(springExpr.substring(j));
                                s.inSeries(s2);
                                break;
                            } else {
                                s.inSeries(springs[d - '0']);
                                break;
                            }
                        }
                    }
                } else {
                    for (int j = i + 1; j < springExpr.length(); j++) {
                        char d = springExpr.charAt(j);
                        if (d != ' ') {
                            if (d == '{' || d == '[') {
                                Spring s2 = equivalentSpring(springExpr.substring(j));
                                s.inParallel(s2);
                                break;
                            } else {
                                s.inParallel(springs[d - '0']);
                                break;
                            }
                        }
                    }
                }
            } else {
                map.put(c, new Spring());
            }
        }
        return map.get(springExpr.charAt(0));
    }
}
