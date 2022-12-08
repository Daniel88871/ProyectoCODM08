package com.example.proyectocodm08;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocodm08.databinding.FragmentRecyclerObjetosBinding;
import com.example.proyectocodm08.databinding.ViewholderObjetoBinding;

import java.util.List;

public class RecyclerObjetosFragment extends Fragment {

    private FragmentRecyclerObjetosBinding binding;
    private ObjetosViewModel objetosViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRecyclerObjetosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        objetosViewModel = new ViewModelProvider(requireActivity()).get(ObjetosViewModel.class);
        navController = Navigation.findNavController(view);

        binding.irANuevoObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_recyclerObjetosFragment_to_nuevoObjetoFragment);
            }
        });

        ObjetosAdapter objetosAdapter = new ObjetosAdapter();

        binding.recyclerView.setAdapter(objetosAdapter);

        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Objeto objeto = objetosAdapter.obtenerObjeto(posicion);
                objetosViewModel.eliminar(objeto);

            }
        }).attachToRecyclerView(binding.recyclerView);

        objetosViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Objeto>>() {
            @Override
            public void onChanged(List<Objeto> objetos) {
                objetosAdapter.establecerLista(objetos);
            }
        });
    }

    class ObjetosAdapter extends RecyclerView.Adapter<ObjetoViewHolder> {

        List<Objeto> objetos;

        @NonNull
        @Override
        public ObjetoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ObjetoViewHolder(ViewholderObjetoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ObjetoViewHolder holder, int position) {

            Objeto objeto = objetos.get(position);

            holder.binding.nombre.setText(objeto.nombre);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objetosViewModel.seleccionar(objeto);
                    navController.navigate(R.id.action_recyclerObjetosFragment_to_mostrarObjetoFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return objetos != null ? objetos.size() : 0;
        }

        public void establecerLista(List<Objeto> objetos){
            this.objetos = objetos;
            notifyDataSetChanged();
        }

        public Objeto obtenerObjeto(int posicion){
            return objetos.get(posicion);
        }
    }

    static class ObjetoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderObjetoBinding binding;

        public ObjetoViewHolder(ViewholderObjetoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}