package aplicacoes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class AplicacaoGeral {
    public static void run()
    {
        iniciarPrograma();
    }

    public static void iniciarPrograma()
    {
        try(final Scanner SCANNER = new Scanner(System.in))
        {
            AplicacaoAlunos aplicacaoAlunos = new AplicacaoAlunos();
            AplicacaoCursos aplicacaoCursos = new AplicacaoCursos();
            AplicacaoCursosAlunos aplicacaoCursosAlunos = new AplicacaoCursosAlunos();

            boolean online = true;
            while(online)
            {
                System.out.println("Bem vindo(a) ao programa Meu ALuno. Insira o que deseja fazer:");
                System.out.println("1. Sessão de alunos");
                System.out.println("2. Sessão de cusos");
                System.out.println("3. Cadastrar aluno em cursos");

                System.out.print("Insira aqui: ");
                String navegacao = SCANNER.next();

                switch (navegacao)
                {
                    case "1":
                        aplicacaoAlunos.sessaoAlunos();
                        break;

                    case "2":
                        aplicacaoCursos.sessaoCursos();
                        break;

                    case "3":
                        aplicacaoCursosAlunos.sessaoCursosAlunos();
                        break;

                    default:
                        online = false;
                        break;
                }
            }
            System.out.println("Encerrando programa...");

        }catch(SQLException e)
        {
            System.out.println("Erro grave na operação de acessar banco de dados: " + e.getMessage());
        }catch(IOException e)
        {
            System.out.println("Erro grave ao tentar acessar informações do banco de dados " + e.getMessage());
        }
    }
}
