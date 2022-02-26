package day02;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot reach database!");
        }

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        ActorsRepository actorsRepository = new ActorsRepository(dataSource);

        MoviesRepository moviesRepository= new MoviesRepository(dataSource);
        moviesRepository.saveMovie("Titanic", LocalDate.of(1997,12,11));
        moviesRepository.saveMovie("Lord of the rings", LocalDate.of(2000,12,11));

        System.out.println(moviesRepository.findAllMovies());

    }
}
