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

public class FuncionarioServlet extends HttpServlet {
    private final String LOG_TAG = FuncionarioServlet.class.getSimpleName();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        String acao = req.getParameter("acao");
        String login =  req.getParameter("login");
        FuncionarioDAO funcionarioDAO = null;
        try {

            Funcionario funcionario =  new Funcionario(
           req.getParameter("login"),  Integer.parseInt(req.getParameter("tipo_funcionario")),
                   req.getParameter("senha")

               );


            funcionarioDAO = new FuncionarioDAO();
            if(acao.equals("inserir") )
            {
                funcionarioDAO.salvar(funcionario);
            }else if(acao.equals("consultarnome")){
                funcionario = funcionarioDAO.consultarFuncionarioNome(login);

            }else if(acao.equals("atualizar")){
                funcionarioDAO.atualizar(funcionario);
                funcionarioDAO = new FuncionarioDAO();
                funcionario=  funcionarioDAO.consultarFuncionarioNome(login);
            }else if(acao.equals("atualizartodos")){

            }else if(acao.equals("deletar")){
                funcionarioDAO.excluir(login);
            }

            //System.out.println(Funcionario );

            resp.getWriter().write(generateJson(funcionario));

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

    private String  generateJson(Funcionario funcionario){

        JSONObject jo = new JSONObject();


        try{
            jo.put("_id",funcionario.get_ID());
            jo.put("login",funcionario.getLOGIN());
            jo.put("senha",funcionario.getSENHA());
            jo.put("tipo_funcionario",funcionario.getTIPO_FUNCIONARIO());



        }
        catch(JSONException e){ e.printStackTrace(); }

        return(jo.toString());


    }
}
