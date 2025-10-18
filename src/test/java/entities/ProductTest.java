package entities;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductDefaultConstructor() {
        // Given & When
        Product product = new Product();
        
        // Then
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(0);
        assertThat(product.getName()).isNull();
        assertThat(product.getPrice()).isEqualTo(0.0);
    }

    @Test
    public void testProductParameterizedConstructor() {
        // Given
        String name = "Test Product";
        double price = 99.99;
        
        // When
        Product product = new Product(name, price);
        
        // Then
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @Test
    public void testProductSettersAndGetters() {
        // Given
        Product product = new Product();
        int id = 1;
        String name = "Test Product";
        double price = 150.50;
        
        // When
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        
        // Then
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @Test
    public void testProductToString() {
        // Given
        Product product = new Product("Test Product", 99.99);
        product.setId(1);
        
        // When
        String result = product.toString();
        
        // Then
        assertThat(result).contains("Product{");
        assertThat(result).contains("id=1");
        assertThat(result).contains("name='Test Product'");
        assertThat(result).contains("price=99.99");
    }

    @Test
    public void testProductWithZeroPrice() {
        // Given & When
        Product product = new Product("Free Product", 0.0);
        
        // Then
        assertThat(product.getPrice()).isEqualTo(0.0);
        assertThat(product.getName()).isEqualTo("Free Product");
    }

    @Test
    public void testProductWithNegativePrice() {
        // Given & When
        Product product = new Product("Discounted Product", -10.0);
        
        // Then
        assertThat(product.getPrice()).isEqualTo(-10.0);
        assertThat(product.getName()).isEqualTo("Discounted Product");
    }

    @Test
    public void testProductWithEmptyName() {
        // Given & When
        Product product = new Product("", 50.0);
        
        // Then
        assertThat(product.getName()).isEmpty();
        assertThat(product.getPrice()).isEqualTo(50.0);
    }

    @Test
    public void testProductWithNullName() {
        // Given
        Product product = new Product();
        
        // When
        product.setName(null);
        
        // Then
        assertThat(product.getName()).isNull();
    }
}
