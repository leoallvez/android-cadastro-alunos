package br.com.caelum.cadastro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Button botao = (Button) findViewById(R.id.formulario_botao);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FormularioActivity.this, "Você clicou no botão", Toast.LENGTH_SHORT).show();
                // Finalizar activity atual.
                finish();
            }
        });
    }
    /**Explicação do professor
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Add um item no menu.
        //menu.app("Ok"); //não é o ideal pois não é uma ação.
        MenuInflater inflater = getMenuInflater();
        // Pegar o XML para gerar o menu
        inflater.inflate(R.menu.menu_formulario, menu);

        return true;
    }*/
    /**Explicação do apostila*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**Explicação do professor
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Definindo qual intem do menu foi selecionado.
        if(item.getItemId() == R.id.menu_formulario_ok) {
            finish();
            return true;
        }
        return false;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_formulario_ok : Toast.makeText(this,"OK Clicado", Toast.LENGTH_SHORT).show();
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
