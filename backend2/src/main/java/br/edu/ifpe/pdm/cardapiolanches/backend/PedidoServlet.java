/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package br.edu.ifpe.pdm.cardapiolanches.backend;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PedidoServlet extends HttpServlet {
    private final String LOG_TAG = PedidoServlet.class.getSimpleName();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        String acao = req.getParameter("acao");
        String num_pedido = req.getParameter("num_pedido");

        PedidoDAO PedidoDAO = null;
        try {
                
            Pedido pedido = new Pedido(Integer.parseInt(  req.getParameter("_id")),
                   Integer.parseInt( req.getParameter("tempo_total_pedido")),
                    Integer.parseInt (req.getParameter("quantidade")),
                    Integer.parseInt( req.getParameter("num_mesa")),
                    Integer.parseInt(req.getParameter("funcionario_id")),
                            Integer.parseInt(req.getParameter("pedido_id")),
                                    Integer.parseInt(req.getParameter("pacote_id")),
                                            Integer.parseInt(req.getParameter("status_pedido")),
                                                    Integer.parseInt(req.getParameter("num_pedido")
            ));


            PedidoDAO = new PedidoDAO();
            if(acao.equals("inserir") )
            {
                PedidoDAO.salvar(pedido);
            }else if(acao.equals("consultarnome")){
                pedido =  PedidoDAO.consultarPedidoNumPedido(pedido.getNUM_PEDIDO().toString());

            }else if(acao.equals("atualizar")){
                PedidoDAO.atualizarNumPedido(pedido);
                PedidoDAO = new PedidoDAO();
                pedido=  PedidoDAO.consultarPedidoNumPedido(num_pedido);
            }else if(acao.equals("atualizartodos")){

            }else if(acao.equals("deletar")){
                PedidoDAO.excluir(num_pedido);
            }

            //System.out.println(pedido );

            resp.getWriter().write(generateJson(pedido));

        }catch (Exception e){
            e.printStackTrace();

        }



    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("Hello " + name);
    }

    private String  generateJson(Pedido pedido){

        JSONObject jo = new JSONObject();


        try{
            jo.put("_id",pedido.get_ID());
            jo.put("funcionario_id",pedido.getFUNCIONARIO_ID());
            jo.put("pacote_id",pedido.getPACOTE_ID());
            jo.put("num_mesa",pedido.getNUM_MESA());
            jo.put("num_pedido",pedido.getNUM_PEDIDO());
            jo.put("produto_id",pedido.getPRODUTO_ID());
            jo.put("quantidade",pedido.getQUANTIDADE());
            jo.put("status_pedido",pedido.getSTATUS_PEDIDO());
            jo.put("tempo_total",pedido.getTEMPO_TOTAL_PEDIDO());


        }
        catch(JSONException e){ e.printStackTrace(); }

        return(jo.toString());


    }
}
