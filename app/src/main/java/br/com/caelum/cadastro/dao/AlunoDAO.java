package br.com.caelum.cadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6920 on 20/07/17.
 */

public class AlunoDAO extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private static final String TABELA = "Alunos";
    private static final String DATABASE = "CadastroCaelum";

    public AlunoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }
    /**
     *  Os tamanhos dos campos da tabela são dinamicos;
     *  Uma boa ideia seria usar o atributos da tabela como constantes
     *  SQLite é um banco bem restrito.
     */
    public void onCreate(SQLiteDatabase database) {
        String ddl = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT NOT NULL, "
                + " telefone TEXT, "
                + " endereco TEXT, "
                + " site TEXT, "
                + " nota REAL);";
        // Rodando o script acima;
        database.execSQL(ddl);
    }
    /** Atualizando a base */
    public void onUpgrade(SQLiteDatabase database, int versaoAntiga, int versaoNova) {
        String sql = "DROP TABLE IF EXIST " + TABELA;
        database.execSQL(sql);
        // Criando novamente a base.
        onCreate(database);
    }
    /** Insert do Aluno*/
    public void insere(Aluno aluno) {
        // ContentValues é usado para se evitar SQL Injection
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        // getWriteDatabas() recupera a instância da tabela
        getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Aluno> getLista() {

        List<Aluno> alunos = new ArrayList<Aluno>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABELA + ";", null);

        
        while(c.moveToNext()) {
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }
}
