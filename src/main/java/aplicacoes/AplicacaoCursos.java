package aplicacoes;

import cadastrosAlunos.Curso;
import cadastrosAlunos.CursosCrud;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AplicacaoCursos {
    private final Scanner SCANNER;
    private final CursosCrud CURSOS_CRUD;
    private Curso curso;

    public AplicacaoCursos() throws SQLException, IOException
    {
        CURSOS_CRUD = new CursosCrud();
        SCANNER = new Scanner(System.in);
        curso = new Curso();
    }

    public void sessaoCursos()
    {
        boolean online = true;
        while(online)
        {
            System.out.println("Bem vindo(a) a sessão de gerenciamento de cursos");
            System.out.println("1. Adicionar curso");
            System.out.println("2. Buscar curso por id");
            System.out.println("3. Listar todos os cursos");
            System.out.println("4. Atualizar nome de curso");
            System.out.println("5. Deletar curso por id");

            String navegacao = SCANNER.next();

            try
            {
                switch(navegacao)
                {
                    case "1":
                        adicionarCurso();
                        break;

                    case "2":
                        buscarCursoPorId();
                        break;

                    case "3":
                        listarTodosOsCursos();
                        break;

                    case "4":
                        atualizarNomeDoCurso();
                        break;

                    case "5":
                        deletarCursoPorId();
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

    public void adicionarCurso() throws SQLException
    {
        System.out.print("Insira o nome do curso que voce deseja adicionar: ");
        String nome = SCANNER.nextLine();
        curso.setNome(nome);
        CURSOS_CRUD.adicionarCurso(curso);

        System.out.println(nome + " foi adicionado como um curso");
    }

    public void buscarCursoPorId() throws SQLException
    {
        try
        {
            System.out.print("Insira o id do curso que você deseja buscar: ");
            int id = SCANNER.nextInt();

            curso = CURSOS_CRUD.buscarCurso(id);

            if(curso == null)
            {
                System.out.println("Este curso não foi encontrado");
                return;
            }
            curso.exibirCurso();

        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void listarTodosOsCursos() throws SQLException
    {
        CURSOS_CRUD.listarTodosOsCursos();
    }

    public void atualizarNomeDoCurso() throws  SQLException
    {
        try
        {
            System.out.print("Insira o id do curso que você deseja atualizar o nome: ");
            int id = SCANNER.nextInt();
            SCANNER.nextLine();

            System.out.println("Insira o novo nome do curso: ");
            String novoNome = SCANNER.nextLine();

            curso = CURSOS_CRUD.buscarCurso(id);
            if(!CURSOS_CRUD.mudarNomeDoCurso(id, novoNome))
            {
                System.out.println("Parece que você inserio dados errados. Tente novamente");
                return;
            }

            System.out.println(curso.getNome() + " foi atualizado para " + novoNome);

        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void deletarCursoPorId() throws SQLException
    {
        try
        {
            System.out.print("Insira o id do curso que você deseja deletar: ");
            int id = SCANNER.nextInt();

            curso = CURSOS_CRUD.buscarCurso(id);
            if(!CURSOS_CRUD.deletarCurso(id))
            {
                System.out.println("Erro ao tentar deletar curso. Tente novamente");
                return;
            }

            System.out.println(curso.getNome() + " foi deletado com sucesso");
        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
