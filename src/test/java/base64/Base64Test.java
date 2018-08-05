package base64;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Base64;

public class Base64Test {

    @Test
    public void encodeAndDecode(){
        String s = "Hello World";

        String encoded = Base64.getEncoder().encodeToString(s.getBytes());

        System.out.println(encoded);

        byte[] decoded = Base64.getDecoder().decode(encoded);

        System.out.println(decoded);
        System.out.println(new String(decoded));

        Assertions.assertEquals(s, new String(decoded));
    }
}
