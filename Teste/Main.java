import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Clinica clinica = new Clinica();

    public static void main(String[] args) {
        criarDadosIniciais();

        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarAtendimento();
                    break;
                case 2:
                    associarVeterinario();
                    break;
                case 3:
                    atribuirAtend();
                    break;
                case 4:
                    exibirAtendsDaSala();
                    break;
                case 5:
                    exibirFinalizadasPorSala();
                    break;
                case 6:
                    buscarPorStatus();
                    break;
                case 7:
                    exibirDetalhesAtendimento();
                    break;
                case 0:
                    System.out.println("Programa encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void criarDadosIniciais() {
        Veterinario v1 = new Veterinario("Pedro", "111.111.111-11", "aaa", "31999990001");
        Veterinario v2 = new Veterinario("Thiago", "222.222.222-22", "bbb", "31999990002");
        Veterinario v3 = new Veterinario("Carlos", "333.333.333-33", "ccc", "31999990003");

        Sala s1 = new Sala(1, "a", 5, "bloco A");
        Sala s2 = new Sala(2, "b", 5, "bloco B");
        Sala s3 = new Sala(3, "c", 5, "bloco C");

        clinica.adicionarVeterinario(v1);
        clinica.adicionarVeterinario(v2);
        clinica.adicionarVeterinario(v3);

        clinica.adicionarSala(s1);
        clinica.adicionarSala(s2);
        clinica.adicionarSala(s3);
    }

    private static void exibirMenu() {
        System.out.println("\n Clinica");
        System.out.println("1 - Cadastrar atendimento");
        System.out.println("2 - Associar veterinario a uma sala");
        System.out.println("3 - Atribuir atendimento a uma sala");
        System.out.println("4 - Exibir atendimentos de uma sala");
        System.out.println("5 - Quantidade de atendimentos finalizadas por sala");
        System.out.println("6 - Buscar atendimentos por status");
        System.out.println("7 - Exibir detalhes de um atendimento");
        System.out.println("0 - Sair");
        System.out.println("---------------------------------------");
    }

    private static void cadastrarAtendimento() {
        System.out.println("\n--- Cadastro de Atendimento ---");
        int codigo = lerInt("Código: ");
        if (clinica.buscarAtendimento(codigo) != null) {
            System.out.println("Já existe um atendimento com esse código.");
            return;
        }

        String nomeAnimal = lerTexto("Nome do animal: ");
        String especie = lerTexto("Especie: ");
        String nomeTutor = lerTexto("Nome do tutor: ");
        String data = lerTexto("Data: ");
        String status = lerStatus();
        String observacao = lerTexto("Observação: ");


        System.out.println("\nDados do procedimento:");
        String nomeProcedimento = lerTexto("Nome: ");
        int duracaoEstimada = lerInt("Duração estimada(min): ");
        double valor = lerDouble("Valor: ");
        String nivelComplexidade = lerTexto("Nivel de complexidade: ");

        Procedimento procedimento = new Procedimento(nomeProcedimento, duracaoEstimada, valor, nivelComplexidade);
        Atendimento atend = new Atendimento( codigo, nomeAnimal, especie, nomeTutor, data, status, observacao, procedimento);
        clinica.cadastrarAtendimento(atend);

        System.out.println("Atendimento cadastrado com sucesso!");
        if (status.equalsIgnoreCase("aberta")) {
            System.out.println("Como o Atendimento está aberto, ele foi cadastrada sem sala.");
        }
    }

    private static void associarVeterinario() {
        System.out.println("\n--- Associar Veterinario a Sala ---");
        listarVeterinarios();
        String cpf = lerTexto("CPF: ");
        Veterinario veterinario = clinica.buscarVeterinarioPorCpf(cpf);

        if (veterinario == null) {
            System.out.println("Veterinario não encontrado.");
            return;
        }

        listarSalas();
        int numero = lerInt("Número da sala: ");
        Sala sala = clinica.buscarSala(numero);

        if (sala == null) {
            System.out.println("Sala não encontrado.");
            return;
        }

        if (clinica.associarMecanicoAoBox(veterinario, sala)) {
            System.out.println("Veterinario associado a sala com sucesso!");
        } else {
            System.out.println("Não foi possível associar. O vet pode já possuir uma sala ou a sala já possui responsável.");
        }
    }

    private static void atribuirAtend() {
        System.out.println("\n--- Atribuir Atend a Sala ---");
        int codigo = lerInt("Código do Atend: ");
        Atendimento atendimento = clinica.buscarAtendimento(codigo);

        if (atendimento == null) {
            System.out.println("Atendimento não encontrada.");
            return;
        }

        if (!atendimento.getStatus().equalsIgnoreCase("aberta") &&
                !atendimento.getStatus().equalsIgnoreCase("em execução")) {
            System.out.println("Somente atendimentos abertos ou em execução podem ser atribuídas a uma sala.");
            return;
        }

        listarSalas();
        int numero = lerInt("Número da sala: ");
        Sala sala = clinica.buscarSala(numero);

        if (sala == null) {
            System.out.println("Sala não encontrado.");
            return;
        }

        if (clinica.atribuirOrdemAoBox(atendimento, sala)) {
            System.out.println("Atend atribuída a sala com sucesso!");
        } else {
            System.out.println("Não foi possível atribuir o atendimento.");
        }
    }

    private static void exibirAtendsDaSala() {
        System.out.println("\n--- Atendimento de uma sala ---");
        int numero = lerInt("Número da sala: ");
        Sala sala = clinica.buscarSala(numero);

        if (sala == null) {
            System.out.println("sala não encontrado.");
            return;
        }

        System.out.println(sala);
        if (sala.getOrdens().isEmpty()) {
            System.out.println("Nenhum atendimento atribuída.");
        } else {
            for (Atendimento atendimento: sala.getOrdens()) {
                System.out.println(atendimento);
            }
        }
        System.out.println("Total de atends: " + sala.getOrdens().size());
    }

    private static void exibirFinalizadasPorSala() {
        System.out.println("\n--- Atendimento Finalizadas por Sala ---");
        for (Sala sala : clinica.getSalas()) {
            System.out.println("Sala " + sala.getNumero() + ": " + sala.quantidadeFinalizadas() + " atendimentos finalizada(s)");
        }
    }

    private static void buscarPorStatus() {
        System.out.println("\n--- Buscar Atendimentos por Status ---");
        String status = lerStatus();
        List<Atendimento> atendimentos = clinica.buscarPorStatus(status);

        if (atendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento encontrado com esse status.");
            return;
        }

        for (Atendimento atend : atendimentos) {
            System.out.println("\n" + atend);
            if (atend.getSala() != null) {
                System.out.println("Sala: " + atend.getSala().getNumero());
                if (atend.getSala().getVeterinarioResponsavel() != null) {
                    System.out.println("Vet: " + atend.getSala().getVeterinarioResponsavel().getNome());
                } else {
                    System.out.println("Vet: Nenhum responsável associado.");
                }
            } else {
                System.out.println("Sala: Nenhum");
                System.out.println("Vet: Nenhum");
            }
        }
    }

    private static void exibirDetalhesAtendimento() {
        System.out.println("\n--- Detalhes do Atendimento ---");
        int codigo = lerInt("Código do Atendimento: ");
        Atendimento atendimento = clinica.buscarAtendimento(codigo);

        if (atendimento == null) {
            System.out.println("Atendimento não encontrada.");
            return;
        }

        System.out.println("Código: " + atendimento.getCodigo());
        System.out.println("Nome do animal: " + atendimento.getNomeAnimal());
        System.out.println("Especie: " + atendimento.getEspecie());
        System.out.println("Nome do tutor: " + atendimento.getNomeTutor());
        System.out.println("Data: " + atendimento.getData());
        System.out.println("Status: " + atendimento.getStatus());
        System.out.println("Procedimento: " + atendimento.getProcedimento().getNome());
        System.out.println("Tempo estimado: " + atendimento.getProcedimento().getTempoEstimado() + " minutos");
        System.out.println("Complexidade: " + atendimento.getProcedimento().getComplexidade());

        if (atendimento.getSala() != null) {
            System.out.println("Sala: " + atendimento.getSala().getNumero());
            if (atendimento.getSala().getVeterinarioResponsavel() != null) {
                System.out.println("Veterinario responsável: " + atendimento.getSala().getVeterinarioResponsavel().getNome());
                System.out.println("Especialidade: " + atendimento.getSala().getVeterinarioResponsavel().getEspecialidade());
            }
        } else {
            System.out.println("Sala: Nenhum");
        }
    }

    private static void listarVeterinarios() {
        System.out.println("\nVeterinarios disponíveis:");
        for (Veterinario vet : clinica.getVeterinarios()) {
            System.out.println(vet);
        }
    }

    private static void listarSalas() {
        System.out.println("\nSalas disponíveis:");
        for (Sala sala : clinica.getSalas()) {
            System.out.println(sala);
        }
    }

    private static String lerStatus() {
        while (true) {
            String status = lerTexto("Status (aberta/em execução/finalizada): ");
            if (status.equalsIgnoreCase("aberta") ||
                    status.equalsIgnoreCase("em execução") ||
                    status.equalsIgnoreCase("finalizada")) {
                return status;
            }
            System.out.println("Status inválido.");
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                String valor = lerTexto(mensagem).replace(',', '.');
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numérico válido.");
            }
        }
    }
}
