package cadastrosAlunos;

public class Curso {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void exibirCurso()
    {
        System.out.println("Nome: " + nome);
    }
}
