package cf.baradist.algorithms;

import cf.baradist.SymbolToCode;

import java.util.List;

public abstract class AbstractCoding implements Codable {
    protected List<SymbolToCode> symbolToCodes;
    protected int totalSymbolsAmount;

    @Override
    public abstract List<SymbolToCode> getCodes(String s);

    @Override
    public double getAverageLenght() {
        if (symbolToCodes == null) {
            throw new RuntimeException("Call getCodes first");
        }
        return symbolToCodes.stream()
                .mapToDouble(symbolToCode -> symbolToCode.getCode().length())
                .average()
                .orElse(Double.NaN);
    }

    @Override
    public double getEntropy() {
        if (symbolToCodes == null) {
            throw new RuntimeException("Call getCodes first");
        }
        return symbolToCodes.stream()
                .mapToDouble(symbolToCode -> ((double) symbolToCode.getCount()) / totalSymbolsAmount)
                .map(raiting -> raiting * log2(raiting))
                .sum();
    }

    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }
}
