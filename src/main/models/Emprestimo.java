package main.models;

import java.time.LocalDate;

public class Emprestimo implements Emprestavel {
    private String livro;
    private String cliente;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;

    public Emprestimo(String livro, String cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.livro = livro;
        this.cliente = cliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = false;
    }

    @Override
    public boolean estaDisponivel() {
        return devolvido;
    }

    @Override
    public void emprestar(Cliente cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.cliente = cliente.getCpf();
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = false;
    }

    @Override
    public void devolver() {
        this.devolvido = true;
    }

    @Override
    public String getLivro() {
        return livro;
    }

    @Override
    public Object getCliente() {
        return cliente;
    }

    @Override
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
}
