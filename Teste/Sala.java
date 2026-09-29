import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int numero;
    private String tipoSala;
    private int capacidadeMaximaAnimais;
    private String bloco;
    private Veterinario veterinarioResponsavel;
    private List<Atendimento> atend;

    public Sala(int numero, String tipoSala, int capacidadeMaximaAnimais, String bloco) {
        this.numero = numero;
        this.tipoSala = tipoSala;
        this.capacidadeMaximaAnimais = capacidadeMaximaAnimais;
        this.bloco = bloco;
        this.atend = new ArrayList<>();
    }

    public int getNumero() { return numero; }
    public String getTipoSala() { return tipoSala; }
    public int getCapacidadeMaximaAnimais() { return capacidadeMaximaAnimais; }
    public String getBloco() { return bloco; }
    public Veterinario getVeterinarioResponsavel() { return veterinarioResponsavel; }
    public List<Atendimento> getOrdens() { return atend; }

    public boolean possuiCapacidade() {
        return atend.size() < capacidadeMaximaAnimais;
    }

    public boolean podeReceber(Atendimento atendimento) {
        return possuiCapacidade()
                && atendimento.getProcedimento().getComplexidade().equalsIgnoreCase(tipoSala);
    }

    public boolean adicionarAtendimento(Atendimento atendimento) {
        if (!podeReceber(atendimento)) {
            return false;
        }
        atend.add(atendimento);
        atendimento.setSala(this);
        return true;
    }

    public void definirVeterinario(Veterinario veterinario) {
        this.veterinarioResponsavel = veterinario;
    }

    public int quantidadeFinalizadas() {
        int total = 0;
        for (Atendimento atendimento : atend) {
            if (atendimento.getStatus().equalsIgnoreCase("finalizada")) {
                total++;
            }
        }
        return total;
    }

    @Override
    public String toString() {
        String veterinario = veterinarioResponsavel == null ? "Nenhum" : veterinarioResponsavel.getNome();
        return "N° da sala: " + numero + " | Tipo: " + tipoSala
                + " | Capacidade: " + capacidadeMaximaAnimais
                + " | Bloco: " + bloco
                + " | Veterinario: " + veterinario;
    }
}
