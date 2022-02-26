package day05;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActorsRepository {

    private DataSource dataSource;

    public ActorsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long saveActor(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("insert into actors(actor_name) values(?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getLong(1);
                }
                throw new IllegalStateException("Cannot insert and get id!");
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot update: " + name, sqle);
        }
    }

    public List<String> findActorsWithPrefix(String prefix){
        List<String> result = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("select actor_name from actors where actor_name like ?")) {
            stmt.setString(1,prefix+"%");

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    String actorName = rs.getString("actor_name");
                    result.add(actorName);
                }
            }catch (SQLException sqle){
                throw new IllegalStateException("RS", sqle);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot query", e);
        }
        return result;
    }

    public Optional<Actor> findActorByName(String name){
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from actors where actor_name=?")){
            stmt.setString(1,name);

            return processSelectStatement(stmt);

        }catch(SQLException sqle){
            throw new IllegalStateException("Cannot connect fo select by name!");
        }
    }
    private Optional<Actor> processSelectStatement(PreparedStatement statement) throws SQLException {
        try(ResultSet rs = statement.executeQuery()){
            if(rs.next()){
                return Optional.of(new Actor(rs.getLong("id"),rs.getString("actor_name")));
            }
            return Optional.empty();
        }
    }

}
