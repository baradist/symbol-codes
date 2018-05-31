package cf.baradist.algorithms;

import java.util.*;
import java.util.stream.Collectors;

public class Huffman extends AbstractCoding {
    private Node tree;

    @Override
    public void code(String s) {
        message = s;
        totalSymbolsAmount = s.length();

        List<Node> nodeList = s.codePoints()
                .mapToObj(c -> (char) c)
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

        tree = queue.poll();
        fillSymbolToCodes(tree, "");

        fillCodedMessage();

        symbolToCodes.sort(SymbolToCode::compareCounts);
    }

    @Override
    public String decode(String codedString) {
        StringBuilder sb = new StringBuilder();
        LinkedList<Character> characters = codedString.codePoints().mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!characters.isEmpty()) {
            sb.append(decode(characters, tree));
        }
        return sb.toString();
    }

    private Character decode(Queue<Character> characters, Node node) {
        if (node instanceof NodeWithSymbol) {
            return ((NodeWithSymbol) node).symbol;
        } else if (node instanceof NodeOfNodes) {
            Character character = characters.poll();
            if (Objects.requireNonNull(character) == '0') {
                return decode(characters, ((NodeOfNodes) node).left);
            } else if (Objects.requireNonNull(character) == '1') {
                return decode(characters, ((NodeOfNodes) node).right);
            }
        }
        return null;
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
