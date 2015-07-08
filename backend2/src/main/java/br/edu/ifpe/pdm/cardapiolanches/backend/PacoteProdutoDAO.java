package br.edu.ifpe.pdm.cardapiolanches.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Richardson on 04/07/2015.
 */
public class PacoteProdutoDAO {

    private Connection conn;

    public PacoteProdutoDAO() throws Exception {
        try {
            this.conn = ConnectionCardapioLunch.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro: " + ":\n" + e.getMessage());
        }
    }

    public void salvar(PacoteProduto pacoteProduto) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (pacoteProduto == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            String SQL = "INSERT INTO pacote_produto(pacote_id,produto_id)"
                    + "values(?,?)";
            conn = this.conn;

            ps = conn.prepareStatement(SQL);
            ps.setInt(2, pacoteProduto.getPACOTE_ID());
            ps.setInt(3, pacoteProduto.getPRODUTO_ID());


            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao inserir dados" + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }


    public void excluir(Integer pacoteId) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (pacoteId == null) {
            throw new Exception("O valor passado não pode ser nulo");
        } try{
            conn = this.conn;
            String SQL = "DELETE FROM pacote_produto WHERE pacote_id =?";

            ps = conn.prepareStatement(SQL);
            ps.setInt(1, pacoteId);

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao excluir dados" + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }



}
