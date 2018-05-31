package cf.baradist.algorithms;

public class AbstractTest {
    protected Codable codable;
    protected String message;

    public void test() throws Exception {
        System.out.println(message);

        codable.code(message);
        codable.printVerbose();
        String codedMessage = codable.gedCodedMessage();
        System.out.println("Coded message is:\n" + codedMessage);
        System.out.println("Decoded message is\n" + codable.decode(codedMessage));
    }
}
