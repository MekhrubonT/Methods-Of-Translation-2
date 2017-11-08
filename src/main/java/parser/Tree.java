package parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Tree {
    private final String node;
    private final List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = children.length == 0 ? Collections.emptyList() : Arrays.asList(children);
    }

    public Tree(String node) {
        this(node, new Tree[0]);
    }

    @Override
    public String toString() {
        if (children.isEmpty()) {
            return "VARGROUPS".equals(node) || "VARLISTPRIME".equals(node) ? "" : node;
        }

        String result = children.stream().map(Tree::toString).collect(Collectors.joining());
        if (node.equals("V")) result += " ";
        return result;
    }
}
