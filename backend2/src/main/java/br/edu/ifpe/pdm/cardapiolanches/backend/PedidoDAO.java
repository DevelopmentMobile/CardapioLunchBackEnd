package br.edu.ifpe.pdm.cardapiolanches.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Richardson on 04/07/2015.
 */
public class PedidoDAO {


    private Connection conn;

    public PedidoDAO() throws Exception {
        try {
            this.conn = ConnectionCardapioLunch.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro: " + ":\n" + e.getMessage());
        }
    }

    public void salvar(Pedido pedido) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (pedido== null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            String SQL = "INSERT INTO pedido(produto_id,funcionario_id,pacote_id,status_pedido,quantidade, data_hora_pedido_solicitado,data_hora_pedido_estimado, tempo_total_pedido,num_mesa)"
                    + "values(?,?,?,?,?,?,?,?,?)";
            conn = this.conn;

            ps = conn.prepareStatement(SQL);
            ps.setInt(1, pedido.getPRODUTO_ID());
            ps.setInt(2, pedido.getFUNCIONARIO_ID());
            ps.setInt(3, pedido.getPACOTE_ID());
            ps.setInt(4, pedido.getSTATUS_PEDIDO());
            ps.setInt(5, pedido.getQUANTIDADE());
            long timeNow = Calendar.getInstance().getTimeInMillis();
            long timeAfter = timeNow + (pedido.getTEMPO_TOTAL_PEDIDO() * 60000) ;
            java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
            java.sql.Timestamp ta = new java.sql.Timestamp(timeAfter);
            ps.setTimestamp(6, ts);
            ps.setTimestamp(7, ta);
            ps.setInt(8, pedido.getTEMPO_TOTAL_PEDIDO());
            ps.setInt(9, pedido.getNUM_MESA());


            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao inserir dados" + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }


    public void excluir(Integer numPedido) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (numPedido == null) {
            throw new Exception("O valor passado não pode ser nulo");
        } try{
            conn = this.conn;
            String SQL = "DELETE FROM pedido WHERE  num_pedido =?";

            ps = conn.prepareStatement(SQL);
            ps.setInt(1, numPedido);

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao excluir dados" + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }



    public void atualizarNumPedido(Pedido pedido) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (pedido== null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {

            String SQL = "UPDATE pedido SET produto_id=?," + "funcionario_id=?," + "pacote_id=?,"+ "status_pedido=?,"
                    + "quantidade=?"   + " WHERE num_pedido=? ";
            conn = this.conn;
            ps = conn.prepareStatement(SQL);

            ps = conn.prepareStatement(SQL);
            ps.setInt(1, pedido.getPRODUTO_ID());
            ps.setInt(2, pedido.getFUNCIONARIO_ID());
            ps.setInt(3, pedido.getPACOTE_ID());
            ps.setInt(4, pedido.getSTATUS_PEDIDO());
            ps.setInt(5, pedido.getQUANTIDADE());
            ps.setString(6, pedido.getNUM_MESA() + "" + pedido.getNUM_PEDIDO());


            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }

    public int consultaUltimoPedido() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        int PedidoId = -1;
        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT _id FROM pedido ORDER BY _id ASC");


            //ps.executeUpdate();
            rs= ps.executeQuery();
            List<Pedido> list = new ArrayList<Pedido>();

            while (rs.next()) {

                if(rs.last()) {
                    PedidoId = rs.getInt("_id");
                }
            }


        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }

        return PedidoId;
    }



    public void atualizaStatusPedido(String numPedido , Integer status) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;

        try {

            String SQL = "UPDATE pedido SET status_pedido=? WHERE num_pedido=? ";
            conn = this.conn;
            ps = conn.prepareStatement(SQL);

            ps.setInt(1, status);
            ps.setString(2, numPedido);


            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }

    public void gerarNumPedido(int idPedido, Integer numMesa ) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;

        try {

            String SQL = "UPDATE pedido SET num_pedido=? WHERE _id=? ";
            conn = this.conn;
            ps = conn.prepareStatement(SQL);
            String num_pedido = numMesa.toString() + idPedido;
            ps.setString(1,  num_pedido);
            ps.setInt(2, idPedido);



            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps);
        }
    }


    public void atualizarTodos(List<Pedido> pedido) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        if (pedido== null) {
            throw new Exception("O valor passado não pode ser nulo");
        }
        try {
            for (Pedido pedidoList : pedido) {



                String SQL = "UPDATE pedido SET produto_id=?," + "funcionario_id=?," + "pacote_id=?,"+ "status_pedido=?,"
                        + "quantidade=?"   + " WHERE num_pedido=? ";
                conn = this.conn;
                ps = conn.prepareStatement(SQL);

                ps = conn.prepareStatement(SQL);

                ps.setInt(1, pedidoList.getPRODUTO_ID());
                ps.setInt(2, pedidoList.getFUNCIONARIO_ID());
                ps.setInt(3, pedidoList.getPACOTE_ID());
                ps.setInt(4, pedidoList.getSTATUS_PEDIDO());
                ps.setInt(5, pedidoList.getQUANTIDADE());

                ps.executeUpdate();
            }
        } catch (SQLException sqle) {
            throw new Exception("Erro ao atualizar dados " + sqle);
        } /*finally {
                ConnectionCardapioLunch.closeConnection(conn, ps);
            }*/
    }




    public List consultarNumeroPedido(String numPedido) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM pedido WHERE num_pedido = ?");
            ps.setString(1, numPedido);
            rs= ps.executeQuery();
            List<Pedido> list = new ArrayList<Pedido>();
            while (rs.next()) {


                list.add(new Pedido(rs.getInt("_id"),
                        rs.getInt("tempo_total_pedido"),
                        rs.getInt("quantidade"),
                        rs.getInt("num_mesa"),
                        rs.getInt("funcionario_id"),
                        rs.getInt("produto_id"),
                        rs.getInt("pacote_id"),
                        rs.getInt("status_pedido"),
                        rs.getInt("num_pedido")
                ));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }

    public List<Pedido> consultarPedidoStatus(Integer status) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM pedido WHERE status = ?");

            ps.setInt(1, status);
            rs= ps.executeQuery();
            List<Pedido> list = new ArrayList<Pedido>();
            while (rs.next()) {


                list.add(new Pedido(rs.getInt("_id"),
                        rs.getInt("tempo_total_pedido"),
                        rs.getInt("quantidade"),
                        rs.getInt("num_mesa"),
                        rs.getInt("funcionario_id"),
                        rs.getInt("produto_id"),
                        rs.getInt("pacote_id"),
                        rs.getInt("status_pedido"),
                        rs.getInt("num_pedido")
                ));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }


    public List consultarTodosPedidos() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM pedido");


            ps.executeUpdate();
            rs= ps.executeQuery();
            List<Pedido> list = new ArrayList<Pedido>();
            while (rs.next()) {

                list.add(new Pedido(rs.getInt("_id"),
                        rs.getInt("tempo_total_pedido"),
                        rs.getInt("quantidade"),
                        rs.getInt("num_mesa"),
                        rs.getInt("funcionario_id"),
                        rs.getInt("produto_id"),
                        rs.getInt("pacote_id"),

                        rs.getInt("status_pedido"),
                        rs.getInt("num_pedido")







                ));
            }
            return list;

        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }
    }


    public Pedido consultarPedidoNumPedido(Integer numPedido) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        Pedido pedido = null;
        try {
            conn = this.conn;
            ps = conn.prepareStatement("SELECT * FROM pedido WHERE num_pedido =?");
            ps.setInt(1, numPedido);
            rs = ps.executeQuery();
            if (rs.next()) {


                pedido =     new Pedido(rs.getInt("_id"),
                        rs.getInt("tempo_total_pedido"),
                        rs.getInt("quantidade"),
                        rs.getInt("num_mesa"),
                        rs.getInt("funcionario_id"),
                        rs.getInt("produto_id"),
                        rs.getInt("pacote_id"),

                        rs.getInt("status_pedido"),
                        rs.getInt("num_pedido")







                );

            }

            return pedido;
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } finally {
            ConnectionCardapioLunch.closeConnection(conn, ps, rs);
        }



    }
    
    
}
