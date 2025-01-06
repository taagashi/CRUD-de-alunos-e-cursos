package cadastrosAlunos;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Conector {
    private static Connection connection;

    public static Connection getCONNECTION() throws SQLException, IOException
    {
        List<String> dados = Files.readAllLines(Path.of("database.txt"));
        connection = DriverManager.getConnection(dados.get(0), dados.get(1), dados.get(2));

        return connection;
    }

}
