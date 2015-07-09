/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package br.edu.ifpe.pdm.cardapiolanches.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PedidoServlet extends HttpServlet {
    private final String LOG_TAG = PedidoServlet.class.getSimpleName();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {



        List<Pedido> pedidos = null;
        PedidoDAO PedidoDAO = null;
        try {

            String acao = req.getParameter("acao");
            int tam = req.getParameterValues("num_pedido").length;
            String [] tempo_total_pedido =   req.getParameterValues("tempo_total");
            String [] quantidade =   req.getParameterValues("quantidade");
            String [] num_mesa =   req.getParameterValues("num_mesa");
            String [] funcionario_id =   req.getParameterValues("funcionario_id");
            String [] pacote_id =   req.getParameterValues("pacote_id");
            String [] produto_id =   req.getParameterValues("produto_id");
            String [] status_pedido =   req.getParameterValues("status_pedido");
            String [] num_pedido =   req.getParameterValues("num_pedido");



           List<Pedido> pedidosReq = new ArrayList<Pedido>();
           for(int i=0; i<tam; i++) {
               Pedido pedido = new Pedido(
                       Integer.parseInt(tempo_total_pedido[i]),
                       Integer.parseInt(quantidade[i]),
                       Integer.parseInt(num_mesa[i]),
                       Integer.parseInt(funcionario_id[i]),
                       Integer.parseInt(produto_id[i]),
                       Integer.parseInt(pacote_id[i]),
                       Integer.parseInt(status_pedido[i]),
                       Integer.parseInt(num_pedido[i])
                       );
               pedidosReq.add(pedido);
           }

            PedidoDAO = new PedidoDAO();
            pedidos = new ArrayList<Pedido>();
            if(acao.equals("inserir") )
            {
                PedidoDAO.salvar(pedidosReq.get(0));
                PedidoDAO = new PedidoDAO();
                int ultimoPedido = PedidoDAO.consultaUltimoPedido();
                PedidoDAO = new PedidoDAO();
                System.out.println(ultimoPedido);
                PedidoDAO.gerarNumPedido(ultimoPedido, pedidosReq.get(0).getNUM_MESA());

                pedidosReq.get(0).set_ID(ultimoPedido);
                pedidosReq.get(0).setACAO("inserir");
                pedidos.add(pedidosReq.get(0));

            }else if(acao.equals("consultarnumpedido")){
                pedidos.set(0, PedidoDAO.consultarPedidoNumPedido(pedidosReq.get(0).getNUM_PEDIDO()));
                pedidos.get(0).setACAO("consultarnumpedido");

            }else if(acao.equals("consultarpedidostatus")){
                pedidos  =PedidoDAO.consultarPedidoStatus(pedidosReq.get(0).getSTATUS_PEDIDO());
                pedidos.get(0).setACAO("consultarpedidostatus");
            }else if(acao.equals("consultartodospedidos")){
                pedidos  =PedidoDAO.consultarTodosPedidos();
                pedidos.get(0).setACAO("consultartodospedidos");
            }else if(acao.equals("atualizar")){
                PedidoDAO.atualizarNumPedido( pedidosReq.get(0));
                PedidoDAO = new PedidoDAO();
                pedidos.set(0,PedidoDAO.consultarPedidoNumPedido( pedidosReq.get(0).getNUM_PEDIDO()));
                pedidos.add( pedidos.get(0));
                pedidos.get(0).setACAO("atualizar");
            }else if(acao.equals("atualizartodos")){

                    PedidoDAO.atualizarTodos(pedidosReq);
                    PedidoDAO = new PedidoDAO();
                    pedidos = PedidoDAO.consultarTodosPedidos();
                pedidos.get(0).setACAO("atualizartodos");


            }else if(acao.equals("deletar")){
                PedidoDAO.excluir(pedidosReq.get(0).getNUM_PEDIDO());
                PedidoDAO = new PedidoDAO();
                pedidos.set(0,PedidoDAO.consultarPedidoNumPedido( pedidosReq.get(0).getNUM_PEDIDO()));
                pedidos.add( pedidos.get(0));
                pedidos.get(0).setACAO("deletar");

            }

            //System.out.println(pedido );

            resp.getWriter().write(generateJson(pedidos));

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

    private String  generateJson(List<Pedido> pedidos){

        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        try{

            for(Pedido pedido : pedidos) {
                JSONObject aux = new JSONObject();
                aux.put("_id", pedido.get_ID());
                aux.put("funcionario_id", pedido.getFUNCIONARIO_ID());
                aux.put("pacote_id", pedido.getPACOTE_ID());
                aux.put("num_mesa", pedido.getNUM_MESA());
                aux.put("num_pedido", pedido.getNUM_PEDIDO());
                aux.put("produto_id", pedido.getPRODUTO_ID());
                aux.put("quantidade", pedido.getQUANTIDADE());
                aux.put("status_pedido", pedido.getSTATUS_PEDIDO());
                aux.put("tempo_total", pedido.getTEMPO_TOTAL_PEDIDO());
                aux.put("acao", pedidos.get(0).getACAO());
                ja.put(aux);

            }
            jo.put("pedidos", ja);
        }
        catch(JSONException e){ e.printStackTrace(); }

        return(jo.toString());


    }
}
