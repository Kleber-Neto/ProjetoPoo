package main;

import main.models.Emprestavel;
import main.models.Livro;
import main.services.BibliotecaService;
import main.services.EmprestimoService;
import main.services.RelatorioService;
import main.models.InRelatorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BibliotecaService biblioteca = new BibliotecaService();
    private static EmprestimoService emprestimoService = new EmprestimoService(biblioteca);
    private static InRelatorio relatorio = new RelatorioService(emprestimoService, biblioteca);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- CLUBE DA LEITURA ---");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Cadastrar Cliente");
            System.out.println("3. Listar Livros Disponíveis");
            System.out.println("4. Realizar Empréstimo");
            System.out.println("5. Devolver Livro");
            System.out.println("6. Gerar Relatório");
            System.out.println("7. Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarLivro();
                case 2 -> cadastrarCliente();
                case 3 -> listarLivros();
                case 4 -> emprestarLivro();
                case 5 -> devolverLivro();
                case 6 -> relatorio.gerarRelatorio();
            }
        } while (opcao != 7);
    }

    private static void cadastrarLivro() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        biblioteca.cadastrarLivro(titulo, autor, ano);
        System.out.println("Livro cadastrado!");
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        try {
            biblioteca.cadastrarCliente(nome, cpf);
            System.out.println("Cliente cadastrado!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarLivros() {
        List<Livro> todos = biblioteca.getLivros();
        List<Emprestavel> emprestimos = emprestimoService.getEmprestimos();
        List<String> livrosEmprestados = emprestimos.stream()
                .filter(e -> !e.estaDisponivel())
                .map(e -> (String) e.getLivro())
                .collect(Collectors.toList());

        System.out.println("\nLIVROS DISPONÍVEIS:");
        todos.stream()
                .filter(l -> !livrosEmprestados.contains(l.getTitulo()))
                .forEach(livro -> System.out.println(livro.getTitulo() + " - " +
                        livro.getAutor()));
    }

    private static void emprestarLivro() {
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        System.out.print("Título do Livro: ");
        String titulo = scanner.nextLine();

        try {
            emprestimoService.emprestarLivro(cpf, titulo, LocalDate.now().plusDays(7));
            System.out.println("Empréstimo realizado!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void devolverLivro() {
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        System.out.print("Título do Livro: ");
        String titulo = scanner.nextLine();

        try {
            emprestimoService.devolverLivro(cpf, titulo);
            System.out.println("Livro devolvido!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}