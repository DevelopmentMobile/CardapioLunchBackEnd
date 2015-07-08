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
public class FuncionarioDAO {

    private Connection conn;
    public FuncionarioDAO() throws Exception {
        try {
            this.conn = ConnectionCardapioLunch.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro: " + ":\n" + e.getMessage());
        }
    }

    public void salvar(Funcionario funcionario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (funcionario == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            String SQL = "INSERT INTO funcionario(login,senha,tipo_funcionario)"
                    + "values(?,?,?)";
            conn = this.conn;

            ps = conn.prepareStatement(SQL);
            ps.setString(1, funcionario.getLOGIN());
            ps.setString(2, funcionario.getSENHA());
            ps.setFloat(3, funcionario.getTIPO_FUNCIONARIO());


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
            String SQL = "DELETE FROM funcionario WHERE  login =?";

            ps = conn.prepareStatement(SQL);
            ps.setString(1, nome);

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao excluir dados" + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }



    public void atualizar(Funcionario funcionario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (funcionario == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {

            String SQL = "UPDATE funcionario SET login=?," + "senha=?," + "tipo_funcionario=? " + " WHERE login=? ";
            conn = this.conn;
            ps = conn.prepareStatement(SQL);

            ps.setString(1, funcionario.getLOGIN());
            ps.setString(2, funcionario.getSENHA());
            ps.setInt(3, funcionario.getTIPO_FUNCIONARIO());
            ps.setString(4, funcionario.getLOGIN());

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }


    public void atualizarTodosFuncionario(List<Funcionario> funcionario) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (funcionario == null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            for (Funcionario funcionarioTemp : funcionario) {



                String SQL = "UPDATE funcionario SET nome=?," + "descricao=?," + "preco=?,"+ "categoria=?,"
                        + "tempo_pronto=?,"+ "unidade=?,"+ "nome_imagem=?,"   + "WHERE nome=?";
                conn = this.conn;
                ps = conn.prepareStatement(SQL);

                ps.setString(1, funcionarioTemp.getLOGIN());
                ps.setString(2, funcionarioTemp.getSENHA());
                ps.setFloat(3, funcionarioTemp.getTIPO_FUNCIONARIO());

                ps.executeUpdate();
            }
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } /*finally {
                ConnectionCardapioLunch.closeConnection(conn, ps);
            }*/
    }




    public List listarTodosFuncionarioTipo(String tipo) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM funcionario WHERE tipo_funcionario = ?");
            ps.setString(1, tipo);

            ps.executeUpdate();
            rs= ps.executeQuery();
            List<Funcionario> list = new ArrayList<Funcionario>();
            while (rs.next()) {


                list.add(new Funcionario(rs.getInt("_id"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getInt("tipo_funcionario")

                        ));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }

    public List listarTodosFuncionarios() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM funcionario");


            ps.executeUpdate();
            rs= ps.executeQuery();
            List<Funcionario> list = new ArrayList<Funcionario>();
            while (rs.next()) {


                list.add(new Funcionario(rs.getInt("_id"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getInt("tipo_funcionario")

                ));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }


    public Funcionario consultarFuncionarioNome(String nome) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        Funcionario funcionario = null;
        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM funcionario WHERE login =?");
            ps.setString(1,nome);
            rs = ps.executeQuery();
            if (rs.next()) {


                funcionario  =  new Funcionario(rs.getInt("_id"),
                                rs.getString("login"),
                                rs.getString("senha"),
                                rs.getInt("tipo_funcionario"));


            }

            return funcionario;
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }



    }

}
