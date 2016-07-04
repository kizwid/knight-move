package sandkev.knight;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by kevin on 04/07/2016.
 */
public class KeyPadTest {

    private KeyPad keyPad;

    @Before
    public void setUp() throws Exception {
        keyPad = new KeyPad();
    }

    @Test
    public void possibleMoves(){

        for (Key key : keyPad.getActiveKeys()) {
            char[] chars = keyPad.charsFor(key.getValue());
            System.out.println("from " + key.getValue() + " could move to " + Arrays.toString(chars));
        }

        check('A',"LH");
        check('B',"MKI");
        check('C',"NLJF");
        check('D',"OMG");
        check('E',"NH");
        check('F',"1MC");
        check('G',"2ND");
        check('H',"31OEAK");
        check('I',"2BL");
        check('J',"3CM");
        check('K',"B2H");
        check('L',"CA3I");
        check('M',"DBJF");
        check('N',"ECG1");
        check('O',"DH2");
        check('1',"HFN");
        check('2',"IGOK");
        check('3',"JHL");

    }

    private void check(char value, String possibleNextPress) {
        assertArrayEquals(possibleNextPress.toCharArray(), keyPad.charsFor(value));
    }

}