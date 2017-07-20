package br.com.caelum.cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    private List<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        //String[] alunos = {"Anderson", "Filipe", "Smith", "Guilherme", "Maria", "Fatima", "Sandra"};

        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();

        final ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);

        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        this.listaAlunos.setAdapter(adapter);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                Toast.makeText(ListaAlunosActivity.this, "Posição selecionada: "+posicao, Toast.LENGTH_LONG).show();
            }

        });

        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
                String aluno = (String) adapter.getItemAtPosition(posicao);
                Toast.makeText(ListaAlunosActivity.this, "Click longo: "+ aluno, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        Button botaoAdicionar = (Button) findViewById(R.id.lista_aluno_floating_button);

        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intenção de chamar outra activity
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                // Dar um start na Activity com a intenção.
                startActivity(intent);

            }
        });
    }
}
