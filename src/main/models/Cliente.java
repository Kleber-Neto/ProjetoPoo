package main.models;

public class Cliente extends Pessoa {
    public Cliente(String nome, String cpf) {
        super(nome, cpf);
    }

    @Override
    public void apresentar() {
        System.out.println("Cliente: " + getName() + " | CPF: " + getCpf());
    }
}
