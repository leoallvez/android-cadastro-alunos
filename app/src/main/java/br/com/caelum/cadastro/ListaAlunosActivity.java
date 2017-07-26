package br.com.caelum.cadastro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.adapter.ListaAlunoAdapter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private List<Aluno> alunos;
    private Aluno alunoSelecionado;
    private static final int REQUEST_LIGACAO = 123;

    private void carregarLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        ListaAlunoAdapter adapter = new ListaAlunoAdapter(this, alunos);

        this.listaAlunos.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        //String[] alunos = {"Anderson", "Filipe", "Smith", "Guilherme", "Maria", "Fatima", "Sandra"};

        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();

        //final ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);

        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        //this.listaAlunos.setAdapter(adapter);

        registerForContextMenu(listaAlunos);

        listaAlunos.setOnItemClickListener(new OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                //Toast.makeText(ListaAlunosActivity.this, "Posição selecionada: "+posicao, Toast.LENGTH_LONG).show();
                // Intent para chamar a próxima tela.
                Intent editar = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                //Pegar o aluno
                Aluno aluno = (Aluno) adapter.getItemAtPosition(posicao);
                // Associando o aluno a intent
                editar.putExtra("aluno", aluno);

                startActivity(editar);
            }

        });

        /*listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
                String aluno = (String) adapter.getItemAtPosition(posicao);
                Toast.makeText(ListaAlunosActivity.this, "Click longo: "+ aluno, Toast.LENGTH_LONG).show();
                return false;
            }
        });*/

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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterContextMenuInfo info = (AdapterContextMenuInfo)  menuInfo;
        //Final para ser possivel usar o alunoSelecionado na classe anonima.
        alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);
        // Criando um item do menu.
        MenuItem excluir = menu.add("Excluir");
        // Listener do botão de excluir.
        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deletar(alunoSelecionado);
                dao.close();
                carregarLista();
                return true;
            }
        });

        MenuItem ligar = menu.add("Ligar");
        // Intent implicita para fazer uma ligação.
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String permissaoLiga = Manifest.permission.CALL_PHONE;

                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, permissaoLiga) == PackageManager.PERMISSION_GRANTED){
                    fazerLigacao();
                }else{
                    // Ultimo paramentro é o request code
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{ permissaoLiga }, REQUEST_LIGACAO );
                }
                return false;
            }
        });

        Intent fazerligacao = new Intent(Intent.ACTION_CALL);

        fazerligacao.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));

        ligar.setIntent(fazerligacao);

        MenuItem sms = menu.add("Enviar SMS");

        Intent enviarSms = new Intent(Intent.ACTION_VIEW);

        enviarSms.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
        enviarSms.putExtra("sms_body", "Olá Aluno");

        sms.setIntent(enviarSms);

        MenuItem mapa = menu.add("Achar no mapa");

        Intent abrirMapa = new Intent(Intent.ACTION_VIEW);
        // Tirar os caracteres especiais do endereço
        abrirMapa.setData(Uri.parse("geo:0,0?z=14&q="+ Uri.encode(alunoSelecionado.getEndereco())));

        mapa.setIntent(abrirMapa);

        MenuItem site = menu.add("Navegar no site");

        Intent abrirSite = new Intent(Intent.ACTION_VIEW);

        abrirSite.setData(Uri.parse("http://"+ alunoSelecionado.getSite()));

        site.setIntent(abrirSite);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.carregarLista();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] resultados) {
        if(requestCode == REQUEST_LIGACAO) {
            if(resultados[0] == PackageManager.PERMISSION_GRANTED) {
                fazerLigacao();
            }else{
                Toast.makeText(ListaAlunosActivity.this,"Não foi possivel fazer ligação ", Toast.LENGTH_LONG).show();
            }
        }

    }
    @SuppressWarnings({"MissingPermissions"})
    private void fazerLigacao() {
        Intent ligar = new Intent(Intent.ACTION_CALL);
        ligar.setData(Uri.parse("tel:"+ alunoSelecionado.getTelefone()));
        if(true)
            startActivity(ligar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_lista_alunos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_nota: {
                AlunoDAO dao = new AlunoDAO(this);
                List<Aluno> alunos = dao.getLista();
                dao.close();

                String json = new AlunoConverte().toJson(alunos);
                WebClient client = new WebClient();
                String reposta = client.post(json);
                Toast.makeText(this, reposta, Toast.LENGTH_LONG).show();
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
