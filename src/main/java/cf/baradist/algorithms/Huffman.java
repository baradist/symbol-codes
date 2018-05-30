package cf.baradist.algorithms;

import cf.baradist.SymbolToCode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Huffman extends AbstractCoding {

    @Override
    public List<SymbolToCode> getCodes(String s) {
        totalSymbolsAmount = s.length();
        char[] chars = s.toCharArray();
        List<Character> list = new ArrayList<>(s.length());
        for (char c : chars) {
            list.add(c);
        }

        List<Node> nodeList = list.stream()
                .collect(Collectors.groupingBy(character -> character, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new NodeWithSymbol(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());


        PriorityQueue<Node> queue = new PriorityQueue(nodeList);
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            queue.add(new NodeOfNodes(left.weight + right.weight, left, right));
        }
        symbolToCodes = new ArrayList<>(nodeList.size());

        fillSymbolToCodes(queue.poll(), "");

        symbolToCodes.sort((o1, o2) -> (o1.getCount() - o2.getCount()) > 0 ? -1 : 1);
        return symbolToCodes;
    }

    private void fillSymbolToCodes(Node node, String prefix) {
        if (node instanceof NodeWithSymbol) {
            symbolToCodes.add(new SymbolToCode(((NodeWithSymbol) node).symbol, node.weight, prefix));
            return;
        }
        if (node instanceof NodeOfNodes) {
            if (((NodeOfNodes) node).left != null) {
                fillSymbolToCodes(((NodeOfNodes) node).left, prefix + "0");
            }
            if (((NodeOfNodes) node).right != null) {
                fillSymbolToCodes(((NodeOfNodes) node).right, prefix + "1");
            }
        }
    }

    private static class Node implements Comparable {
        protected Long weight;

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Node)) {
                throw new IllegalArgumentException("Attampt to compare with not a Node instance - " + o);
            }
            return (this.weight - ((Node) o).weight) < 0 ? -1 : 1;
        }
    }

    private static final class NodeOfNodes extends Node {
        private Node left;
        private Node right;

        public NodeOfNodes(Long weight, Node left, Node right) {
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

    }

    private static final class NodeWithSymbol extends Node {
        private Character symbol;

        public NodeWithSymbol(Long weight, Character symbol) {
            this.weight = weight;
            this.symbol = symbol;
        }
    }
}
