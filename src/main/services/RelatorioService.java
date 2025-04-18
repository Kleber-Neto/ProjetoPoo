package main.services;

import main.models.Emprestimo;
import main.models.InRelatorio;
import java.util.List;

public class RelatorioService implements InRelatorio {
    private List<Emprestimo> emprestimos;

    public RelatorioService(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    @Override
    public void gerarRelatorio() {
        System.out.println("\n=== RELATÓRIO DE EMPRÉSTIMOS ===");

        emprestimos.forEach(emp -> {
            String status = emp.estaDisponivel() ? "EMPRESTADO" : "DEVOLVIDO";
            System.out.println(
                    "Livro: " + emp.getLivro().getTitulo() +
                            " | Cliente: " + emp.getCliente().getName() +
                            " | Devolução: " + emp.getDataDevolucao() +
                            " | Status: " + status);
        });
    }
}