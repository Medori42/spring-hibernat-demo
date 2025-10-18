package metier;

import dao.IDao;
import entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Product> query;

    @InjectMocks
    private ProductDaoImpl productDao;

    private Product testProduct;

    @Before
    public void setUp() {
        testProduct = new Product("Test Product", 99.99);
        testProduct.setId(1);
        
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void testCreate() {
        // Given
        Product newProduct = new Product("New Product", 50.0);
        
        // When
        boolean result = productDao.create(newProduct);
        
        // Then
        assertThat(result).isTrue();
        verify(session).save(newProduct);
    }

    @Test
    public void testDelete() {
        // Given
        Product productToDelete = new Product("Product to Delete", 25.0);
        
        // When
        boolean result = productDao.delete(productToDelete);
        
        // Then
        assertThat(result).isTrue();
        verify(session).delete(productToDelete);
    }

    @Test
    public void testUpdate() {
        // Given
        Product productToUpdate = new Product("Updated Product", 75.0);
        productToUpdate.setId(1);
        
        // When
        boolean result = productDao.update(productToUpdate);
        
        // Then
        assertThat(result).isTrue();
        verify(session).update(productToUpdate);
    }

    @Test
    public void testFindById() {
        // Given
        int productId = 1;
        when(session.get(Product.class, productId)).thenReturn(testProduct);
        
        // When
        Product result = productDao.findById(productId);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testProduct);
        verify(session).get(Product.class, productId);
    }

    @Test
    public void testFindByIdNotFound() {
        // Given
        int productId = 999;
        when(session.get(Product.class, productId)).thenReturn(null);
        
        // When
        Product result = productDao.findById(productId);
        
        // Then
        assertThat(result).isNull();
        verify(session).get(Product.class, productId);
    }

    @Test
    public void testFindAll() {
        // Given
        List<Product> expectedProducts = Arrays.asList(
            new Product("Product 1", 10.0),
            new Product("Product 2", 20.0),
            new Product("Product 3", 30.0)
        );
        
        when(session.createQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.list()).thenReturn(expectedProducts);
        
        // When
        List<Product> result = productDao.findAll();
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(expectedProducts);
        verify(session).createQuery("from Product", Product.class);
        verify(query).list();
    }

    @Test
    public void testFindAllEmpty() {
        // Given
        List<Product> emptyList = Arrays.asList();
        
        when(session.createQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.list()).thenReturn(emptyList);
        
        // When
        List<Product> result = productDao.findAll();
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(session).createQuery("from Product", Product.class);
        verify(query).list();
    }

    @Test
    public void testCreateWithNullProduct() {
        // Given
        Product nullProduct = null;
        
        // When & Then
        assertThatThrownBy(() -> productDao.create(nullProduct))
            .isInstanceOf(Exception.class);
    }

    @Test
    public void testUpdateWithNullProduct() {
        // Given
        Product nullProduct = null;
        
        // When & Then
        assertThatThrownBy(() -> productDao.update(nullProduct))
            .isInstanceOf(Exception.class);
    }

    @Test
    public void testDeleteWithNullProduct() {
        // Given
        Product nullProduct = null;
        
        // When & Then
        assertThatThrownBy(() -> productDao.delete(nullProduct))
            .isInstanceOf(Exception.class);
    }
}
