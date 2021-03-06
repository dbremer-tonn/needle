package de.akquinet.jbosscc.needle.db;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import de.akquinet.jbosscc.needle.db.operation.AbstractDBOperation;
import de.akquinet.jbosscc.needle.db.operation.ExecuteScriptOperation;
import de.akquinet.jbosscc.needle.db.operation.JdbcConfiguration;
import de.akquinet.jbosscc.needle.db.operation.hsql.HSQLDeleteOperation;
import de.akquinet.jbosscc.needle.reflection.ReflectionUtil;

public class DatabaseTestcaseConfigurationTest {

    @Test(expected = RuntimeException.class)
    public void testEntityManager_Close() throws Exception {
        DatabaseTestcaseConfiguration databaseRuleConfiguration = new DatabaseTestcaseConfiguration("TestDataModel");
        EntityManager entityManager = databaseRuleConfiguration.getEntityManager();
        assertNotNull(entityManager);

        entityManager.close();
    }

    @Test
    public void testGetEntityManagerFactoryProperties() throws Exception {
        DatabaseTestcaseConfiguration databaseRuleConfiguration = new DatabaseTestcaseConfiguration();

        JdbcConfiguration jdbcConfiguration = (JdbcConfiguration) ReflectionUtil.invokeMethod(
                databaseRuleConfiguration, "getEntityManagerFactoryProperties");

        assertNotNull(jdbcConfiguration);
    }

    @Test
    public void testLookupDBOperationClassClass_HSQLDeleteOperation() throws Exception {
        Class<? extends AbstractDBOperation> dbDialectClass = DatabaseTestcaseConfiguration
                .lookupDBOperationClass(HSQLDeleteOperation.class.getName());
        assertEquals(HSQLDeleteOperation.class, dbDialectClass);
    }

    @Test
    public void testLookupDBOperationClassClass_UnknownClass() throws Exception {
        Class<? extends AbstractDBOperation> dbDialectClass = DatabaseTestcaseConfiguration
                .lookupDBOperationClass("unknowm");
        assertNull(dbDialectClass);
    }

    @Test
    public void testLookupDBOperationClassClass_Null() throws Exception {
        Class<? extends AbstractDBOperation> dbDialectClass = DatabaseTestcaseConfiguration
                .lookupDBOperationClass(null);
        assertNull(dbDialectClass);
    }

    @Test
    public void testCreateDBOperation() throws Exception {
        DatabaseTestcaseConfiguration configuration = new DatabaseTestcaseConfiguration();
        AbstractDBOperation operation = configuration.createDBOperation(ExecuteScriptOperation.class);

        assertTrue(operation instanceof ExecuteScriptOperation);
    }

    @Test
    public void testCreateDBOperation_Null() throws Exception {
        DatabaseTestcaseConfiguration configuration = new DatabaseTestcaseConfiguration();
        assertNull(configuration.createDBOperation(null));
    }
}
