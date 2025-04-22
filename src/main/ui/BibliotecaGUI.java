// Arquivo: BibliotecaGUI.java (com menus organizados por abas)
package main.ui;

import main.services.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class BibliotecaGUI extends JFrame {
    private BibliotecaService biblioteca;
    private EmprestimoService emprestimoService;
    private ReservaService reservaService;

    public BibliotecaGUI() {
        super("Sistema de Biblioteca");
        this.biblioteca = new BibliotecaService();
        this.emprestimoService = new EmprestimoService(biblioteca);
        this.reservaService = new ReservaService();

        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.add("Livros", painelLivros());
        abas.add("Clientes", painelClientes());
        abas.add("Empréstimos", painelEmprestimos());
        abas.add("Reservas", painelReservas());
        abas.add("Relatórios", painelRelatorios());

        add(abas);
    }

    private JPanel painelLivros() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
        painel.add(botao("Cadastrar Livro", e -> cadastrarLivro()));
        painel.add(botao("Atualizar Livro", e -> atualizarLivro()));
        painel.add(botao("Remover Livro", e -> removerLivro()));
        return painel;
    }

    private JPanel painelClientes() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
        painel.add(botao("Cadastrar Cliente", e -> cadastrarCliente()));
        painel.add(botao("Atualizar Cliente", e -> atualizarCliente()));
        painel.add(botao("Remover Cliente", e -> removerCliente()));
        return painel;
    }

    private JPanel painelEmprestimos() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
        painel.add(botao("Listar Livros Disponíveis", e -> listarLivros()));
        painel.add(botao("Realizar Empréstimo", e -> emprestarLivro()));
        painel.add(botao("Devolver Livro", e -> devolverLivro()));
        return painel;
    }

    private JPanel painelReservas() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
        painel.add(botao("Reservar Livro", e -> reservarLivro()));
        painel.add(botao("Cancelar Reserva", e -> cancelarReserva()));
        return painel;
    }

    private JPanel painelRelatorios() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
        painel.add(botao("Relatório de Empréstimos", e -> gerarRelatorioEmprestimos()));
        painel.add(botao("Relatório de Reservas", e -> gerarRelatorioReservas()));
        return painel;
    }

    private JButton botao(String titulo, java.awt.event.ActionListener acao) {
        JButton btn = new JButton(titulo);
        btn.addActionListener(acao);
        return btn;
    }

    // Reuso de métodos anteriores: cadastrar/atualizar/remover livro e cliente,
    // empréstimo, reserva, relatórios

    // Métodos de operação para cada botão:

    private void cadastrarLivro() {
        String titulo = JOptionPane.showInputDialog("Título do livro:");
        String autor = JOptionPane.showInputDialog("Autor:");
        String anoStr = JOptionPane.showInputDialog("Ano:");
        try {
            int ano = Integer.parseInt(anoStr);
            biblioteca.cadastrarLivro(titulo, autor, ano);
            JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void atualizarLivro() {
        String antigo = JOptionPane.showInputDialog("Título atual do livro:");
        String novoTitulo = JOptionPane.showInputDialog("Novo título:");
        String novoAutor = JOptionPane.showInputDialog("Novo autor:");
        String novoAno = JOptionPane.showInputDialog("Novo ano:");
        try {
            int ano = Integer.parseInt(novoAno);
            boolean ok = biblioteca.atualizarLivro(antigo, novoTitulo, novoAutor, ano);
            if (ok)
                JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
            else
                JOptionPane.showMessageDialog(this, "Livro não encontrado.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void removerLivro() {
        String titulo = JOptionPane.showInputDialog("Título do livro a remover:");
        boolean ok = biblioteca.removerLivro(titulo);
        String msg = ok ? "Livro removido com sucesso!" : "Livro não encontrado.";
        JOptionPane.showMessageDialog(this, msg);
    }

    private void cadastrarCliente() {
        String nome = JOptionPane.showInputDialog("Nome do cliente:");
        String cpf = JOptionPane.showInputDialog("CPF (11 dígitos):");
        try {
            biblioteca.cadastrarCliente(nome, cpf);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void atualizarCliente() {
        String cpf = JOptionPane.showInputDialog("CPF do cliente a atualizar:");
        String novoNome = JOptionPane.showInputDialog("Novo nome:");
        boolean ok = biblioteca.atualizarCliente(cpf, novoNome);
        String msg = ok ? "Cliente atualizado!" : "Cliente não encontrado.";
        JOptionPane.showMessageDialog(this, msg);
    }

    private void removerCliente() {
        String cpf = JOptionPane.showInputDialog("CPF do cliente a remover:");
        boolean ok = biblioteca.removerCliente(cpf);
        String msg = ok ? "Cliente removido!" : "Cliente não encontrado.";
        JOptionPane.showMessageDialog(this, msg);
    }

    private void listarLivros() {
        var livrosDisponiveis = biblioteca.listarLivrosDisponiveisComFiltro(emprestimoService);

        if (livrosDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum livro disponível no momento.");
            return;
        }

        StringBuilder sb = new StringBuilder("Livros disponíveis:\n");
        for (var livro : livrosDisponiveis) {
            sb.append("- ").append(livro.getTitulo())
                    .append(" (").append(livro.getAutor()).append(")\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void emprestarLivro() {
        String cpf = JOptionPane.showInputDialog("CPF do cliente:");
        String titulo = JOptionPane.showInputDialog("Título do livro:");
        try {
            emprestimoService.emprestarLivro(cpf, titulo, LocalDate.now().plusDays(7));
            JOptionPane.showMessageDialog(this, "Empréstimo realizado!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void devolverLivro() {
        String cpf = JOptionPane.showInputDialog("CPF do cliente:");
        String titulo = JOptionPane.showInputDialog("Título do livro:");
        try {
            emprestimoService.devolverLivro(cpf, titulo);
            JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void reservarLivro() {
        String cpf = JOptionPane.showInputDialog("CPF do cliente:");
        String titulo = JOptionPane.showInputDialog("Título do livro:");
        try {
            reservaService.adicionarReserva(cpf, titulo);
            JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void cancelarReserva() {
        String cpf = JOptionPane.showInputDialog("CPF do cliente:");
        String titulo = JOptionPane.showInputDialog("Título da reserva a cancelar:");
        try {
            reservaService.cancelarReserva(cpf, titulo);
            JOptionPane.showMessageDialog(this, "Reserva cancelada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void gerarRelatorioEmprestimos() {
        StringBuilder sb = new StringBuilder("Relatório de Empréstimos:");
        for (var emp : emprestimoService.getEmprestimos()) {
            String status = emp.estaDisponivel() ? "DEVOLVIDO" : "EMPRESTADO";
            sb.append("Livro: ").append(emp.getLivro())
                    .append(" | Cliente CPF: ").append(emp.getCliente())
                    .append(" | Devolução: ").append(emp.getDataDevolucao())
                    .append(" | Status: ").append(status).append("");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void gerarRelatorioReservas() {
        String relatorio = reservaService.gerarRelatorioReservas();
        JOptionPane.showMessageDialog(this, relatorio);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BibliotecaGUI().setVisible(true));
    }
}