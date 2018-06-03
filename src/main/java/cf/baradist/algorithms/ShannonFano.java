package cf.baradist.algorithms;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ShannonFano extends AbstractCoding {

    @Override
    public void code(String s) {
        message = s;
        totalSymbolsAmount = s.length();

        SymbolToCode[] array = s.codePoints()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(character -> character, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new SymbolToCode(entry.getKey(), entry.getValue(), ""))
                .sorted((o1, o2) -> (int) (o2.getCount() - o1.getCount())).toArray(SymbolToCode[]::new);

        fillCodes(array, 0, array.length - 1, "");

        symbolToCodes = Arrays.asList(array);

        fillCodedMessage();
    }

    @Override
    public String decode(String codedString) {
        Map<String, Character> map = symbolToCodes.stream()
                .collect(Collectors.toMap(SymbolToCode::getCode, SymbolToCode::getSymbol));
        StringBuilder sb = new StringBuilder();
        int caret = 1;
        int codeStart = 0;
        while (caret <= codedString.length()) {
            String codeCandidate = codedString.substring(codeStart, caret++);
            if (map.containsKey(codeCandidate)) {
                sb.append(map.get(codeCandidate));
                codeStart = caret - 1;
            }
        }
        return sb.toString();
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
        long moreThenAHalf = 0;
        long lessThenAHalf = 0;
        int middle;
        for (middle = start; middle < end; middle++) {
            lessThenAHalf = moreThenAHalf;
            moreThenAHalf += array[middle].getCount();
            if (moreThenAHalf * 2 >= sum) {
                break;
            }
        }
        if (moreThenAHalf * 2 - sum > sum - lessThenAHalf * 2) {
            middle--;
        }

        fillCodes(array, start, middle, "0");
        fillCodes(array, middle + 1, end, "1");
    }
}
