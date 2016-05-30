package camllight.algebras.tests.decls;

import camllight.CamlLight;
import camllight.algebras.tests.TestStub;
import org.junit.Test;

import static org.junit.Assert.*;

public class GlobalAlgTest extends TestStub {

    @Test
    public void testDeclNewEnumType() throws Exception {
        CamlLight.eval("type suit = Heart | Diamond | Club | Spade;; Club;;");
        assertEquals("Variant(Tag(Club),NULL)", out.toString());
    }
}