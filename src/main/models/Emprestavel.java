package main.models;

import java.time.LocalDate;

public interface Emprestavel {
    boolean estaDisponivel();

    void emprestar(Cliente cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao);

    void devolver();

    String getLivro();

    Object getCliente();

    LocalDate getDataDevolucao();
}