package cf.baradist.algorithms;

public interface Codable {
    void code(String s);

    String decode(String codedMessage);

    String gedCodedMessage();

    double getAverageLenght();

    double getEntropy();

    void printCodes();

    void printVerbose();
}
