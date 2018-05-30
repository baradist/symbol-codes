package cf.baradist;

import cf.baradist.algorithms.Algorithm;
import cf.baradist.algorithms.Codable;
import cf.baradist.algorithms.Huffman;
import cf.baradist.algorithms.ShannonFano;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Algorithm algorithm = null;
        String message = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-a")) {
                algorithm = getAlgorithmFromString(args[++i]);
            } else if (args[i].equals("-m")) {
                message = args[++i];
            }
        }
        boolean exit = false;
        if (algorithm == null) {
            exit = true;
            System.out.println("Set an algorithm:\n" +
                    "-a <algorithm>\n" +
                    "possible values: ");
            System.out.println(Arrays.stream(Algorithm.values())
                    .map(a -> a.name() + " - " + a.toString())
                    .collect(Collectors.joining(", ")));
        }
        if (message.isEmpty()) {
            exit = true;
            System.out.println("Set a message:\n" +
                    "-m \"<message>\"");
        }
        if (exit) {
            return;
        }

        System.out.println("Algorithm: " + algorithm);
        System.out.println("Message: " + message);

        Codable codable = null;
        switch (algorithm) {
            case SF:
                codable = new ShannonFano();
                break;
            case H:
                codable = new Huffman();
                break;
        }
        codable.getCodes(message).forEach(System.out::println);
        System.out.println("Average lenght of symbols is " + codable.getAverageLenght());
        System.out.println("Entropy is " + codable.getEntropy());
    }

    private static Algorithm getAlgorithmFromString(String str) {
        try {
            return Algorithm.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
