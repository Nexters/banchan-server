package base64;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.util.Base64;

public class Base64Test {

    @Test
    public void encodeAndDecode(){
        String s = "Hello World!";
        byte[] encoded = Base64.getEncoder().encode(s.getBytes());
        byte[] decoded = Base64.getDecoder().decode(encoded);

        Assertions.assertEquals(s, new String(decoded));
    }
}
