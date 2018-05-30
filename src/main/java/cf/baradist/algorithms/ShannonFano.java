package cf.baradist.algorithms;

import cf.baradist.SymbolToCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShannonFano implements Codable {
    private List<SymbolToCode> symbolToCodes;

    @Override
    public List<SymbolToCode> getCodes(String s) {
        char[] chars = s.toCharArray();
        List<Character> list = new ArrayList<>(s.length());
        for (char c : chars) {
            list.add(c);
        }

        SymbolToCode[] array = list.stream()
                .collect(Collectors.groupingBy(character -> character, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new SymbolToCode(entry.getKey(), entry.getValue(), ""))
                .sorted((o1, o2) -> (int) (o2.getCount() - o1.getCount())).toArray(SymbolToCode[]::new);

        fillCodes(array, 0, array.length - 1, "");

        symbolToCodes = Arrays.asList(array);
        return symbolToCodes;
    }

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

    private void fillCodes(SymbolToCode[] array, int start, int end, String partOfCode) {
        long sum = 0;
        for (int i = start; i <= end; i++) {
            array[i] = array[i].addCodePart(partOfCode);
            sum += array[i].getCount();
        }
        if (start >= end) {
            return;
        }
        long half = 0;
        int middle;
        for (middle = start; middle < end; middle++) {
            half += array[middle].getCount();
            if (half * 2 >= sum) {
                break;
            }
        }

        fillCodes(array, start, middle, "0");
        fillCodes(array, middle + 1, end, "1");
    }
}
