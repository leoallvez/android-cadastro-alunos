package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RatingBar;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6920 on 19/07/17.
 */

public class FormularioHelper {
    private Aluno aluno;
    private EditText nome;
    private EditText telefone;
    private EditText site;
    private RatingBar nota;
    private EditText endereco;

    private ImageView foto;
    private Button fotoButton;

    public FormularioHelper(FormularioActivity activity) {
        this.aluno = new Aluno();

        this.nome = (EditText) activity.findViewById(R.id.formulario_nome);
        this.telefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        this.site  = (EditText) activity.findViewById(R.id.formulario_site);
        this.nota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        this.endereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        //Fotos
        this.foto = (ImageView) activity.findViewById(R.id.formulario_foto);
        this.fotoButton = (Button) activity.findViewById(R.id.formulario_foto_button);
    }

    public Aluno pegaAlunoDoFormulario() {

        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        // Recuperando o caminho da foto na tag da foto.
        aluno.setCaminhoFoto((String) foto.getTag());

        return aluno;
    }

    public void colocarNoFormurario(Aluno aluno) {

        nome.setText( aluno.getNome());
        endereco.setText(aluno.getEndereco());
        site.setText(aluno.getSite());
        telefone.setText(aluno.getTelefone());
        nota.setProgress(aluno.getNota().intValue());
        if(aluno.getCaminhoFoto() != null) {
            carregarImagem(aluno.getCaminhoFoto());
        }
        this.aluno = aluno;
    }

    public Button getFotoButton() {
        return fotoButton;
    }

    public void carregarImagem(/**caminho da imagem*/String localAquivoFoto) {
        // Criar um bitmap para carregar a foto na memória.
        Bitmap imagemFoto = BitmapFactory.decodeFile(localAquivoFoto);
        // Redimensionando a foto.
        Bitmap imagemFotoReduziada = Bitmap.createScaledBitmap(imagemFoto, /**imagemFoto.getWidth() ou */ 400 , 300, true);
        //Setando a foto na ImagemView.
        this.foto.setImageBitmap(imagemFotoReduziada);
        //Seta o caminho da foto na ImagemView.
        //setTag salva uma informação em qualquer view.
        this.foto.setTag(localAquivoFoto);
        // Ajustando a foto na altura e largura de ImagemView.
        this.foto.setScaleType(ScaleType.FIT_XY);
    }
}
