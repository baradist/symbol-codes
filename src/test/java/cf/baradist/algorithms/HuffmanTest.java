package cf.baradist.algorithms;

import cf.baradist.SymbolToCode;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HuffmanTest {
    private Codable codable;

    @Before
    public void setUp() throws Exception {
        codable = new Huffman();
    }

    @Test
    public void test() throws Exception {
        String s = "Football World Championship";
        System.out.println(s);

        final List<SymbolToCode> codes = codable.getCodes(s);
        codes.forEach(System.out::println);
        System.out.println("Average lenght of symbols is " + codable.getAverageLenght());
        System.out.println("Entropy is " + codable.getEntropy());

        assertThat(codes.size(), is(17));
    }
}
