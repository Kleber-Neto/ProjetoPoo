 Sistema de Gerenciamento de Biblioteca - Relatório do Projeto

 Capa e Identificação

 Título do Projeto: Sistema de Gerenciamento de Biblioteca

 Integrantes da Equipe:
- Kleber Carvalho de Lima

 Local de Armazenamento do Código Fonte:
- O projeto está armazenado no GitHub: https://github.com/Kleber-Neto/ProjetoPoo.git

---

Introdução

O projeto tem como objetivo o desenvolvimento de um sistema simples de gerenciamento de biblioteca, permitindo operações de cadastro de livros, clientes, realização de empréstimos, devoluções e reservas de livros. A proposta busca simular um ambiente real de biblioteca, oferecendo funcionalidades de controle de acervo e usuários com foco em praticar conceitos de programação orientada a objetos (POO).

O sistema foi implementado com interface gráfica utilizando Java Swing, permitindo interatividade com o usuário. Cada funcionalidade está distribuída em módulos (classes e serviços), organizados em pacotes, garantindo manutenção e escalabilidade do código.

---

 Modelagem do Problema

O projeto segue uma arquitetura orientada a objetos composta por entidades principais:

- Livro: representa um item do acervo da biblioteca.
- Cliente: representa um usuário que pode realizar empréstimos e reservas.
- Emprestimo: representa a ação de empréstimo de um livro.
- Reserva: representa a intenção de um cliente de reservar um livro.

A lógica de negócio está encapsulada nas classes de serviço:
- BibliotecaService: gerencia cadastro e consulta de livros e clientes.
- EmprestimoService: gerencia empréstimos e devoluções.
- ReservaService: gerencia reservas de livros.

A interface Emprestavel define um contrato comum para itens que podem ser emprestados, permitindo aplicar polimorfismo. A classe Emprestimo implementa essa interface.

Outros conceitos aplicados:
- Encapsulamento: atributos privados e acesso via getters/setters.
- Herança e Interface: Emprestavel padroniza comportamentos de itens emprestáveis.
- Tratamento de exceções: evita operações com dados inválidos.

---

 Ferramentas Utilizadas

- IDE: Visual Studio Code
- Linguagem: Java 23+
- Interface Gráfica: Swing
- Estrutura de Pacotes:
  - main.models: classes principais (Livro, Cliente, etc.)
  - main.services: regras de negócio (serviços)
  - main.ui: interface com o usuário (BibliotecaGUI)

---

 Resultados e Considerações Finais

O sistema foi concluído com sucesso, entregando as funcionalidades principais:
- Cadastro e gerenciamento de livros e clientes
- Empréstimos e devoluções
- Sistema de reservas
- Relatório de empréstimos

 Dificuldades encontradas:
- Garantir que um livro reservado não pudesse ser emprestado por outro cliente
- Trabalhar com persistência simples usando arquivos de texto
- Organizar a lógica entre GUI, serviços e modelos

 Aprendizados:
- Maior compreensão de herança, interfaces e polimorfismo
- Prática com controle de fluxos e manipulação de coleções
- Uso de tratamento de exceções na interação com o usuário

 Considerações finais:
O sistema de biblioteca foi desenvolvido com sucesso, implementando as principais funcionalidades propostas: cadastro de livros e clientes, controle de empréstimos e devoluções, gerenciamento de reservas e geração de relatórios. A interface gráfica tornou a interação com o sistema mais intuitiva, permitindo uma experiência prática mais próxima de um sistema real.


 O projeto proporcionou uma aplicação real e concreta dos conceitos de POO. Foi possível perceber na prática a importância de modelar bem as classes, reutilizar código com herança e garantir a coesão e desacoplamento entre as partes do sistema. A experiência de criar uma interface gráfica também agregou valor, proporcionando visão completa do ciclo de desenvolvimento.

Sugestões para a disciplina:

Incluir exercícios menores e progressivos no início para praticar herança, composição e tratamento de exceções separadamente.
