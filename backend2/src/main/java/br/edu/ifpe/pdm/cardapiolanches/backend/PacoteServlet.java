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

public class PacoteServlet extends HttpServlet {
    private final String LOG_TAG = PacoteServlet.class.getSimpleName();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        String acao = req.getParameter("acao");
        String nome =  req.getParameter("nome");
        PacoteDAO pacoteDAO = null;
        PacoteProdutoDAO pacoteProdutoDAO = null;

        try {
            Integer produtoId = Integer.parseInt(req.getParameter("produtoId"));
            Pacote pacote =  new Pacote(
                    req.getParameter("nome"),
                    Integer.parseInt(req.getParameter("tipo_pacote")),
                    req.getParameter("descricao"),
                    Float.parseFloat(req.getParameter("preco")),
                    Integer.parseInt(req.getParameter("unidade")),
                    req.getParameter("nome_image")
            );
            PacoteProduto  pacoteProduto = null;

            pacoteDAO = new PacoteDAO();
            if(acao.equals("inserir") )
            {
                pacoteDAO.salvar(pacote);
                pacoteProdutoDAO = new PacoteProdutoDAO();
                pacoteProduto =  new PacoteProduto(pacote.get_ID(),produtoId);

                pacoteProdutoDAO.salvar(pacoteProduto);
            }else if(acao.equals("consultarnome")){
                pacote =  pacoteDAO.procurarPacoteNome(nome);

            }else if(acao.equals("atualizar")){
                pacoteDAO.atualizar(pacote);
                pacoteDAO = new PacoteDAO();
                pacote=  pacoteDAO.procurarPacoteNome(nome);
            }else if(acao.equals("atualizartodos")){

            }else if(acao.equals("deletar")){
                pacoteDAO.excluir(nome);

                pacoteProdutoDAO = new PacoteProdutoDAO();
                pacoteProduto =  new PacoteProduto(pacote.get_ID(),produtoId);

                pacoteProdutoDAO.excluir(pacote.get_ID());
            }

            //System.out.println(pacote );

            resp.getWriter().write(generateJson(pacote));

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

    private String  generateJson(Pacote pacote){

        JSONObject jo = new JSONObject();


        try{
            jo.put("_id",pacote.get_ID());
            jo.put("unidade",pacote.get_ID());
            jo.put("nome",pacote.getNOME_PACOTE());
            jo.put("preco",pacote.getPRECO());
            jo.put("descricao",pacote.getDESCRICAO_PACOTE());
            jo.put("nome_imagem",pacote.getNOME_IMAGE());
            jo.put("tipo_pacote",pacote.getTIPO_PACOTE());


        }
        catch(JSONException e){ e.printStackTrace(); }

        return(jo.toString());


    }
}
