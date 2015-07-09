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

public class ProdutoServlet extends HttpServlet {

    private final String LOG_TAG = ProdutoServlet.class.getSimpleName();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


       String acao = req.getParameter("acao");
       String nome =  req.getParameter("nome");
        ProdutoDAO produtoDAO = null;
        List<Produto> produtos = null;
        try {

    Produto produto =  new Produto(
            Integer.parseInt(req.getParameter("unidade")),
           nome,
            Float.parseFloat(req.getParameter("preco")),
            req.getParameter("descricao"),
            req.getParameter("nome_imagem"),
            Integer.parseInt(req.getParameter("tempo_pronto")),
            req.getParameter("categoria"));


            produtoDAO = new ProdutoDAO();
    if(acao.equals("inserir") )
    {
        produtoDAO.salvar(produto);
        produto =  produtoDAO.procurarProdutoNome(nome);
        produtos.add(produto);
    }else if(acao.equals("consultarnome")){
      produto =  produtoDAO.procurarProdutoNome(nome);
        produtos.add(produto);


    }else if(acao.equals("atualizar")){
        produtoDAO.atualizar(produto);
        produtoDAO = new ProdutoDAO();
        produto=  produtoDAO.procurarProdutoNome(nome);
        produtos.add(produto);
    }else if(acao.equals("atualizartodos")){

    }else if(acao.equals("deletar")){
        produto=  produtoDAO.procurarProdutoNome(nome);
        produtoDAO.excluir(nome);
    produtos.add(produto);
    }else if(acao.equals("consultar")){
        produtos =  new ArrayList<Produto>();
        produtos = produtoDAO.todosProduto();
    }
            resp.getWriter().write(generateArrayJson(produtos));


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

    private String  generateArrayJson(List<Produto> produtos){
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        try{

            for(Produto produto : produtos) {
                JSONObject aux = new JSONObject();
                aux.put("_id", produto.get_ID());
                aux.put("unidade", produto.getUNIDADE_ESTOQUE());
                aux.put("nome", produto.getNOME());
                aux.put("preco", produto.getPRECO());
                aux.put("descricao", produto.getDESCRICAO());
                aux.put("nome_imagem", produto.getNOME_IMAGEM());
                aux.put("tempo_pronto", produto.getTEMPO_PRONTO_PRODUTO());
                aux.put("categoria", produto.getCATEGORIA());
                ja.put(aux);
            }
            jo.put("produtos", ja);

        }
        catch(JSONException e){ e.printStackTrace(); }
        return(jo.toString());
    }


    private String  generateJson(Produto produto){
        JSONObject jo = new JSONObject();
        try{
            jo.put("_id",produto.get_ID());
            jo.put("unidade",produto.getUNIDADE_ESTOQUE());
            jo.put("nome",produto.getNOME());
            jo.put("preco",produto.getPRECO());
            jo.put("descricao",produto.getDESCRICAO());
            jo.put("nome_imagem",produto.getNOME_IMAGEM());
            jo.put("tempo_pronto",produto.getTEMPO_PRONTO_PRODUTO());
            jo.put("categoria",produto.getCATEGORIA());
        }
        catch(JSONException e){ e.printStackTrace(); }
        return(jo.toString());
    }
}
