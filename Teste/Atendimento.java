public class Atendimento {
    private int codigo;
    private String nomeAnimal;
    private String especie;
    private String nomeTutor;
    private String data;
    private String status;
    private String observacao;
    private Procedimento procedimento;
    private Sala sala;

    public Atendimento(int codigo, String nomeAnimal, String especie, String nomeTutor, String data, String status, String observacao, Procedimento procedimento) {
        this.codigo = codigo;
        this.nomeAnimal = nomeAnimal;
        this.especie = especie;
        this.nomeTutor = nomeTutor;
        this.data = data;
        this.status = status;
        this.observacao = observacao;
        this.procedimento = procedimento;
    }

    public int getCodigo() { return codigo; }
    public String getNomeAnimal() { return nomeAnimal; }
    public String getEspecie() { return especie; }
    public String getNomeTutor() { return nomeTutor; }
    public String getData() { return data; }
    public String getStatus() { return status; }
    public String getObservacao() { return observacao; }
    public Procedimento getProcedimento() { return procedimento; }
    public Sala getSala() { return sala; }

    public void setStatus(String status) { this.status = status; }
    public void setSala(Sala sala) { this.sala = sala; }

    @Override
    public String toString() {
        String salaInfo = sala == null ? "Nenhum" : String.valueOf(sala.getNumero());
        return "Código: " + codigo
                + " | Nome do animal: " + nomeAnimal
                + " | Especie: " + especie
                + " | Nome do Tutor: " + nomeTutor
                + " | Data: " + data
                + " | Status: " + status
                + " | Observação: " + observacao
                + " | Procedimento: " + procedimento.getNome()
                + " | Box: " + salaInfo;
    }
}
