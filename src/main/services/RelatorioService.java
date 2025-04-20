// Arquivo: RelatorioService.java (com dados detalhados e construtor compatível)
package main.services;

import main.models.Emprestavel;
import main.models.InRelatorio;

import java.util.List;

public class RelatorioService implements InRelatorio {
    private EmprestimoService emprestimoService;
    private BibliotecaService biblioteca;

    public RelatorioService(EmprestimoService emprestimoService, BibliotecaService biblioteca) {
        this.emprestimoService = emprestimoService;
        this.biblioteca = biblioteca;
    }

    @Override
    public void gerarRelatorio() {
        List<Emprestavel> emprestimos = emprestimoService.getEmprestimos();

        System.out.println("\n=== RELATÓRIO DE EMPRÉSTIMOS ===");

        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }

        for (Emprestavel emp : emprestimos) {
            String livroDetalhado = biblioteca.getLivroDetalhado((String) emp.getLivro());
            String nomeCliente = biblioteca.getNomeClientePorCpf((String) emp.getCliente());
            String status = emp.estaDisponivel() ? "DEVOLVIDO" : "EMPRESTADO";

            System.out.println("Livro: " + livroDetalhado +
                    " | Cliente: " + nomeCliente +
                    " | CPF: " + emp.getCliente() +
                    " | Devolução: " + emp.getDataDevolucao() +
                    " | Status: " + status);
        }
    }
}