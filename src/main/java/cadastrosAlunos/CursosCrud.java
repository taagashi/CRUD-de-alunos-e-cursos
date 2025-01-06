package cadastrosAlunos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;

public class CursosCrud {
    private final Connection CONNECTION;

    public CursosCrud() throws SQLException, IOException
    {
        CONNECTION = Conector.getCONNECTION();
    }

    public void adicionarCurso(Curso curso) throws SQLException
    {
        String sql = """
                INSERT INTO cursos(nome) values(?);
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setString(1, curso.getNome());
            int indiceLinha = comando.executeUpdate();

            if(indiceLinha == 0)
            {
                System.out.println("Insira dados validos");
                return;
            }

            System.out.println("Curso de " + curso.getNome() + " foi adicionado com sucesso");
        }
    }

    public Curso buscarCurso(int id) throws SQLException
    {
        String sql = """
                SELECT * FROM cursos WHERE id = ?;
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setInt(1, id);
            ResultSet result = comando.executeQuery();

            if(!result.next())
            {
                return  null;
            }
            Curso curso = new Curso();
            curso.setNome(result.getString("nome"));

            return curso;
        }
    }

    public void listarTodosOsCursos() throws SQLException
    {
        String sql = """
                SELECT * FROM cursos;
                """;
        try(Statement comando = CONNECTION.createStatement())
        {
            ResultSet result = comando.executeQuery(sql);
            boolean temCursos = false;

            while(result.next())
            {
                int id = result.getInt("id");
                String nome = result.getString("nome");
                System.out.println("Id: " + id + " | nome: " + nome);
                temCursos = true;
            }

            if(!temCursos)
            {
                System.out.println("Voce precisa adicionar cursos para poder vê-los");
            }
        }
    }

    public boolean deletarCurso(int id) throws SQLException
    {
        String sql = """
                DELETE FROM cursos WHERE id = ?;
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setInt(1, id);
            int indiceLinha = comando.executeUpdate();

            return (indiceLinha != 0);
        }
    }

    public boolean mudarNomeDoCurso(int id, String novoNome) throws SQLException
    {
        String sql = """
                UPDATE cursos SET nome = ? WHERE id = ?;
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setString(1, novoNome);
            comando.setInt(2, id);
            int indiceLinha = comando.executeUpdate();

            return (indiceLinha != 0);
        }
    }

}
