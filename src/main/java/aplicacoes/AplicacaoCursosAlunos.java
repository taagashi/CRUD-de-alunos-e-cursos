package aplicacoes;

import cadastrosAlunos.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AplicacaoCursosAlunos {
    private final Scanner SCANNER;
    private final CursosAlunosCrud CURSOS_ALUNOS_CRUD;
    private Aluno aluno;
    private final AlunosCrud ALUNOS_CRUD;
    private Curso curso;
    private final CursosCrud CURSOS_CRUD;

    public AplicacaoCursosAlunos() throws SQLException, IOException
    {
        CURSOS_ALUNOS_CRUD = new CursosAlunosCrud();
        SCANNER = new Scanner(System.in);
        aluno = new Aluno();
        ALUNOS_CRUD = new AlunosCrud();
        curso = new Curso();
        CURSOS_CRUD = new CursosCrud();
    }

    public void sessaoCursosAlunos()
    {
        boolean online = true;
        while(online)
        {
            System.out.println("Bem vindo(a) a sessão de cadastro de alunos em cursos");
            System.out.println("1. Adicionar aluno em um curso");
            System.out.println("2. Listar todos os alunos que estão cursando");
            System.out.println("3. Listar todos os cursos de um aluno");
            System.out.println("4. Trocar curso de aluno");
            System.out.println("5. Trocar aluno de curso");
            System.out.println("6. Retirar aluno de seu curso");

            System.out.print("Insira aqui: ");
            String navegacao = SCANNER.next();

            try
            {
                switch(navegacao)
                {
                    case "1":
                      adicionarAlunoEmCurso();
                      break;

                    case "2":
                        listarTodosOsAlunosECursos();
                        break;

                    case "3":
                        listarCursosDeAluno();
                        break;

                    case "4":
                        trocarCursoDeAluno();
                        break;

                    case "5":
                        trocarAlunoDeCurso();
                        break;

                    case "6":
                       deletarAlunoCurso();
                       break;

                    default:
                        online = false;
                        break;
                }

            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void adicionarAlunoEmCurso() throws SQLException, IOException
    {
        try
        {
            System.out.print("Insira o id do aluno: ");
            int idAluno = SCANNER.nextInt();

            System.out.println("Insira o id do curso que o aluno ira cursar: ");
            int idCurso = SCANNER.nextInt();

            if(!CURSOS_ALUNOS_CRUD.adicionarCursoAluno(idAluno, idCurso))
            {
                System.out.println("Você precisa digitar id's que sejam válidos");
            }

        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void listarTodosOsAlunosECursos() throws SQLException
    {
        if(!CURSOS_ALUNOS_CRUD.listarAlunosCursos())
        {
            System.out.println("Você precisa adicionar algum aluno em pelo menos um curso para poder lista-los");
        }
    }

    public void listarCursosDeAluno() throws SQLException
    {
        try
        {
            System.out.print("Insira o id do aluno para ver seus cursos");
            int id = SCANNER.nextInt();

            if(!CURSOS_ALUNOS_CRUD.listarCursosDeAluno(id))
            {
                System.out.println("Esse aluno não está cursando nenhum curso");
            }
        }catch (InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void trocarCursoDeAluno() throws SQLException, IOException
    {
        try
        {
            System.out.print("Insira o id do aluno: ");
            int idAluno = SCANNER.nextInt();

            System.out.print("Insira o id do cuso que será trocado: ");
            int idCurso = SCANNER.nextInt();

            System.out.print("Digite o id do novo curso: ");
            int idNovoCurso = SCANNER.nextInt();

            if(!CURSOS_ALUNOS_CRUD.trocarCusoDeAluno(idAluno, idCurso, idNovoCurso))
            {
                System.out.println("Parece que você inserio dados errados. Tente novamente");
                return;
            }

            aluno = ALUNOS_CRUD.buscarAluno(idAluno);
            curso = CURSOS_CRUD.buscarCurso(idNovoCurso);

            System.out.println("Agora " + aluno.getNome() + " cursa " + curso.getNome());

        }catch (InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void trocarAlunoDeCurso() throws SQLException, IOException
    {
        try
        {
            System.out.print("Insira o id do aluno que será trocado: ");
            int idAluno = SCANNER.nextInt();

            System.out.print("Insira o id do aluno que vai substituir o antigo: ");
            int idNovoAluno = SCANNER.nextInt();

            System.out.println("Digite o id do curso que este aluno esta cursando: ");
            int idCurso = SCANNER.nextInt();

            aluno = ALUNOS_CRUD.buscarAluno(idAluno);
            curso = CURSOS_CRUD.buscarCurso(idCurso);
            Aluno novoAluno = ALUNOS_CRUD.buscarAluno(idNovoAluno);

            if(!CURSOS_ALUNOS_CRUD.trocarAlunoDeCurso(idAluno, idNovoAluno, idCurso))
            {
                System.out.println("Dados de aluno ou curso estão incorretos");
                return;
            }
            System.out.println(aluno.getNome() + " foi substituido por " + novoAluno.getNome() + " no curso " + curso.getNome());

        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage() );
        }
    }

    public void deletarAlunoCurso() throws SQLException
    {
        try
        {
            System.out.print("Insira o id do aluno que vai ser retirado: ");
            int idAluno = SCANNER.nextInt();

            System.out.print("Insira o id do Curso que este aluno está cursando: ");
            int idCurso = SCANNER.nextInt();

            if(!CURSOS_ALUNOS_CRUD.retirarAlunoDeCuso(idAluno, idCurso))
            {
                System.out.println("Você precisa digitar um id de aluno ou curso válidos");
                return;
            }

            aluno = ALUNOS_CRUD.buscarAluno(idAluno);
            curso = CURSOS_CRUD.buscarCurso(idCurso);

            System.out.println(aluno.getNome() + " foi retirado do curso " + curso.getNome());

        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
