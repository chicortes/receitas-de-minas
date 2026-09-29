import java.util.ArrayList;
import java.util.List;

public class Clinica {
    private List<Veterinario> veterinarios;
    private List<Sala> salas;
    private List<Atendimento> atends;

    public Clinica() {
        veterinarios = new ArrayList<>();
        salas = new ArrayList<>();
        atends = new ArrayList<>();
    }

    public void adicionarVeterinario(Veterinario vet) {
        veterinarios.add(vet);
    }

    public void adicionarSala(Sala sala) {
        salas.add(sala);
    }

    public void cadastrarAtendimento(Atendimento atend) {
        atends.add(atend);
    }

    public Veterinario buscarVeterinarioPorCpf(String cpf) {
        for (Veterinario vet : veterinarios) {
            if (vet.getCpf().equals(cpf)) {
                return vet;
            }
        }
        return null;
    }

    public Sala buscarSala(int numero) {
        for (Sala sala : salas) {
            if (sala.getNumero() == numero) {
                return sala;
            }
        }
        return null;
    }

    public Atendimento buscarAtendimento(int codigo) {
        for (Atendimento atend : atends) {
            if (atend.getCodigo() == codigo) {
                return atend;
            }
        }
        return null;
    }

    public boolean associarMecanicoAoBox(Veterinario vet, Sala sala) {
        if (vet == null || sala == null) {
            return false;
        }

        if (vet.possuiSala()) {
            return false;
        }

        if (sala.getVeterinarioResponsavel() != null) {
            return false;
        }

        vet.definirSalaResponsavel(sala);
        sala.definirVeterinario(vet);
        return true;
    }

    public boolean atribuirOrdemAoBox(Atendimento atend, Sala sala) {
        if (atend == null || sala == null) {
            return false;
        }

        if (!atend.getStatus().equalsIgnoreCase("aberta") &&
                !atend.getStatus().equalsIgnoreCase("em execução")) {
            return false;
        }

        return sala.adicionarAtendimento(atend);
    }

    public List<Atendimento> buscarPorStatus(String status) {
        List<Atendimento> resultado = new ArrayList<>();
        for (Atendimento atend : atends) {
            if (atend.getStatus().equalsIgnoreCase(status)) {
                resultado.add(atend);
            }
        }
        return resultado;
    }

    public List<Veterinario> getVeterinarios() { return veterinarios; }
    public List<Sala> getSalas() { return salas; }
    public List<Atendimento> getAtendimentos() { return atends; }
}
