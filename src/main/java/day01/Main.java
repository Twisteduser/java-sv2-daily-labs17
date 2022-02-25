package day01;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

        ActorsRepository actorsRepository = new ActorsRepository(dataSource);
        //actorsRepository.saveActor("Jack Done");

        System.out.println(actorsRepository.findActorsWithPrefix("jo"));

    }
}
