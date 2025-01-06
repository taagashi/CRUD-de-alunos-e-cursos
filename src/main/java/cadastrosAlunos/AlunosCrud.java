package cadastrosAlunos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.io.IOException;
import java.sql.SQLException;

public class AlunosCrud {
    private final Connection CONNECTION;

    public AlunosCrud() throws SQLException, IOException
    {
        CONNECTION = Conector.getCONNECTION();
    }

    public void adicionarAluno(Aluno aluno)
    {
        if(aluno.getIdade() <= 0)
        {
            throw new IllegalArgumentException("Insira uma idade valida");
        }

        String sql = """
                INSERT INTO alunos(nome, idade) values(?,?);
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setString(1, aluno.getNome());
            comando.setInt(2, aluno.getIdade());
            comando.executeUpdate();
            System.out.println(aluno.getNome() + " cadastrado com sucesso");
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public Aluno buscarAluno(int id) throws SQLException
    {
        String sql = """
                SELECT * FROM alunos WHERE id = ?;
                """;

        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setInt(1, id);
            ResultSet result = comando.executeQuery();
            if(!result.next())
            {
                return null;
            }

            Aluno aluno = new Aluno();
            aluno.setNome(result.getString("nome"));
            aluno.setIdade(result.getInt("idade"));

            return aluno;
        }
    }

    public boolean listarTodosOsAlunos() throws SQLException
    {
        String sql = """
                SELECT * FROM alunos;
                """;
        try(Statement comando = CONNECTION.createStatement())
        {
            ResultSet result = comando.executeQuery(sql);
            boolean temAlunos = false;

            while(result.next())
            {
                int id = result.getInt("id");
                String nome = result.getString("nome");
                int idade = result.getInt("idade");

                System.out.println("Id: " + id + " | nome: " + nome + " | idade: " + idade);
                temAlunos = true;
            }

            return temAlunos;
        }
    }

    public boolean deletarAluno(int id) throws SQLException
    {
        String sql = """
                DELETE FROM alunos WHERE id = ?;
                """;

        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setInt(1, id);
            int indiceLinha = comando.executeUpdate();

            return (indiceLinha != 0);
        }
    }

    public boolean mudarNomeAluno(int id, String novoNome) throws SQLException
    {
        String sql = """
                UPDATE alunos SET nome = ? WHERE id = ?;
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
