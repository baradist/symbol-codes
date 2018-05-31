package cf.baradist.algorithms;

import org.junit.Before;
import org.junit.Test;

public class ShannonFanoTest extends AbstractTest {

    @Before
    public void setUp() throws Exception {
        codable = new ShannonFano();
    }

    @Test
    public void test() throws Exception {
        message = "Football World Championship";
        super.test();
    }
}
