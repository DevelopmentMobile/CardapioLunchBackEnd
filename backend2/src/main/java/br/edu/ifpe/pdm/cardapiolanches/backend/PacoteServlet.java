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

public class PacoteServlet extends HttpServlet {
    private final String LOG_TAG = PacoteServlet.class.getSimpleName();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        String acao = req.getParameter("acao");
        String nome =  req.getParameter("nome");
        String [] ids = req.getParameterValues("ids");
        PacoteDAO pacoteDAO = null;
        PacoteProdutoDAO pacoteProdutoDAO = null;
        List<Pacote> pacotes = null;
        try {
            //Integer produtoId = Integer.parseInt(req.getParameter("produto_id"));
            Pacote pacote =  new Pacote(
                    req.getParameter("nome"),
                    Integer.parseInt(req.getParameter("tipo_pacote")),
                    req.getParameter("descricao"),
                    Float.parseFloat(req.getParameter("preco")),
                    Integer.parseInt(req.getParameter("unidade")),
                    req.getParameter("nome_imagem")
            );
            PacoteProduto  pacoteProduto = null;

            pacoteDAO = new PacoteDAO();
            pacotes = new ArrayList<Pacote>();
            if(acao.equals("inserir") )
            {
                pacoteDAO.salvar(pacote);
                pacoteDAO = new PacoteDAO();
               int pacoteId = pacoteDAO.consultaUltimoPacote();
                for(int i =0 ; i< ids.length; i++ ) {
                    pacoteProdutoDAO  =  new PacoteProdutoDAO();
                    Integer produtoId = Integer.parseInt(ids[i]);
                    pacoteProduto = new PacoteProduto(pacoteId, produtoId);
                    pacoteProdutoDAO.salvar(pacoteProduto);

                }
                pacotes.add(pacote);
            }else if(acao.equals("consultarnome")){
                pacote =  pacoteDAO.procurarPacoteNome(nome);
                pacotes.add(pacote);
            }else if(acao.equals("atualizar")){
                pacoteDAO.atualizar(pacote);
                pacoteDAO = new PacoteDAO();
                pacote=  pacoteDAO.procurarPacoteNome(nome);
                pacotes.add(pacote);
            }else if(acao.equals("consultar")){
                pacotes = pacoteDAO.todosPacote();

            }else if(acao.equals("atualizartodos")){

            }else if(acao.equals("deletar")){
                pacote=  pacoteDAO.procurarPacoteNome(nome);
                pacoteDAO.excluir(nome);
                pacoteProdutoDAO = new PacoteProdutoDAO();
                Integer produtoId = Integer.parseInt(ids[0]);
                pacoteProduto =  new PacoteProduto(pacote.get_ID(),produtoId);
                pacoteProdutoDAO.excluir(pacote.get_ID());
                pacotes.add(pacote);
            }

            //System.out.println(pacote );

            resp.getWriter().write(generateArrayJson(pacotes));

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

    private String  generateArrayJson(List<Pacote> produtos){
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        try{

            for(Pacote pacote : produtos) {
                JSONObject aux = new JSONObject();

                aux.put("_id",pacote.get_ID());
                aux.put("unidade",pacote.get_ID());
                aux.put("nome",pacote.getNOME_PACOTE());
                aux.put("preco",pacote.getPRECO());
                aux.put("descricao",pacote.getDESCRICAO_PACOTE());
                aux.put("nome_imagem",pacote.getNOME_IMAGE());
                aux.put("tipo_pacote",pacote.getTIPO_PACOTE());
                ja.put(aux);
            }
            jo.put("pacotes", ja);

        }
        catch(JSONException e){ e.printStackTrace(); }
        return(jo.toString());
    }
}
