package br.edu.ifpe.pdm.cardapiolanches.dao;

import java.util.List;

import br.edu.ifpe.pdm.cardapiolanches.bean.Funcionario;

/**
 * Created by Richardson on 05/07/2015.
 */
public interface FuncionarioListener {

    public void showFuncionario(List< Funcionario> funcionarios);
}
