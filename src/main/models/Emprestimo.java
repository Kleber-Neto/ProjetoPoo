package main.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo implements Emprestavel {
    private Livro livro;
    private Cliente cliente;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;

    public Emprestimo() {
        this.livro = livro;
        this.cliente = cliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = false;
    }

    public Emprestimo(Livro livro2, Cliente cliente2, LocalDate now, LocalDate dataDevolucao2) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean estaDisponivel() {
        return devolvido;
    }

    @Override
    public void emprestar(Cliente cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        if (!estaDisponivel()) {
            throw new IllegalStateException("Livro já emprestado!");
        }
        this.cliente = cliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = false;
    }

    @Override
    public void devolver() {
        this.devolvido = true;
    }

    // Getters (Encapsulamento)
    public Livro getLivro() {
        return livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void realizarEmprestimo(String cpf, String titulo) {

        // TODO Auto-generated method stub
        System.out.println("Empréstimo realizado!");
    }

}