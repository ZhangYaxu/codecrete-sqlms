package com.codecrete.sqlms.repository;

import com.codecrete.domain.model.User;
import com.codecrete.sqlms.configuration.IntegrationTestConfiguration;
import com.codecrete.sqlms.configuration.MockBeanConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ContextConfiguration(classes={
        IntegrationTestConfiguration.class,
        MockBeanConfiguration.class
})
public class UserRepositoryTest {

    private final static Logger LOG = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {}

    // FIX: database out of sync, awaiting codecrete-domain refresh
    @Test
    public void testGetByEmail() {

        User actual = (User) this.context.getBean("mockTim");
        this.entityManager.persist(actual);

        User expected = this.userRepository.getByEmail("tim.santaniello@codecrete.org");

        assertThat(expected.getEmail()).isEqualTo(actual.getEmail());
        assertThat(expected.getRoles()).isEqualTo(actual.getRoles());
        assertThat(expected.getSurname()).isEqualTo(actual.getSurname());
        assertThat(expected.getPassword()).isEqualTo(actual.getPassword());
        assertThat(expected.getModified()).isEqualTo(actual.getModified());
        assertThat(expected.getForename()).isEqualTo(actual.getForename());
        assertThat(expected.getCreated()).isEqualTo(actual.getCreated());
        assertThat(expected.getDeleted()).isEqualTo(actual.getDeleted());
        assertThat(expected.getUsername()).isEqualTo(actual.getUsername());
        assertThat(expected.getAuthorities()).isEqualTo(actual.getAuthorities());
    }
}
