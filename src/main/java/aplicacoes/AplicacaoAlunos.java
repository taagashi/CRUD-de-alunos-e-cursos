package aplicacoes;

import cadastrosAlunos.Aluno;
import cadastrosAlunos.AlunosCrud;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AplicacaoAlunos {
    private final Scanner SCANNER;
    private final AlunosCrud ALUNOS_CRUD;
    private Aluno aluno;

    public AplicacaoAlunos() throws SQLException, IOException
    {
        SCANNER = new Scanner(System.in);
        ALUNOS_CRUD = new AlunosCrud();
        aluno = new Aluno();
    }

    public void sessaoAlunos()
    {
        boolean online = true;
        while(online)
        {
            System.out.println("Bem vindo(a) a sessão de gerenciamento de alunos");
            System.out.println();

            System.out.println("1. Adicionar aluno");
            System.out.println("2. Buscar aluno por id");
            System.out.println("3. Listar todos os alunos");
            System.out.println("4. Atualizar nome de aluno");
            System.out.println("5. Atualizar idade de aluno");
            System.out.println("6. Deletar aluno");
            System.out.println("7. Sair");
            System.out.println();

            System.out.print("Insira aqui: ");
            String navegacao = SCANNER.next();

            try
            {
                switch (navegacao)
                {
                    case "1":
                        adicionarAluno(aluno);
                        break;
                        
                    case "2":
                        buscarAluno();
                        break;
                        
                    case "3":
                        listarTodosOsAlunos();
                        break;
                        
                    case "4":
                        atualizarNomeDeAluno();
                        break;
                        
                    case "5":
                        atualizarIdadeDeAluno();
                        break;
                        
                    case "6":
                        deletarAluno();
                        break;
                        
                    default:
                        online = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void adicionarAluno(Aluno aluno)
    {
        System.out.print("Insira o nome do aluno(a): ");
        aluno.setNome(SCANNER.next());

        System.out.print("Insira a idade de " + aluno.getNome() + ": ");
        try
        {
            aluno.setIdade(SCANNER.nextInt());
        }catch(InputMismatchException e)
        {
            System.out.println("Você precisa digitar uma idade válida");
            return;
        }
        
        ALUNOS_CRUD.adicionarAluno(aluno);
    }

    public void buscarAluno() throws SQLException, InputMismatchException
    {
        System.out.print("Insira o id do aluno que você deseja buscar: ");
        int id = SCANNER.nextInt();

        aluno = ALUNOS_CRUD.buscarAluno(id);
        
        if(aluno == null)
        {
            System.out.println("Não foi possivel encontrar aluno com ess id");
            return;
        }
        
        System.out.println("Aluno encontrado!");
        aluno.apresentar();
    }
    
    public void listarTodosOsAlunos() throws SQLException
    {
        if(!ALUNOS_CRUD.listarTodosOsAlunos())
        {
            System.out.println("Adicione alunos para poder lista-los");
        }
    }

    public void atualizarNomeDeAluno() throws SQLException
    {
        System.out.print("Insira o id do aluno que voce deseja atualizar o nome: ");
        int id;
        try
        {
            id = SCANNER.nextInt();
            SCANNER.nextLine();

            System.out.print("Agora digite o novo nome que será atualizado: ");
            String novoNome = SCANNER.nextLine();

            aluno = ALUNOS_CRUD.buscarAluno(id);
            if(!ALUNOS_CRUD.mudarNomeAluno(id, novoNome))
            {
                System.out.println("Certifique-se de que os dados colocados estão corretos");
                return;
            }

            System.out.println("Aluno(a) " + aluno.getNome() + " foi atualizado para " + novoNome);

        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void atualizarIdadeDeAluno() throws SQLException
    {
        try
        {
            System.out.print("Insira o id do aluno que voce deseja atualizar o nome: ");
            int id = SCANNER.nextInt();
            System.out.print("Agora digita a nova idade do aluno: ");
            int novaIdade = SCANNER.nextInt();
            
            aluno = ALUNOS_CRUD.buscarAluno(id);
            if(!ALUNOS_CRUD.mudarIdadeAluno(id, novaIdade))
            {
                System.out.println("Verifique se os dados foram digitados corretamente");
                return;
            }

            System.out.println("Idade de " + aluno.getNome() + " foi autalizado(a) para " + novaIdade + " anos");

        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void deletarAluno() throws SQLException
    {
        System.out.print("Insira o id do aluno que voce deseja retirar da lista: ");
        try
        {
            int id = SCANNER.nextInt();
            aluno = ALUNOS_CRUD.buscarAluno(id);
            if(!ALUNOS_CRUD.deletarAluno(id))
            {
                System.out.println("Não é possível deletar aluno porque esse id não existe");
                return;
            }
            System.out.println(aluno.getNome() + " foi retirado com sucesso");
            
        }catch(InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
