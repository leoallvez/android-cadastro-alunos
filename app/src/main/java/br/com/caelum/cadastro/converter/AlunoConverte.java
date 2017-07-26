package br.com.caelum.cadastro.converter;

import com.google.gson.Gson;

import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6920 on 25/07/17.
 */

public class AlunoConverte {

    public String toJson (List<Aluno> alunos) {
        Gson gson = new Gson();
        String jsonString = "{list:[{aluno:" + gson.toJson(alunos) + "}]}";
        return jsonString;
    };
}
