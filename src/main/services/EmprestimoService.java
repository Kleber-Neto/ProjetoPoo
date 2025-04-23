package main.services;

import main.models.Emprestavel;
import main.models.Emprestimo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmprestimoService {

    public List<String> getTitulosEmprestadosAtualmente() {
        List<String> ativos = new ArrayList<>();
        for (Emprestavel e : emprestimos) {
            if (!e.estaDisponivel()) {
                String titulo = (String) e.getLivro();
                if (titulo != null) {
                    ativos.add(titulo.toLowerCase());
                }
            }
        }
        return ativos;
    }

    private List<Emprestavel> emprestimos = new ArrayList<>();
    private BibliotecaService biblioteca;
    private final String ARQUIVO_EMPRESTIMOS = "emprestimos.txt";

    public EmprestimoService(BibliotecaService biblioteca) {
        this.biblioteca = biblioteca;
        carregarEmprestimos();
    }

    public void emprestarLivro(String cpf, String titulo, LocalDate dataDevolucao) {
        if (!biblioteca.clienteExiste(cpf) || !biblioteca.livroExiste(titulo)) {
            throw new IllegalArgumentException("Cliente ou livro não encontrado!");
        }

        // Verifica se o livro está reservado por outra pessoa
        boolean reservadoPorOutro = biblioteca.getReservas().stream()
                .anyMatch(r -> r.getTituloLivro().trim().equalsIgnoreCase(titulo.trim())
                        && !r.getCpf().trim().equalsIgnoreCase(cpf.trim()));

        if (reservadoPorOutro) {
            throw new IllegalArgumentException("Este livro está reservado por outro cliente.");
        }

        // Cancela a reserva se for do mesmo cliente
        biblioteca.getReservas().stream()
                .filter(r -> r.getTituloLivro().trim().equalsIgnoreCase(titulo.trim())
                        && r.getCpf().trim().equalsIgnoreCase(cpf.trim()))
                .findFirst()
                .ifPresent(r -> {
                    biblioteca.cancelarReserva(cpf, titulo);
                    System.out.println("Reserva cancelada automaticamente após o empréstimo.");
                });

        // Verifica se o livro já está emprestado
        boolean livroJaEmprestado = emprestimos.stream()
                .anyMatch(e -> {
                    String livro = (String) e.getLivro();
                    return livro != null && livro.equalsIgnoreCase(titulo) && !e.estaDisponivel();
                });

        if (livroJaEmprestado) {
            throw new IllegalArgumentException("Este livro já está emprestado!");
        }

        Emprestimo emprestimo = new Emprestimo(titulo, cpf, LocalDate.now(), dataDevolucao);
        emprestimos.add(emprestimo);
        salvarEmprestimos();
    }

    public void devolverLivro(String cpf, String titulo) {
        Optional<Emprestavel> emprestimoOpt = emprestimos.stream()
                .filter(e -> e.getLivro().equals(titulo) && e.getCliente().equals(cpf) && !e.estaDisponivel())
                .findFirst();

        if (emprestimoOpt.isEmpty()) {
            throw new IllegalArgumentException("Nenhum empréstimo ativo encontrado para esse cliente e livro.");
        }

        emprestimoOpt.get().devolver();
        salvarEmprestimos();
    }

    public List<Emprestavel> getEmprestimos() {
        return new ArrayList<>(emprestimos);
    }

    private void salvarEmprestimos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_EMPRESTIMOS))) {
            for (Emprestavel emp : emprestimos) {
                writer.write(emp.getLivro() + ";" + emp.getCliente() + ";" + emp.getDataDevolucao() + ";"
                        + (emp.estaDisponivel() ? "1" : "0"));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar empréstimos: " + e.getMessage());
        }
    }

    private void carregarEmprestimos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_EMPRESTIMOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 4) {
                    String livro = partes[0];
                    String cliente = partes[1];
                    LocalDate dataDevolucao = LocalDate.parse(partes[2]);
                    boolean devolvido = partes[3].equals("1");

                    Emprestimo emp = new Emprestimo(livro, cliente, LocalDate.now(), dataDevolucao);
                    if (devolvido)
                        emp.devolver();
                    emprestimos.add(emp);
                }
            }
        } catch (IOException ignored) {
        }
    }
}
