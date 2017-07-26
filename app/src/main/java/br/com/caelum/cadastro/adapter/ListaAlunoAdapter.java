package br.com.caelum.cadastro.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6920 on 24/07/17.
 */

public class ListaAlunoAdapter extends BaseAdapter {
    private final List<Aluno>  alunos;
    private final Activity activity;


    public ListaAlunoAdapter(Activity activity, List<Aluno> alunos) {
        this.activity = activity;
        this.alunos = alunos;
    }
    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        Aluno aluno = alunos.get(posicao);
        view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        Bitmap bm;

        if(aluno.getCaminhoFoto() != null) {
            bm = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
            bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }

        nome.setText(aluno.getNome());

        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        foto.setImageBitmap(bm);
        return view;
    }
    @Override
    public int getCount() {
        return alunos.size();
    }
    @Override
    public Object getItem(int posicao) {
        return alunos.get(posicao);
    }
    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

}
