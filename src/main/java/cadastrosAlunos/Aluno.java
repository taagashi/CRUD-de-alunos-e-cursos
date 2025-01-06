package cadastrosAlunos;

public class Aluno {
    private String nome;
    private int idade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void apresentar()
    {
        System.out.println("Meu nome é " + nome + " e eu tenho " + idade + " anos");
    }
}
