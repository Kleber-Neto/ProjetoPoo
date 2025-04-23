package main.services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaService {
    private List<Reserva> reservas = new ArrayList<>();
    private final String ARQUIVO_RESERVAS = "reservas.txt";

    public ReservaService() {
        carregarReservas();
    }

    public void adicionarReserva(String cpf, String titulo) {
        if (reservas.stream().anyMatch(r -> r.getTituloLivro().equalsIgnoreCase(titulo))) {
            throw new IllegalArgumentException("Este livro já está reservado.");
        }
        reservas.add(new Reserva(cpf, titulo));
        salvarReservas();
    }

    public void cancelarReserva(String cpf, String titulo) {
        reservas.removeIf(r -> r.getCpf().equalsIgnoreCase(cpf) && r.getTituloLivro().equalsIgnoreCase(titulo));
        salvarReservas();
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    public String gerarRelatorioReservas() {
        if (reservas.isEmpty())
            return "Nenhuma reserva encontrada.";
        return reservas.stream()
                .map(r -> "Cliente: " + r.getCpf() + " | Livro: " + r.getTituloLivro())
                .collect(Collectors.joining("\n"));
    }

    private void salvarReservas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_RESERVAS))) {
            for (Reserva r : reservas) {
                writer.write(r.getCpf() + ";" + r.getTituloLivro());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar reservas: " + e.getMessage());
        }
    }

    private void carregarReservas() {
        File arquivo = new File(ARQUIVO_RESERVAS);
        if (!arquivo.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    reservas.add(new Reserva(partes[0], partes[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar reservas: " + e.getMessage());
        }
    }

    public static class Reserva {
        private String cpf;
        private String tituloLivro;

        public Reserva(String cpf, String tituloLivro) {
            this.cpf = cpf;
            this.tituloLivro = tituloLivro;
        }

        public String getCpf() {
            return cpf;
        }

        public String getTituloLivro() {
            return tituloLivro;
        }
    }
}
