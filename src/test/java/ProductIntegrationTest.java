import dao.IDao;
import entities.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import util.TestHibernateConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestHibernateConfig.class)
@Transactional
public class ProductIntegrationTest {

    @Autowired
    private IDao<Product> productDao;

    private Product testProduct;

    @Before
    public void setUp() {
        testProduct = new Product("Integration Test Product", 199.99);
    }

    @After
    public void tearDown() {
        // Cleanup is handled by @Transactional and create-drop
    }

    @Test
    public void testCreateAndFindProduct() {
        // When
        boolean createResult = productDao.create(testProduct);
        
        // Then
        assertThat(createResult).isTrue();
        assertThat(testProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindById() {
        // Given
        productDao.create(testProduct);
        int productId = testProduct.getId();
        
        // When
        Product foundProduct = productDao.findById(productId);
        
        // Then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(productId);
        assertThat(foundProduct.getName()).isEqualTo("Integration Test Product");
        assertThat(foundProduct.getPrice()).isEqualTo(199.99);
    }

    @Test
    public void testFindAll() {
        // Given
        Product product1 = new Product("Product 1", 10.0);
        Product product2 = new Product("Product 2", 20.0);
        Product product3 = new Product("Product 3", 30.0);
        
        productDao.create(product1);
        productDao.create(product2);
        productDao.create(product3);
        
        // When
        List<Product> allProducts = productDao.findAll();
        
        // Then
        assertThat(allProducts).isNotNull();
        assertThat(allProducts).hasSize(3);
        assertThat(allProducts).extracting(Product::getName)
            .containsExactlyInAnyOrder("Product 1", "Product 2", "Product 3");
    }

    @Test
    public void testUpdateProduct() {
        // Given
        productDao.create(testProduct);
        int productId = testProduct.getId();
        
        // When
        testProduct.setName("Updated Product Name");
        testProduct.setPrice(299.99);
        boolean updateResult = productDao.update(testProduct);
        
        // Then
        assertThat(updateResult).isTrue();
        
        Product updatedProduct = productDao.findById(productId);
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product Name");
        assertThat(updatedProduct.getPrice()).isEqualTo(299.99);
    }

    @Test
    public void testDeleteProduct() {
        // Given
        productDao.create(testProduct);
        int productId = testProduct.getId();
        
        // When
        boolean deleteResult = productDao.delete(testProduct);
        
        // Then
        assertThat(deleteResult).isTrue();
        
        Product deletedProduct = productDao.findById(productId);
        assertThat(deletedProduct).isNull();
    }

    @Test
    public void testCompleteCrudOperations() {
        // Create
        Product newProduct = new Product("CRUD Test Product", 150.0);
        boolean createResult = productDao.create(newProduct);
        assertThat(createResult).isTrue();
        assertThat(newProduct.getId()).isGreaterThan(0);
        
        // Read
        Product foundProduct = productDao.findById(newProduct.getId());
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("CRUD Test Product");
        
        // Update
        foundProduct.setName("Updated CRUD Product");
        foundProduct.setPrice(200.0);
        boolean updateResult = productDao.update(foundProduct);
        assertThat(updateResult).isTrue();
        
        // Verify Update
        Product updatedProduct = productDao.findById(newProduct.getId());
        assertThat(updatedProduct.getName()).isEqualTo("Updated CRUD Product");
        assertThat(updatedProduct.getPrice()).isEqualTo(200.0);
        
        // Delete
        boolean deleteResult = productDao.delete(updatedProduct);
        assertThat(deleteResult).isTrue();
        
        // Verify Delete
        Product deletedProduct = productDao.findById(newProduct.getId());
        assertThat(deletedProduct).isNull();
    }

    @Test
    public void testFindByIdWithNonExistentId() {
        // When
        Product foundProduct = productDao.findById(99999);
        
        // Then
        assertThat(foundProduct).isNull();
    }

    @Test
    public void testFindAllWithEmptyDatabase() {
        // When
        List<Product> allProducts = productDao.findAll();
        
        // Then
        assertThat(allProducts).isNotNull();
        assertThat(allProducts).isEmpty();
    }

    @Test
    public void testProductWithSpecialCharacters() {
        // Given
        Product specialProduct = new Product("Product with émojis 🚀 and special chars: @#$%", 99.99);
        
        // When
        boolean createResult = productDao.create(specialProduct);
        
        // Then
        assertThat(createResult).isTrue();
        
        Product foundProduct = productDao.findById(specialProduct.getId());
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("Product with émojis 🚀 and special chars: @#$%");
    }

    @Test
    public void testProductWithZeroPrice() {
        // Given
        Product freeProduct = new Product("Free Product", 0.0);
        
        // When
        boolean createResult = productDao.create(freeProduct);
        
        // Then
        assertThat(createResult).isTrue();
        
        Product foundProduct = productDao.findById(freeProduct.getId());
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getPrice()).isEqualTo(0.0);
    }
}
