package main.services;

import main.models.Emprestimo;
import java.util.List;
import java.util.ArrayList;

public class EmprestimoService {
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public void realizarEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

    // Método getter
    public List<Emprestimo> getEmprestimos() {
        return new ArrayList<>(emprestimos); // Retorna cópia da lista
    }
}
