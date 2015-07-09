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

public class FuncionarioServlet extends HttpServlet {
    private final String LOG_TAG = FuncionarioServlet.class.getSimpleName();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String acao = req.getParameter("acao");
        String login =  req.getParameter("login");
        FuncionarioDAO funcionarioDAO = null;
        List<Funcionario> funcionarios = null;
        try {

            Funcionario funcionario =  new Funcionario(
                    req.getParameter("login"), Integer.parseInt(req.getParameter("tipo_funcionario")),
                    req.getParameter("senha")
            );

            funcionarioDAO = new FuncionarioDAO();
            funcionarios = new ArrayList<Funcionario>();
            if(acao.equals("inserir") )
            {
                funcionarioDAO.salvar(funcionario);
                funcionarioDAO = new FuncionarioDAO();
                funcionario=  funcionarioDAO.consultarFuncionarioNome(login);
                funcionario.setACAO("inserir");
                funcionarios.add(funcionario);
            }else if(acao.equals("consultarnome")){
                funcionario = funcionarioDAO.consultarFuncionarioNome(login);
            }else if(acao.equals("consultarnomesenha")){
                funcionario = funcionarioDAO.consultarFuncionarioNomeSenha(funcionario.getLOGIN(),funcionario.getSENHA());
                funcionario.setACAO("consultarnomesenha");
                funcionarios.add(funcionario);
            }else if(acao.equals("atualizar")){
                funcionarioDAO.atualizar(funcionario);
                funcionarioDAO = new FuncionarioDAO();
                funcionario=  funcionarioDAO.consultarFuncionarioNome(login);
                funcionarios.add(funcionario);
            }else if(acao.equals("atualizartodos")){

            }else if(acao.equals("deletar")){
                funcionarioDAO.excluir(login);
            }else if(acao.equals("consultartodos")){
                funcionarios = funcionarioDAO.listarTodosFuncionarios();

            }


            resp.getWriter().write(generateJson(funcionarios));

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

    }
    private String generateJson(List<Funcionario> funcionarios){

        JSONObject joList =new JSONObject();
        JSONArray ja = new JSONArray();

        try{
            for(Funcionario funcionario:funcionarios) {
                JSONObject  jo = new JSONObject();
                jo.put("_id", funcionario.get_ID());
                jo.put("login", funcionario.getLOGIN());
                jo.put("senha", funcionario.getSENHA());
                jo.put("tipo_funcionario", funcionario.getTIPO_FUNCIONARIO());
                jo.put("acao", funcionario.getACAO());
                ja.put(jo);
            }
            joList.put("funcionarios",ja);
        }
        catch(JSONException e){ e.printStackTrace(); }
        return(joList.toString());
    }
}
