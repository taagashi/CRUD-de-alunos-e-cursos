package cadastrosAlunos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursosAlunosCrud {
    private final Connection CONNECTION;

    public CursosAlunosCrud() throws SQLException, IOException
    {
        CONNECTION = Conector.getCONNECTION();
    }

    public boolean adicionarCursoAluno(int idAluno, int idCurso) throws SQLException, IOException
    {
        if((idAluno <= 0) || (idCurso <= 0))
        {
            throw new IllegalArgumentException("Insira id's válidos");
        }

        String sql = """
                INSERT INTO cursos_alunos(idaluno, idcurso) values (?, ?);
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            AlunosCrud alunosCrud = new AlunosCrud();
            CursosCrud cursosCrud = new CursosCrud();

            Aluno aluno = alunosCrud.buscarAluno(idAluno);
            Curso curso = cursosCrud.buscarCurso(idCurso);

            if((aluno == null) || curso == null)
            {
                return false;
            }

            comando.setInt(1, idAluno);
            comando.setInt(2, idCurso);
            System.out.println("Aluno(a) " + aluno.getNome() + " foi adicionado para o curso de " + curso.getNome());
            comando.executeUpdate();
            return true;
        }
    }

    public boolean listarAlunosCursos() throws SQLException
    {
        String sql = """
                SELECT alunos.id AS idaluno, alunos.nome AS alunos, cursos.id AS idcurso, cursos.nome as Cursos from cursos_alunos
                INNER JOIN alunos ON cursos_alunos.idaluno = alunos.id
                INNER JOIN cursos ON cursos_alunos.idcurso = cursos.id
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            ResultSet result = comando.executeQuery();
            boolean temCursosAlunos = false;

            while(result.next())
            {
                temCursosAlunos = true;
                int idAluno = result.getInt("idaluno");
                int idCurso = result.getInt("idcurso");
                String nomeAluno = result.getString("alunos");
                String nomeCurso = result.getString("cursos");

                System.out.println("[" + idAluno + "] " + nomeAluno + " está cursando " + nomeCurso + " [" + idCurso + "]");
            }
            return temCursosAlunos;
        }

    }


    public boolean naoTemAlunoCursando(int id) throws SQLException
    {
        String sql = """
                SELECT DISTINCT alunos.nome from cursos_alunos INNER JOIN alunos on cursos_alunos.idaluno = alunos.id WHERE alunos.id = ?;
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setInt(1, id);
            ResultSet result = comando.executeQuery();
            return !result.next();
        }
    }

    public boolean naoTemCursoAdicionado(int id) throws SQLException
    {
        String sql = """
                SELECT DISTINCT cursos.nome from cursos_alunos INNER JOIN cursos on cursos_alunos.idcurso = cursos.id
                WHERE cursos.id = ?;
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setInt(1, id);
            ResultSet result = comando.executeQuery();
            return !result.next();
        }
    }

    public boolean listarCursosDeAluno(int id) throws SQLException
    {
        if(naoTemAlunoCursando(id))
        {
            return false;
        }

        String sql = """
                SELECT alunos.id as idaluno, alunos.nome as aluno, cursos.id as idcurso, cursos.nome as cursos from cursos_alunos
                INNER JOIN alunos on cursos_alunos.idaluno = alunos.id
                INNER JOIN cursos on cursos_alunos.idcurso = cursos.id
                WHERE alunos.id = ?;
                """;
        try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
        {
            comando.setInt(1, id);
            ResultSet result = comando.executeQuery();

            while(result.next())
            {
                int idAluno = result.getInt("idaluno");
                int idCurso = result.getInt("idcurso");
                String nomeAluno = result.getString("aluno");
                String nomeCurso = result.getString("cursos");

                System.out.println("[" + idAluno + "] " + nomeAluno + " esta cursando " + nomeCurso + " [" + idCurso + "]");
            }
            return true;
        }
    }

     public boolean trocarCusoDeAluno(int idAluno, int idCurso, int idNovoCurso) throws  SQLException, IOException
     {
         if((naoTemAlunoCursando(idAluno) || (naoTemCursoAdicionado(idCurso))))
         {
             return false;
         }

         CursosCrud cursosCrud = new CursosCrud();
         Curso curso = cursosCrud.buscarCurso(idNovoCurso);

         if(curso == null)
         {
             return false;
         }

         String sql = """
                 UPDATE cursos_alunos set idcurso = ?
                 WHERE idcurso = ? AND idaluno = ?;
                 """;
         try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
         {
             comando.setInt(1, idNovoCurso);
             comando.setInt(2, idCurso);
             comando.setInt(3, idAluno);

            return (comando.executeUpdate() != 0);
         }
     }

     public boolean trocarAlunoDeCurso(int idAluno, int idNovoAluno, int idCurso) throws SQLException, IOException
     {
       if((naoTemAlunoCursando(idAluno) || (naoTemCursoAdicionado(idCurso))))
       {
           return false;
       }
       AlunosCrud alunosCrud = new AlunosCrud();
       Aluno aluno = alunosCrud.buscarAluno(idNovoAluno);
       if(aluno == null)
       {
           return false;
       }

       String sql = """
               UPDATE cursos_alunos SET idaluno = ?
               WHERE idaluno = ? AND idcurso = ?;
               """;
       try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
       {
           comando.setInt(1, idNovoAluno);
           comando.setInt(2, idAluno);
           comando.setInt(3, idCurso);
           comando.executeUpdate();
           return true;
       }
     }

     public boolean retirarAlunoDeCuso(int idAluno, int idCurso) throws SQLException
     {
         if((naoTemAlunoCursando(idAluno) || (naoTemCursoAdicionado(idCurso))))
         {
             return  false;
         }

         String sql = """
                 DELETE FROM cursos_alunos WHERE idaluno = ? AND idcurso = ?;
                 """;
         try(PreparedStatement comando = CONNECTION.prepareStatement(sql))
         {
             comando.setInt(1, idAluno);
             comando.setInt(2, idCurso);
             comando.executeUpdate();
             return true;
         }
     }
}
