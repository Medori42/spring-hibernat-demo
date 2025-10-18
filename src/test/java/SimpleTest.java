import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTest {
    
    @Test
    public void testBasicFunctionality() {
        // Test simple pour verifier que JUnit fonctionne
        assertTrue("JUnit fonctionne correctement", true);
        assertEquals("Les assertions fonctionnent", 2, 1 + 1);
    }
    
    @Test
    public void testProductCreation() {
        // Test de creation d'un produit sans Spring
        entities.Product product = new entities.Product("Test Product", 99.99);
        
        assertNotNull("Le produit ne doit pas etre null", product);
        assertEquals("Le nom doit etre correct", "Test Product", product.getName());
        assertEquals("Le prix doit etre correct", 99.99, product.getPrice(), 0.01);
    }
    
    @Test
    public void testProductSetters() {
        // Test des setters et getters
        entities.Product product = new entities.Product();
        
        product.setName("Modified Product");
        product.setPrice(150.0);
        
        assertEquals("Le nom modifie doit etre correct", "Modified Product", product.getName());
        assertEquals("Le prix modifie doit etre correct", 150.0, product.getPrice(), 0.01);
    }
}
