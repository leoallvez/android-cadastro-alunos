package br.com.caelum.cadastro;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private String localArquivo;
    private final static int TIRA_FOTO = 123;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        /**
        Button botao = (Button) findViewById(R.id.formulario_botao);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FormularioActivity.this, "Você clicou no botão", Toast.LENGTH_SHORT).show();
                // Finalizar activity atual.
                finish();
            }
        });
        */

        this.helper = new FormularioHelper(this);

        //Botão de tirar foto;
        //Pegar a referencia do botão na classe helper
        Button foto = this.helper.getFotoButton();
        // Açção do botão
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pegar o caminho da memória externa;
                // parametro null é para subdiretório.
                // System.currentTimeMillis() para ter um nome único para o arquivo.
                localArquivo = getExternalFilesDir(null)+"/"+ System.currentTimeMillis()+".jpg";
                //  Converter o aquivo para ser passado por uma Uri.
                Uri localFoto = Uri.fromFile(new File(localArquivo));
                // Declarando Intent implicita.
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // passadno a uri para a intent.
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
                //Chamando de fato a intent
                startActivityForResult(irParaCamera, TIRA_FOTO);
            }
        });

        Intent intent = getIntent();
        // Pega o aluno da intent que vem da tela de listagem
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null) {
            helper.colocarNoFormurario(aluno);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    /**Explicação do professor
     @Override public boolean onCreateOptionsMenu(Menu menu) {
     //Add um item no menu.
     //menu.app("Ok"); //não é o ideal pois não é uma ação.
     MenuInflater inflater = getMenuInflater();
     // Pegar o XML para gerar o menu
     inflater.inflate(R.menu.menu_formulario, menu);

     return true;
     }*/
    /**
     * Explicação do apostila
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Explicação do professor
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Definindo qual intem do menu foi selecionado.
        if (item.getItemId() == R.id.menu_formulario_ok) {
            // Instância do alunoDaAO
            AlunoDAO dao = new AlunoDAO(this);

            Aluno aluno = helper.pegaAlunoDoFormulario();
            //Toast.makeText(this, "Nome do aluno: "+aluno.getNome(), Toast.LENGTH_SHORT).show();

            if (aluno.getId() == null) {
                //Inserir aluno na base
                dao.insere(aluno);
            } else {
                dao.altera(aluno);
            }

            // Fechando a conexão com o banco
            dao.close();
            // Fechando a ativity
            finish();
            return true;
        }
        return false;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Formulario Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    /**
     @Override public boolean onOptionsItemSelected(MenuItem item) {
     switch(item.getItemId()) {
     case R.id.menu_formulario_ok : Toast.makeText(this,"OK Clicado", Toast.LENGTH_SHORT).show();
     finish();
     return false;
     default:
     return super.onOptionsItemSelected(item);
     }
     }*/

    // Callback da função de tirar foto
    @Override
    protected void onActivityResult(/**Codigo da request*/int requestCode, /**Codigo da request*/ int resultCode,/**Dados de retorno*/ Intent data){

        if(requestCode == TIRA_FOTO) {
            if(resultCode == RESULT_OK) {
                // Carregar a imagem no formulario
                helper.carregarImagem(this.localArquivo);
            }
        }else{
            // Apagando o caminho da foto
            this.localArquivo = null;
        }
    }
}
