package sandkev.knight;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by kevin on 4/07/2016.
 */
public class ControllerTest {

    @Test
    public void possibleMoves(){

        KeyPad keyPad = new KeyPad();

        for (Key key : keyPad.getActiveKeys()) {
            char[] chars = keyPad.charsFor(key.getValue());
            System.out.println("from " + key.getValue() + " = " + Arrays.toString(chars));
        }

        assertEquals(1, 1);
    }





}
