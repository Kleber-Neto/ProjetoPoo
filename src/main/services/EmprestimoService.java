// Arquivo: EmprestimoService.java (com devolução e relatório detalhado)
package main.services;

import main.models.*;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmprestimoService {
    private List<Emprestavel> emprestimos = new ArrayList<>();
    private BibliotecaService biblioteca;

    public EmprestimoService(BibliotecaService biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void emprestarLivro(String cpf, String titulo, LocalDate dataDevolucao) {
        if (!biblioteca.clienteExiste(cpf) || !biblioteca.livroExiste(titulo)) {
            throw new IllegalArgumentException("Cliente ou livro não encontrado!");
        }

        boolean livroJaEmprestado = emprestimos.stream()
                .anyMatch(e -> e.getLivro().equals(titulo) && !e.estaDisponivel());

        if (livroJaEmprestado) {
            throw new IllegalArgumentException("Este livro já está emprestado!");
        }

        Emprestimo emprestimo = new Emprestimo(titulo, cpf, LocalDate.now(), dataDevolucao);
        emprestimos.add(emprestimo);
    }

    public void devolverLivro(String cpf, String titulo) {
        Optional<Emprestavel> emprestimoOpt = emprestimos.stream()
                .filter(e -> e.getLivro().equals(titulo) && e.getCliente().equals(cpf) && !e.estaDisponivel())
                .findFirst();

        if (emprestimoOpt.isEmpty()) {
            throw new IllegalArgumentException("Nenhum empréstimo ativo encontrado para esse cliente e livro.");
        }

        emprestimoOpt.get().devolver();
    }

    public List<Emprestavel> getEmprestimos() {
        return new ArrayList<>(emprestimos);
    }

    public BibliotecaService getBiblioteca() {
        return biblioteca;
    }
}
