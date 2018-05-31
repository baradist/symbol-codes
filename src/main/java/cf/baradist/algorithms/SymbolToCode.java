package cf.baradist.algorithms;

public final class SymbolToCode {
    private final char symbol;
    private final long count;
    private final String code;

    public SymbolToCode(char symbol, long count, String code) {
        this.symbol = symbol;
        this.count = count;
        this.code = code;
    }

    public SymbolToCode addCodePart(String codePart) {
        return new SymbolToCode(this.symbol, this.count, this.code + codePart);
    }

    public static int compareCounts(SymbolToCode o1, SymbolToCode o2) {
        if (o1.getCount() == o2.getCount()) {
            return 0;
        }
        return (o1.getCount() - o2.getCount()) > 0 ? -1 : 1;
    }

    public char getSymbol() {
        return symbol;
    }

    public long getCount() {
        return count;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "\'" + symbol + "\'" +
                "\t" + count +
                "\t" + code;
    }
}
