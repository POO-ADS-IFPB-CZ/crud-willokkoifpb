package view;

import dao.GenericDao;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Set;

/**
 * Tela principal da aplicação para gerenciar produtos.
 */
public class TelaPrincipal extends JFrame {

    private GenericDao<Produto> produtoDao;
    private JTable tabela;
    private DefaultTableModel tableModel;

    public TelaPrincipal() {
        // --- Configurações da Janela ---
        setTitle("Cadastro de Produtos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // --- Inicializando o DAO ---
        try {
            // O arquivo "produtos.dat" será criado na raiz do projeto.
            produtoDao = new GenericDao<>("produtos.dat");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao iniciar o arquivo de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Fecha a aplicação se não conseguir criar o arquivo
        }

        // --- Criando os Componentes da Interface ---
        // Modelo da tabela, define as colunas
        tableModel = new DefaultTableModel(new Object[]{"Código", "Descrição", "Preço"}, 0);
        tabela = new JTable(tableModel);

        // Painel com os botões
        JPanel painelBotoes = new JPanel();
        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoAtualizar = new JButton("Atualizar");
        JButton botaoRemover = new JButton("Remover");
        
        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoAtualizar);
        painelBotoes.add(botaoRemover);

        // --- Layout da Janela ---
        // Adiciona a tabela (com barra de rolagem) ao centro e os botões na parte inferior
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- Ações dos Botões ---
        botaoAdicionar.addActionListener(e -> adicionarProduto());
        botaoAtualizar.addActionListener(e -> atualizarProduto());
        botaoRemover.addActionListener(e -> removerProduto());

        // Carrega os dados iniciais do arquivo na tabela
        carregarTabela();
    }

    /**
     * Carrega os produtos do arquivo e atualiza a JTable.
     */
    private void carregarTabela() {
        // Limpa a tabela antes de carregar novos dados
        tableModel.setRowCount(0);
        try {
            Set<Produto> produtos = produtoDao.getAll();
            for (Produto p : produtos) {
                tableModel.addRow(new Object[]{p.getCodigo(), p.getDescricao(), "R$ " + String.format("%.2f", p.getPreco())});
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lógica para adicionar um novo produto.
     */
    private void adicionarProduto() {
        try {
            String codigoStr = JOptionPane.showInputDialog(this, "Digite o código do produto:", "Adicionar Produto", JOptionPane.PLAIN_MESSAGE);
            if (codigoStr == null) return; // Usuário cancelou

            String descricao = JOptionPane.showInputDialog(this, "Digite a descrição do produto:", "Adicionar Produto", JOptionPane.PLAIN_MESSAGE);
            if (descricao == null) return;

            String precoStr = JOptionPane.showInputDialog(this, "Digite o preço do produto:", "Adicionar Produto", JOptionPane.PLAIN_MESSAGE);
            if (precoStr == null) return;

            int codigo = Integer.parseInt(codigoStr);
            double preco = Double.parseDouble(precoStr.replace(",", ".")); // Aceita vírgula ou ponto

            Produto novoProduto = new Produto(codigo, descricao, preco);

            if (produtoDao.salvar(novoProduto)) {
                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarTabela(); // Atualiza a tabela
            } else {
                JOptionPane.showMessageDialog(this, "Já existe um produto com este código.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código e preço devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lógica para remover um produto selecionado.
     */
    private void removerProduto() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) tableModel.getValueAt(linhaSelecionada, 0);
        
        // Cria um produto temporário apenas com o código para a remoção funcionar (baseado no equals)
        Produto produtoParaRemover = new Produto(codigo, "", 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o produto de código " + codigo + "?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (produtoDao.remover(produtoParaRemover)) {
                    JOptionPane.showMessageDialog(this, "Produto removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarTabela(); // Atualiza a tabela
                } else {
                     JOptionPane.showMessageDialog(this, "Produto não encontrado para remoção.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Erro ao remover o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Lógica para atualizar um produto selecionado.
     */
    private void atualizarProduto() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pega os dados atuais da linha selecionada
        int codigo = (int) tableModel.getValueAt(linhaSelecionada, 0);
        String descricaoAtual = (String) tableModel.getValueAt(linhaSelecionada, 1);
        
        // Extrai o valor numérico do preço
        String precoAtualStr = (String) tableModel.getValueAt(linhaSelecionada, 2);
        double precoAtual = Double.parseDouble(precoAtualStr.replace("R$ ", "").replace(",", "."));
        
        try {
            // Pede os novos dados ao usuário, já mostrando os dados atuais
            String novaDescricao = JOptionPane.showInputDialog(this, "Nova descrição:", descricaoAtual);
            if (novaDescricao == null) return;

            String novoPrecoStr = JOptionPane.showInputDialog(this, "Novo preço:", String.valueOf(precoAtual));
            if (novoPrecoStr == null) return;

            double novoPreco = Double.parseDouble(novoPrecoStr.replace(",", "."));

            // Cria o objeto produto com os dados atualizados (o código não muda)
            Produto produtoAtualizado = new Produto(codigo, novaDescricao, novoPreco);

            if (produtoDao.atualizar(produtoAtualizado)) {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarTabela(); // Atualiza a tabela
            } else {
                 JOptionPane.showMessageDialog(this, "Erro: produto não encontrado para atualização.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O preço deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}