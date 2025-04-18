package main;

import main.models.Livro;
import main.models.Cliente;
import main.services.BibliotecaService;
import main.services.EmprestimoService;
import main.services.RelatorioService;
import main.models.Emprestimo;
import main.models.InRelatorio;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BibliotecaService biblioteca = new BibliotecaService();
    private static Emprestimo emprestimos = new Emprestimo();
    private static EmprestimoService emprestimoService = new EmprestimoService();
    private static InRelatorio relatorio = new RelatorioService(emprestimoService.getEmprestimos());

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- CLUBE DA LEITURA ---");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Cadastrar Cliente");
            System.out.println("3. Listar Livros Disponíveis");
            System.out.println("4. Realizar Empréstimo");
            System.out.println("5. Gerar Relatório");
            System.out.println("6. Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer

            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    listarLivros();
                    break;
                case 4:
                    realizarEmprestimo();
                    break;
                case 5:
                    relatorio.gerarRelatorio();
                    break;
            }
        } while (opcao != 6);
    }

    private static void cadastrarLivro() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Ano: ");
        int ano = scanner.nextInt();

        biblioteca.cadastrarLivro(titulo, autor, ano);
        System.out.println("✔ Livro cadastrado!");
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        try {
            biblioteca.cadastrarCliente(nome, cpf);
            System.out.println("✔ Cliente cadastrado!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarLivros() {
        System.out.println("\nLIVROS DISPONÍVEIS:");
        BibliotecaService.listarLivrosDisponivel()
                .forEach(livro -> System.out.println(livro.getTitulo() + " - " + livro.getAutor()));
    }

    private static void realizarEmprestimo() {
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();

        System.out.print("Título do Livro: ");
        String titulo = scanner.nextLine();

        try {
            emprestimos.realizarEmprestimo(cpf, titulo);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void extracted() {
        System.out.println("✔ Empréstimo realizado!");
    }
}