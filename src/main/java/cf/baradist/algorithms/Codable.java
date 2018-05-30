package cf.baradist.algorithms;

import cf.baradist.SymbolToCode;

import java.util.List;

public interface Codable {
    List<SymbolToCode> getCodes(String s);

    double getAverageLenght();

    double getEntropy();
}
