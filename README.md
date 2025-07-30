[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/qNmwDDQV)

# Cadastro de Produtos em Java Swing

Este é um projeto simples de uma aplicação Desktop em Java para realizar as quatro operações básicas de um cadastro (CRUD - Criar, Ler, Atualizar e Excluir) de produtos.

A aplicação utiliza uma interface gráfica construída com **Java Swing** e salva os dados localmente em um arquivo binário, utilizando o mecanismo de serialização do Java.

---

### Funcionalidades

* **Adicionar Produtos**: Permite inserir um novo produto com código, descrição e preço.
* **Listar Produtos**: Exibe todos os produtos salvos numa tabela na tela principal.
* **Atualizar Produtos**: Permite editar a descrição e o preço de um produto já existente.
* **Remover Produtos**: Exclui um produto selecionado da base de dados.
* **Persistência de Dados**: Os produtos são salvos no arquivo `produtos.dat`, que é criado automaticamente na raiz do projeto.

---

### Tecnologias e Conceitos Utilizados

* **Java**: Linguagem de programação principal.
* **Java Swing**: Biblioteca para a criação da interface gráfica (GUI).
* **Java Serializable**: Para converter os objetos `Produto` em uma sequência de bytes e salvá-los em arquivo.
* **Padrão de Projeto DAO (Data Access Object)**: Para separar a lógica de negócio da lógica de acesso aos dados, utilizando a classe `GenericDao`.
