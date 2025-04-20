package main.models;

import main.models.Cliente;
import java.time.LocalDate;

public interface Emprestavel {
    boolean estaDisponivel();

    void emprestar(Cliente cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao);

    void devolver();

    Object getLivro();

    Object getCliente();

    LocalDate getDataDevolucao();
}