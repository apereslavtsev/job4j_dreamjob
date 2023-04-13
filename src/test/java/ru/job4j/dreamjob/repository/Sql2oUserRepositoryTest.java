package ru.job4j.dreamjob.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

class Sql2oUserRepositoryTest {
    
    private static Sql2oUserRepository sql2oUserRepository;
    
    private static Sql2o sql2o; 

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepository.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
                
    }
    
    @AfterEach
    public void clearUsers() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("TRUNCATE TABLE users");
            query.executeUpdate();
        }
    }
    
    @Test
    public void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User("name", "email@example", "485"));
        var savedUser = sql2oUserRepository.findByEmailAndPassword("email@example", "485");
        assertThat(savedUser.get()).usingRecursiveComparison().isEqualTo(user.get());
    }
    
    @Test
    public void whenSaveDuplicateEmailThenExaption() {
        sql2oUserRepository.save(new User("name", "email@example", "485"));
        
        assertThatThrownBy(() -> 
            sql2oUserRepository.save(new User("name", "email@example", "485")))
        .isInstanceOf(Sql2oException.class)
        .message()
        .isNotEmpty();
    } 

}
