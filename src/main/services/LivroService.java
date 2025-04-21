// Arquivo: LivroService.java
package main.services;

import main.models.Livro;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivroService {
    private List<Livro> livros = new ArrayList<>();

    public void adicionarLivro(String titulo, String autor, int ano) {
        livros.add(new Livro(titulo, autor, ano));
    }

    public List<Livro> listarLivros() {
        return new ArrayList<>(livros);
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    public boolean atualizarLivro(String tituloAntigo, String novoTitulo, String novoAutor, int novoAno) {
        Livro livro = buscarLivroPorTitulo(tituloAntigo);
        if (livro == null)
            return false;

        livros.remove(livro);
        livros.add(new Livro(novoTitulo, novoAutor, novoAno));
        return true;
    }

    public boolean removerLivro(String titulo) {
        Optional<Livro> livroOpt = livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();

        livroOpt.ifPresent(livros::remove);
        return livroOpt.isPresent();
    }

    public void limparLivros() {
        livros.clear();
    }
}