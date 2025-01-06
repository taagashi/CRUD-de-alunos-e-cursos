package cadastrosAlunos;

import java.sql.PreparedStatement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AlunosCrud {
    private Connection connection;

    public AlunosCrud() throws SQLException, IOException
    {
        connection = Conector.getCONNECTION();
    }

    public void adicionarAluno(String nome, int idade)
    {
        String sql = """
                INSERT INTO alunos(nome, idade) values(?,?);
                """;
        try(PreparedStatement comando = connection.prepareStatement(sql))
        {
            comando.setString(1, nome);
            comando.setInt(2, idade);
            comando.executeUpdate();
            System.out.println(nome + " cadastrado com sucesso");
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        AlunosCrud alunosCrud;
        try
        {
            alunosCrud = new AlunosCrud();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

        alunosCrud.adicionarAluno("thaua", 18);

    }
}
