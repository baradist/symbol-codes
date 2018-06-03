package cf.baradist.algorithms;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCoding implements Codable {
    protected List<SymbolToCode> symbolToCodes;
    protected int totalSymbolsAmount;
    protected String message;
    protected String codedMessage;

    @Override
    public abstract void code(String s);

    @Override
    public abstract String decode(String codedString);

    @Override
    public String gedCodedMessage() {
        return codedMessage;
    }

    @Override
    public double getAverageLenght() {
        if (symbolToCodes == null) {
            throw new RuntimeException("Call code() first");
        }
        return symbolToCodes.stream()
                .mapToDouble(symbolToCode -> symbolToCode.getCode().length() * symbolToCode.getCount())
                .sum() / totalSymbolsAmount;
    }

    @Override
    public double getEntropy() {
        if (symbolToCodes == null) {
            throw new RuntimeException("Call code() first");
        }
        return -symbolToCodes.stream()
                .mapToDouble(symbolToCode -> ((double) symbolToCode.getCount()) / totalSymbolsAmount)
                .map(raiting -> raiting * log2(raiting))
                .sum();
    }

    public String getMessage() {
        if (message == null) {
            throw new RuntimeException("Call code() first");
        }
        return message;
    }

    public String getCodedMessage() {
        if (codedMessage == null) {
            throw new RuntimeException("Call code() first");
        }
        return codedMessage;
    }

    public int getTotalSymbolsAmount() {
        return totalSymbolsAmount;
    }

    @Override
    public void printCodes() {
        if (symbolToCodes == null) {
            throw new RuntimeException("Call code() first");
        }
        symbolToCodes.forEach(System.out::println);
    }

    @Override
    public void printVerbose() {
        printCodes();
        System.out.println("Message:\n" + message);
        System.out.println("Average lenght of symbols is " + getAverageLenght());
        System.out.println("Symbols amount is " + getTotalSymbolsAmount());
        System.out.println("Entropy is " + getEntropy());

        System.out.println("Original message is\n" + decode(gedCodedMessage()));
    }

    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    protected void fillCodedMessage() {
        Map<Character, String> map = symbolToCodes.stream()
                .collect(Collectors.toMap(SymbolToCode::getSymbol, SymbolToCode::getCode));

        codedMessage = message.codePoints()
                .mapToObj(c -> (char) c)
                .map(map::get).collect(Collectors.joining());
    }

}
