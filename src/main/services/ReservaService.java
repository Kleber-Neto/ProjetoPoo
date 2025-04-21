// Arquivo: ReservaService.java (CRUD completo com relatório)
package main.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservaService {
    public static class Reserva {
        private String cpf;
        private String tituloLivro;
        private LocalDate dataReserva;

        public Reserva(String cpf, String tituloLivro, LocalDate dataReserva) {
            this.cpf = cpf;
            this.tituloLivro = tituloLivro;
            this.dataReserva = dataReserva;
        }

        public String getCpf() {
            return cpf;
        }

        public String getTituloLivro() {
            return tituloLivro;
        }

        public LocalDate getDataReserva() {
            return dataReserva;
        }

        public void setTituloLivro(String novoTitulo) {
            this.tituloLivro = novoTitulo;
        }

        public void setDataReserva(LocalDate novaData) {
            this.dataReserva = novaData;
        }

        @Override
        public String toString() {
            return "Livro: " + tituloLivro + " | CPF: " + cpf + " | Data: " + dataReserva;
        }
    }

    private List<Reserva> reservas = new ArrayList<>();

    public void adicionarReserva(String cpf, String tituloLivro) {
        if (cpf == null || tituloLivro == null || cpf.isEmpty() || tituloLivro.isEmpty()) {
            throw new IllegalArgumentException("CPF e título são obrigatórios.");
        }

        boolean jaReservado = reservas.stream()
                .anyMatch(r -> r.getCpf().equals(cpf) && r.getTituloLivro().equalsIgnoreCase(tituloLivro));

        if (jaReservado) {
            throw new IllegalArgumentException("Este livro já está reservado por este cliente.");
        }

        reservas.add(new Reserva(cpf, tituloLivro, LocalDate.now()));
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    public boolean atualizarReserva(String cpf, String tituloAntigo, String novoTitulo, LocalDate novaData) {
        Optional<Reserva> reservaOpt = reservas.stream()
                .filter(r -> r.getCpf().equals(cpf) && r.getTituloLivro().equalsIgnoreCase(tituloAntigo))
                .findFirst();

        if (reservaOpt.isEmpty())
            return false;

        Reserva r = reservaOpt.get();
        r.setTituloLivro(novoTitulo);
        r.setDataReserva(novaData);
        return true;
    }

    public void cancelarReserva(String cpf, String tituloLivro) {
        Optional<Reserva> reservaOpt = reservas.stream()
                .filter(r -> r.getCpf().equals(cpf) && r.getTituloLivro().equalsIgnoreCase(tituloLivro))
                .findFirst();

        if (reservaOpt.isEmpty()) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }

        reservas.remove(reservaOpt.get());
    }

    public void limparReservas() {
        reservas.clear();
    }

    public String gerarRelatorioReservas() {
        if (reservas.isEmpty())
            return "Nenhuma reserva registrada.";

        StringBuilder sb = new StringBuilder("\n=== RELATÓRIO DE RESERVAS ===\n");
        for (Reserva r : reservas) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }
}