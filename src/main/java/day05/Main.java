package day05;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
//        flyway.clean();
        flyway.migrate();

        ActorsRepository actorsRepository = new ActorsRepository(dataSource);

        MoviesRepository moviesRepository = new MoviesRepository(dataSource);

        RatingsRepository ratingsRepository = new RatingsRepository(dataSource);

        ActorsMoviesRepository actorsMoviesRepository = new ActorsMoviesRepository(dataSource);

        ActorsMoviesService service = new ActorsMoviesService(actorsRepository, moviesRepository, actorsMoviesRepository);
        MoviesRatingService moviesRatingService = new MoviesRatingService(moviesRepository, ratingsRepository);



//        service.insertMovieWithActors("Titanic", LocalDate.of(1997, 11, 13), List.of("Leonardo DiCaprio", "Kate Winslet"));
//        service.insertMovieWithActors("Great Gatsby", LocalDate.of(2012, 10, 7), List.of("Leonardo DiCaprio", "Toby"));

//        moviesRatingService.addRatings("Titanic", 5,3,2,1,3);
        moviesRatingService.addRatings("Great Gatsby", 1,3,2);
    }
}
