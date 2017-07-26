package br.com.caelum.cadastro.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ObbScanner;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.support.WebClient;

/**
 * Created by Leonardo on 26/07/2017.
 */

public class EnviaAlunosTask extends AsyncTask <Object, Object, String> {

    private final Context context;
    private ProgressDialog progress;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        String json = new AlunoConverter().toJson(alunos);
        WebClient client = new WebClient();

        return client.post(json);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(context, "Aguarde...", "Envio de dados para a web", true, true);
    }

    @Override
    protected void onPostExecute(String resposta) {
        super.onPostExecute(resposta);
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        progress.cancel();
    }
}
