package siga;

import java.util.List;

/**
 * Ponto de entrada do SIGA (código INICIAL da atividade da Aula 8).
 *
 * Esta é a camada de APRESENTAÇÃO. Ela demonstra, em execução, os três
 * deslizes que você deverá corrigir — e contém, ela própria, um deles.
 *
 * DESLIZE 3 (aqui): a validação da média está repetida nesta camada, com um
 * limite DIFERENTE do usado no ServicoAluno (aqui aceita até 100). Regra de
 * domínio duplicada em dois lugares acaba divergindo, como se vê.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Atividade CRUD e Etapa 1 (código inicial) ===\n");

        AlunoDAO dao = new AlunoDAOMemoria();
        ServicoAluno servico = new ServicoAluno(dao);

        // --- CREATE ---
        cadastrar(servico, new Aluno("Maria Silva", "2026001", 8.5));
        cadastrar(servico, new Aluno("João Souza",  "2026002", 6.0));
        System.out.println();

        // --- READ ---
        System.out.println("Alunos cadastrados:");
        for (Aluno aluno : servico.listar()) {
            System.out.println("  " + aluno);
        }

        // CORREÇÃO DESLIZE 1: a lista agora é uma cópia. Modificá-la não afeta o DAO.
        List<Aluno> lista = servico.listar();
        lista.add(new Aluno("Intruso Silva", "9999999", 10));
        System.out.println("\nApós a tela inserir um aluno DIRETAMENTE na lista retornada:");
        for (Aluno aluno : servico.listar()) {
            System.out.println("  " + aluno);
        }
        System.out.println("  (o 'Intruso' NÃO entrou no sistema, graças à cópia defensiva)");

        // CORREÇÃO DESLIZE 2: exclusão não é mais silenciosa.
        System.out.println("\nExclusão de matrícula inexistente:");
        try {
            servico.excluir("0000000");
        } catch (IllegalArgumentException e) {
            System.out.println("  Serviço recusou exclusão: " + e.getMessage());
        }

        // CORREÇÃO DESLIZE 3: a camada de apresentação apenas confia no serviço.
        Aluno suspeito = new Aluno("Média Absurda", "2026003", 50);
        System.out.println("\nTentativa de cadastrar aluno com média 50:");
        try {
            servico.cadastrar(suspeito);
        } catch (IllegalArgumentException e) {
            System.out.println("  Serviço recusou cadastro: " + e.getMessage());
        }

        // --- READ (um) ---
        System.out.println("\nBusca por matrícula existente:");
        try {
            Aluno consultado = servico.consultar("2026001");
            System.out.println("  Encontrado: " + consultado);
        } catch (IllegalArgumentException e) {
            System.out.println("  Erro: " + e.getMessage());
        }

        // --- UPDATE ---
        System.out.println("\nAtualização de aluno existente (mudando média para 9.0):");
        try {
            servico.alterar(new Aluno("Maria Silva", "2026001", 9.0));
            System.out.println("  Atualizado com sucesso. Nova situação:");
            System.out.println("  " + servico.consultar("2026001"));
        } catch (IllegalArgumentException e) {
            System.out.println("  Erro ao atualizar: " + e.getMessage());
        }

        System.out.println("\n=== Atividade Concluída ===");
    }

    /** Apresentação: traduz as exceções do serviço em mensagens ao usuário. */
    private static void cadastrar(ServicoAluno servico, Aluno aluno) {
        try {
            servico.cadastrar(aluno);
            System.out.println("Cadastrado: " + aluno);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível cadastrar: " + e.getMessage());
        }
    }
}
