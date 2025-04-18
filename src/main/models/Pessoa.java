package main.models;

public abstract class Pessoa {
    private String name;
    private String cpf;

    public Pessoa(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    // Getters e Setters (Encapsulamento)
    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    // Método abstrato (Polimorfismo)
    public abstract void apresentar();
}
