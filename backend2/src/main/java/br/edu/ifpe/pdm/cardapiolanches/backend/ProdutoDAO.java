package br.edu.ifpe.pdm.cardapiolanches.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Richardson on 04/07/2015.
 */
public class ProdutoDAO {


    private Connection conn;
    public ProdutoDAO() throws Exception {
        try {
            this.conn = ConnectionCardapioLunch.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro: " + ":\n" + e.getMessage());
        }
    }

    public void salvar(Produto produto) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (produto == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            String SQL = "INSERT INTO produto(nome,descricao,preco,categoria,tempo_pronto,unidade, nome_imagem)"
                    + "values(?,?,?,?,?,?,?)";
            conn = this.conn;

            ps = conn.prepareStatement(SQL);
            ps.setString(1, produto.getNOME());
            ps.setString(2, produto.getDESCRICAO());
            ps.setFloat(3, produto.getPRECO());
            ps.setString(4, produto.getCATEGORIA());
            ps.setInt(5, produto.getTEMPO_PRONTO_PRODUTO());
            ps.setInt(6, produto.getUNIDADE_ESTOQUE());
            ps.setString(7, produto.getNOME_IMAGEM());

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao inserir dados" + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }


    public void excluir(String nome) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (nome == null) {
            throw new Exception("O valor passado não pode ser nulo");
        } try{
            conn = this.conn;
            String SQL = "DELETE FROM produto WHERE  nome =?";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, nome);

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao excluir dados" + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }



    public void atualizar(Produto produto) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (produto == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {

            String SQL = "UPDATE produto SET nome=?," + "descricao=?," + "preco=?,"+ "categoria=?,"
                    + "tempo_pronto=?,"+ "unidade=?,"+ "nome_imagem=? "   + " WHERE nome=? ";
            conn = this.conn;
            ps = conn.prepareStatement(SQL);

            ps.setString(1, produto.getNOME());
            ps.setString(2, produto.getDESCRICAO());
            ps.setFloat(3, produto.getPRECO());
            ps.setString(4, produto.getCATEGORIA());
            ps.setInt(5, produto.getTEMPO_PRONTO_PRODUTO());
            ps.setInt(6, produto.getUNIDADE_ESTOQUE());
            ps.setString(7, produto.getNOME_IMAGEM());
            ps.setString(8, produto.getNOME());

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }


    public void atualizarTodos(List<Produto> Produto) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (Produto == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            for (Produto ProdutoList : Produto) {



                String SQL = "UPDATE produto SET nome=?," + "descricao=?," + "preco=?,"+ "categoria=?,"
                        + "tempo_pronto=?,"+ "unidade=?,"+ "nome_imagem=?,"   + "WHERE nome=?";
                conn = this.conn;
                ps = conn.prepareStatement(SQL);

                ps.setString(1, ProdutoList.getNOME());
                ps.setString(2, ProdutoList.getDESCRICAO());
                ps.setFloat(3, ProdutoList.getPRECO());
                ps.setString(4, ProdutoList.getCATEGORIA());
                ps.setInt(5, ProdutoList.getTEMPO_PRONTO_PRODUTO());
                ps.setInt(6, ProdutoList.getUNIDADE_ESTOQUE());
                ps.setString(7, ProdutoList.getNOME_IMAGEM());
                ps.setString(8, ProdutoList.getNOME());

                ps.executeUpdate();
            }
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } /*finally {
                ConnectionCardapioLunch.closeConnection(conn, ps);
            }*/
    }




    public List todosProdutoCategoria(String categoria) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM produto WHERE categoria = ?");
            ps.setString(1, categoria);

            ps.executeUpdate();
            rs= ps.executeQuery();
            List<Produto> list = new ArrayList<Produto>();
            while (rs.next()) {
                String isbn = rs.getString(1);
                String titulo = rs.getString(2);
                int edicao = rs.getInt("edicao_num");
                String publicacao = rs.getString("ano_publicacao");

                list.add(new Produto(rs.getInt("_id"),
                        rs.getInt("unidade"),
                        rs.getString("nome"),
                        rs.getFloat("preco"),
                        rs.getString("descricao"),
                        rs.getString("nome_imagem"),
                        rs.getInt("tempo_pronto"),
                        rs.getString("categoria")));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }

    public List todosProduto() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM produto");


            ps.executeUpdate();
            rs= ps.executeQuery();
            List<Produto> list = new ArrayList<Produto>();
            while (rs.next()) {
                String isbn = rs.getString(1);
                String titulo = rs.getString(2);
                int edicao = rs.getInt("edicao_num");
                String publicacao = rs.getString("ano_publicacao");

                list.add(new Produto(rs.getInt("_id"),
                        rs.getInt("unidade"),
                        rs.getString("nome"),
                        rs.getFloat("preco"),
                        rs.getString("descricao"),
                        rs.getString("nome_imagem"),
                        rs.getInt("tempo_pronto"),
                        rs.getString("categoria")));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }


    public Produto procurarProdutoNome(String nome) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        Produto produto = null;
        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM produto WHERE nome =?");
            ps.setString(1,nome);
            rs = ps.executeQuery();
            if (rs.next()) {


                produto  =  new Produto(rs.getInt("_id"),
                        rs.getInt("unidade"),
                        rs.getString("nome"),
                        rs.getFloat("preco"),
                        rs.getString("descricao"),
                        rs.getString("nome_imagem"),
                        rs.getInt("tempo_pronto"),
                        rs.getString("categoria"));


            }

            return produto;
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }



    }
}
