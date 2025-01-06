package cadastrosAlunos;

public class Curso {
    private String nome;
    private int quantidadeAlunos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeAlunos() {
        return quantidadeAlunos;
    }

    public void setQuantidadeAlunos(int quantidadeAlunos) {
        this.quantidadeAlunos = quantidadeAlunos;
    }

    public void exibirCurso()
    {
        System.out.println("Nome: " + nome);
        System.out.println("Alunos cadastrados: " + quantidadeAlunos);
    }
}
