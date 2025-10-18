package util;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestHibernateConfig.class)
public class HibernateConfigTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTransactionManager transactionManager;

    @Test
    public void testDataSourceConfiguration() {
        // Then
        assertThat(dataSource).isNotNull();
        assertThat(dataSource).isInstanceOf(org.springframework.jdbc.datasource.DriverManagerDataSource.class);
    }

    @Test
    public void testSessionFactoryConfiguration() {
        // Then
        assertThat(sessionFactory).isNotNull();
        assertThat(sessionFactory.isClosed()).isFalse();
    }

    @Test
    public void testTransactionManagerConfiguration() {
        // Then
        assertThat(transactionManager).isNotNull();
        assertThat(transactionManager.getSessionFactory()).isNotNull();
    }

    @Test
    public void testSessionFactoryConnection() {
        // When
        boolean isConnected = !sessionFactory.isClosed();
        
        // Then
        assertThat(isConnected).isTrue();
    }

    @Test
    public void testTransactionManagerSessionFactory() {
        // When
        SessionFactory txSessionFactory = transactionManager.getSessionFactory();
        
        // Then
        assertThat(txSessionFactory).isNotNull();
        assertThat(txSessionFactory).isEqualTo(sessionFactory);
    }
}
