package cf.baradist.algorithms;

import org.junit.Before;
import org.junit.Test;

public class HuffmanTest extends AbstractTest {

    @Before
    public void setUp() throws Exception {
        codable = new Huffman();
    }

    @Test
    public void test() throws Exception {
        message = "Football World Championship";
        super.test();
    }
}
