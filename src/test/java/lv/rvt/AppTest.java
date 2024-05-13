package lv.rvt;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertFalse;

import rvt.CSVManager;
import rvt.Product;
import rvt.Validator;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class AppTest 
{
    @Test
    public void testValidateName() {
        assertTrue(Validator.validateName("Gvido"));
        assertFalse(Validator.validateName("john"));
        assertFalse(Validator.validateName("JOHN"));
        assertFalse(Validator.validateName("John1"));
    }
    
    @Test
    public void testSortProductsByName() {
        Product product2 = new Product("Banana");
        Product product1 = new Product("Apple");
        Product product3 = new Product("Cherry");
        List<Product> list = Arrays.asList(product3, product1, product2);
        List<Product> sortedList = Arrays.asList(product1, product2, product3);
        assertEquals(sortedList, CSVManager.getProductsSortedAtoZ(list));
}

    @Test
    public void sortProductsByLowerPrice() {
        Product product2 = new Product(2, "Banana", "Yellow banana", 0.5);
        Product product1 = new Product(1, "Apple", "Red apple", 1.0);
        Product product3 = new Product(3, "Cherry", "Red cherry", 2.0);
        List<Product> list = Arrays.asList(product3, product1, product2);
        List<Product> sortedList = Arrays.asList(product2, product1, product3);
        assertEquals(sortedList, CSVManager.sortProductsByLowerPrice(list));
    }

    @Test
    public void sortProductsByHigherPrice() {
        Product product3 = new Product(3, "Cherry", "Red cherry", 2.0);
        Product product2 = new Product(2, "Banana", "Yellow banana", 0.5);
        Product product1 = new Product(1, "Apple", "Red apple", 1.0);
        List<Product> list = Arrays.asList(product1, product2, product3);
        List<Product> sortedList = Arrays.asList(product3, product1, product2);
        assertEquals(sortedList, CSVManager.sortProductsByHigherPrice(list));
    } 

    @Test
    public void testValidateEmail() {
        assertTrue(Validator.validateEmail("gn00334@rvt.lv"));
        assertFalse(Validator.validateEmail("john123@r."));
        assertFalse(Validator.validateEmail("#asdaws@r.t"));
        assertFalse(Validator.validateEmail("john@rvt"));
    }
}