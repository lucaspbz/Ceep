package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        todasNotas = notasDeExemplo();
        configuraRecyclerView(todasNotas);

        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFormularioNotaActivity = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                startActivityForResult(vaiParaFormularioNotaActivity, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 2 && data.hasExtra("nota")) {
            Nota notaCriada = (Nota) data.getSerializableExtra("nota");
            NotaDAO dao = new NotaDAO();
            dao.insere(notaCriada);
            adapter.adiciona(notaCriada);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Primeira nota", "Descrição pequena"),
                new Nota("Segunda nota", "Segunda descrição é bem maior que a da primeira nota"));
        return dao.todos();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> notas, RecyclerView view) {
        adapter = new ListaNotasAdapter(this, notas);
        view.setAdapter(adapter);
    }
}
