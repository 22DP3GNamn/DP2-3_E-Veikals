package lv.rvt;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import rvt.Validator;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testValidateName() {
        assertTrue(Validator.validateName("Gvido"));
        assertFalse(Validator.validateName("john"));
        assertFalse(Validator.validateName("JOHN"));
        assertFalse(Validator.validateName("John1"));
    }

    // Add the rest of the tests here...
}