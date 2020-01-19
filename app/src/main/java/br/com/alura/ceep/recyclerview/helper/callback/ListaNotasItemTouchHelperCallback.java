package br.com.alura.ceep.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class ListaNotasItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaNotasAdapter adapter;

    public ListaNotasItemTouchHelperCallback(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int marcacoesDeArrastar = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        NotaDAO dao = new NotaDAO();
        dao.troca(posicaoInicial,posicaoFinal);
        adapter.troca(posicaoInicial,posicaoFinal);


        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        NotaDAO dao = new NotaDAO();
        int posicaoArrastada = viewHolder.getAdapterPosition();
        dao.remove(posicaoArrastada);
        adapter.remove(posicaoArrastada);

    }
}
