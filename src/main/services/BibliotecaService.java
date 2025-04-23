package main.services;

import main.models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BibliotecaService {
    private List<Livro> livros = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private ReservaService reservaService;
    private final String ARQUIVO_LIVROS = "livros.txt";
    private final String ARQUIVO_CLIENTES = "clientes.txt";

    public BibliotecaService() {
        carregarDados();
    }

    public void setReservaService(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    public List<ReservaService.Reserva> getReservas() {
        return reservaService != null ? reservaService.listarReservas() : new ArrayList<>();
    }

    public void cancelarReserva(String cpf, String titulo) {
        if (reservaService != null) {
            reservaService.cancelarReserva(cpf, titulo);
        }
    }

    public List<Livro> listarLivrosDisponivel(List<String> titulosEmprestados) {
        List<String> titulosReservados = getReservas().stream()
                .map(r -> r.getTituloLivro().toLowerCase())
                .collect(Collectors.toList());

        return livros.stream()
                .filter(livro -> !titulosEmprestados.contains(livro.getTitulo().toLowerCase()) &&
                        !titulosReservados.contains(livro.getTitulo().toLowerCase()))
                .collect(Collectors.toList());
    }

    // Listagem completa com filtro de disponibilidade
    public List<Livro> listarLivrosDisponiveisComFiltro(EmprestimoService emprestimoService) {
        List<String> titulosEmprestados = new ArrayList<>();
        for (Emprestavel e : emprestimoService.getEmprestimos()) {
            if (!e.estaDisponivel()) {
                String titulo = (String) e.getLivro();
                if (titulo != null) {
                    titulosEmprestados.add(titulo.toLowerCase());
                }
            }
        }
        return listarLivrosDisponivel(titulosEmprestados);
    }

    public void cadastrarLivro(String titulo, String autor, int ano) {
        Livro livro = new Livro(titulo, autor, ano);
        livros.add(livro);
        salvarLivros();
    }

    public void cadastrarCliente(String nome, String cpf) {
        if (cpf == null || cpf.isEmpty() || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }
        boolean cpfJaCadastrado = clientes.stream().anyMatch(c -> c.getCpf().equals(cpf));
        if (cpfJaCadastrado) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }
        Cliente cliente = new Cliente(nome, cpf);
        clientes.add(cliente);
        salvarClientes();
    }

    public Cliente buscarClientePorCPF(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().trim().equalsIgnoreCase(cpf.trim()))
                .findFirst()
                .orElse(null);
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().trim().equalsIgnoreCase(titulo.trim()))
                .findFirst()
                .orElse(null);
    }

    public List<Livro> listarLivrosDisponivel() {
        return new ArrayList<>(livros);
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public List<Livro> getLivros() {
        return new ArrayList<>(livros);
    }

    public boolean atualizarLivro(String tituloAntigo, String novoTitulo, String novoAutor, int novoAno) {
        Livro livro = buscarLivroPorTitulo(tituloAntigo);
        if (livro == null)
            return false;
        livros.remove(livro);
        livros.add(new Livro(novoTitulo, novoAutor, novoAno));
        salvarLivros();
        return true;
    }

    public boolean removerLivro(String titulo) {
        Livro livro = buscarLivroPorTitulo(titulo);
        if (livro == null)
            return false;
        livros.remove(livro);
        salvarLivros();
        return true;
    }

    public boolean atualizarCliente(String cpf, String novoNome) {
        Cliente cliente = buscarClientePorCPF(cpf);
        if (cliente == null)
            return false;
        clientes.remove(cliente);
        clientes.add(new Cliente(novoNome, cpf));
        salvarClientes();
        return true;
    }

    public boolean removerCliente(String cpf) {
        Cliente cliente = buscarClientePorCPF(cpf);
        if (cliente == null)
            return false;
        clientes.remove(cliente);
        salvarClientes();
        return true;
    }

    private void salvarLivros() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_LIVROS))) {
            for (Livro livro : livros) {
                writer.write(livro.getTitulo() + ";" + livro.getAutor() + ";" + livro.getAno());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    private void salvarClientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CLIENTES))) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.getName() + ";" + cliente.getCpf());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    private void carregarDados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_LIVROS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    livros.add(new Livro(partes[0], partes[1], Integer.parseInt(partes[2])));
                }
            }
        } catch (IOException ignored) {
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CLIENTES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    clientes.add(new Cliente(partes[0], partes[1]));
                }
            }
        } catch (IOException ignored) {
        }
    }

    public String getNomeClientePorCpf(String cpf) {
        Cliente cliente = buscarClientePorCPF(cpf);
        return cliente != null ? cliente.getName() : "Desconhecido";
    }

    public String getLivroDetalhado(String titulo) {
        Livro livro = buscarLivroPorTitulo(titulo);
        return livro != null ? livro.getTitulo() + " de " + livro.getAutor() + " (" + livro.getAno() + ")" : titulo;
    }

    public boolean livroExiste(String titulo) {
        return livros.stream().anyMatch(l -> l.getTitulo().equalsIgnoreCase(titulo));
    }

    public boolean clienteExiste(String cpf) {
        return clientes.stream().anyMatch(c -> c.getCpf().equalsIgnoreCase(cpf));
    }
}
