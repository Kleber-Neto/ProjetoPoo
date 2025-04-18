package main.services;

import main.models.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {
    private static List<Livro> livros = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    // Cadastra um livro (Encapsulamento)
    public void cadastrarLivro(String titulo, String autor, int ano) {
        Livro livro = new Livro(titulo, autor, ano);
        livros.add(livro);
    }

    // Cadastra um cliente (Tratamento de exceção)
    public void cadastrarCliente(String nome, String cpf) {
        if (cpf == null || cpf.isEmpty() || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }

        // Verifica se o CPF já existe
        boolean cpfJaCadastrado = clientes.stream()
                .anyMatch(c -> c.getCpf().equals(cpf));

        if (cpfJaCadastrado) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        Cliente cliente = new Cliente(nome, cpf);
        clientes.add(cliente);
    }

    // Realiza empréstimo (Polimorfismo via Interface)
    public void emprestarLivro(String cpf, String titulo, LocalDate dataDevolucao) {
        Cliente cliente = buscarClientePorCPF(cpf);
        Livro livro = buscarLivroPorTitulo(titulo);

        if (cliente == null || livro == null) {
            throw new IllegalArgumentException("Cliente ou livro não encontrado!");
        }

        Emprestimo emprestimo = new Emprestimo(
                livro,
                cliente,
                LocalDate.now(),
                dataDevolucao);
        emprestimos.add(emprestimo);
    }

    // Métodos auxiliares (Collections)
    private Cliente buscarClientePorCPF(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    private Livro buscarLivroPorTitulo(String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
    }

    public static List<Livro> listarLivrosDisponivel() {
        return new ArrayList<>(livros); // Retona cópias da lista
    }
}