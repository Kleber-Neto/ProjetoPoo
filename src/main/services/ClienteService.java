// Arquivo: ClienteService.java
package main.services;

import main.models.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private List<Cliente> clientes = new ArrayList<>();

    public void adicionarCliente(String nome, String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos.");
        }
        if (buscarClientePorCPF(cpf) != null) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }
        clientes.add(new Cliente(nome, cpf));
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public Cliente buscarClientePorCPF(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equalsIgnoreCase(cpf))
                .findFirst()
                .orElse(null);
    }

    public boolean atualizarCliente(String cpf, String novoNome) {
        Cliente cliente = buscarClientePorCPF(cpf);
        if (cliente == null)
            return false;

        clientes.remove(cliente);
        clientes.add(new Cliente(novoNome, cpf));
        return true;
    }

    public boolean removerCliente(String cpf) {
        Optional<Cliente> clienteOpt = clientes.stream()
                .filter(c -> c.getCpf().equalsIgnoreCase(cpf))
                .findFirst();

        clienteOpt.ifPresent(clientes::remove);
        return clienteOpt.isPresent();
    }

    public void limparClientes() {
        clientes.clear();
    }
}