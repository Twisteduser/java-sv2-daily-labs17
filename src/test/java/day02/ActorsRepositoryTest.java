package day02;

import day01.ActorsRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ActorsRepositoryTest {

    ActorsRepository actorsRepository;
    Flyway flyway;

    @BeforeEach
    void init(){

        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors-test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("root");
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot reach DataBase!",sqle);
        }

        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        actorsRepository = new ActorsRepository(dataSource);
    }

    @Test
    void testInsert(){
        actorsRepository.saveActor("John Wick");
        flyway.clean();
    }

}