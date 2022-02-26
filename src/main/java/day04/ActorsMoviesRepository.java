package day04;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsMoviesRepository {
    private DataSource dataSource;

    public ActorsMoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertActorAndMovieId(long actorId, long movieId){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into actor_movies(actor_id, movie_id) values(?,?)")){
            stmt.setLong(1,actorId);
            stmt.setLong(2,movieId);
            stmt.executeUpdate();
        }catch (SQLException sqle){
            throw new IllegalStateException("Cannot inser row to actors-movies", sqle);
        }
    }


}
