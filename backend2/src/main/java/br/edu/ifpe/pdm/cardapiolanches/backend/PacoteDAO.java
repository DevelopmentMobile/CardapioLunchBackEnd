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
public class PacoteDAO {

    private Connection conn;

    public PacoteDAO() throws Exception {
        try {
            this.conn = ConnectionCardapioLunch.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro: " + ":\n" + e.getMessage());
        }
    }

    public void salvar(Pacote Pacote) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (Pacote == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            String SQL = "INSERT INTO pacote(nome,descricao,preco,tipo_pacote,unidade, nome_imagem)"
                    + "values(?,?,?,?,?,?)";
            conn = this.conn;

            ps = conn.prepareStatement(SQL);
            ps.setString(1, Pacote.getNOME_PACOTE());
            ps.setString(2, Pacote.getDESCRICAO_PACOTE());
            ps.setFloat(3, Pacote.getPRECO());
            ps.setInt(4, Pacote.getTIPO_PACOTE());
            ps.setInt(5, Pacote.getUNIDADE());
            ps.setString(6, Pacote.getNOME_IMAGE());


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
            String SQL = "DELETE FROM pacote WHERE  nome =?";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, nome);

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao excluir dados" + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }



    public void atualizar(Pacote Pacote) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (Pacote == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {

            String SQL = "UPDATE pacote SET nome=?," + "descricao=?," + "preco=?,"+ "tipo_pacote=?,"
                    + "unidade=?,"+ "nome_imagem=? "   + " WHERE nome=? ";
            conn = this.conn;
            ps = conn.prepareStatement(SQL);

            ps = conn.prepareStatement(SQL);
            ps.setString(1, Pacote.getNOME_PACOTE());
            ps.setString(2, Pacote.getDESCRICAO_PACOTE());
            ps.setFloat(3, Pacote.getPRECO());
            ps.setInt(4, Pacote.getTIPO_PACOTE());
            ps.setInt(5, Pacote.getUNIDADE());
            ps.setString(6, Pacote.getNOME_IMAGE());

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }


    public void atualizarTodos(List<Pacote> Pacote) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (Pacote == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            for (Pacote PacoteList : Pacote) {



                String SQL = "UPDATE pacote SET nome=?," + "descricao=?," + "preco=?,"+ "tipo_pacote=?,"
                        + "unidade=?,"+ "nome_imagem=? "   + " WHERE nome=? ";
                conn = this.conn;
                ps = conn.prepareStatement(SQL);

                ps = conn.prepareStatement(SQL);
                ps.setString(1, PacoteList.getNOME_PACOTE());
                ps.setString(2, PacoteList.getDESCRICAO_PACOTE());
                ps.setFloat(3, PacoteList.getPRECO());
                ps.setInt(4, PacoteList.getTIPO_PACOTE());
                ps.setInt(5, PacoteList.getUNIDADE());
                ps.setString(6, PacoteList.getNOME_IMAGE());


                ps.executeUpdate();
            }
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } /*finally {
                ConnectionCardapioLunch.closeConnection(conn, ps);
            }*/
    }




    public List todosPacoteTipoProduto(String categoria) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM pacote WHERE tipo_produto = ?");
            ps.setString(1, categoria);

            ps.executeUpdate();
            rs= ps.executeQuery();
            List<Pacote> list = new ArrayList<Pacote>();
            while (rs.next()) {


                list.add(new Pacote(rs.getInt("_id"),
                        rs.getString("nome"),
                        rs.getInt("tipo_pacote"),
                        rs.getString("descricao"),
                        rs.getFloat("preco"),
                        rs.getInt("unidade"),
                        rs.getString("nome_image")
                ));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }

    public List todosPacote() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM pacote");


            ps.executeUpdate();
            rs= ps.executeQuery();
            List<Pacote> list = new ArrayList<Pacote>();
            while (rs.next()) {

   list.add(new Pacote(rs.getInt("_id"),
                        rs.getString("nome"),
                        rs.getInt("tipo_pacote"),
                        rs.getString("descricao"),
                        rs.getFloat("preco"),
                        rs.getInt("unidade"),
                        rs.getString("nome_image")
                       ));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }


    public Pacote procurarPacoteNome(String nome) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        Pacote Pacote = null;
        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM pacote WHERE nome =?");
            ps.setString(1,nome);
            rs = ps.executeQuery();
            if (rs.next()) {


                Pacote  =  new Pacote(rs.getInt("_id"),
                        rs.getString("nome"),
                        rs.getInt("tipo_pacote"),
                        rs.getString("descricao"),
                        rs.getFloat("preco"),
                        rs.getInt("unidade"),
                        rs.getString("nome_image")
                );


            }

            return Pacote;
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }



    }
    
}
