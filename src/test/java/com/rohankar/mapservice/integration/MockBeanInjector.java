package com.rohankar.mapservice.integration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import com.rohankar.mapservice.gateway.AuthserverGateway;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Sagar Rohankar
 */
public class MockBeanInjector implements BeanPostProcessor {

    private final static Logger LOG = LoggerFactory.getLogger(MockBeanInjector.class);

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            LOG.info("Mocking DataSource instance: " + bean);
            final EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
            final EmbeddedDatabase embeddedDatabase =
                embeddedDatabaseBuilder.addScript("schema.sql").addScript("data.sql").build();
            return embeddedDatabase;
            // else
            // return new ClassPathXmlApplicationContext("applicationContext-test.xml").getBean("dataSource");
        }
        if (bean instanceof AuthserverGateway) {
            LOG.info("Mocking AuthserverGateway instance: " + bean);
            final AuthserverGateway mockAsGateway = mock(AuthserverGateway.class);
            final ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            when(mockAsGateway.callAuthorizeService("12345")).thenReturn(response);
            return mockAsGateway;
        } else {
            return bean;
        }
    }
}
