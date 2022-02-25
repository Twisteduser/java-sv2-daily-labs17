package day01;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorsRepository {

    private DataSource dataSource;

    public ActorsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveActor(String name){
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into actors(actor_name) values (?)")) {
            stmt.setString(1,name);
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new IllegalStateException("Cannot update: "+ name, e);
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
}
