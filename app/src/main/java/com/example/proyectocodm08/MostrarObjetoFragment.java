package com.example.proyectocodm08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.proyectocodm08.databinding.FragmentMostrarObjetoBinding;

public class MostrarObjetoFragment extends Fragment {
    private FragmentMostrarObjetoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarObjetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObjetosViewModel objetosViewModel = new ViewModelProvider(requireActivity()).get(ObjetosViewModel.class);

        objetosViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Objeto>() {
            @Override
            public void onChanged(Objeto objeto) {
                binding.nombre.setText(objeto.nombre);
                binding.descripcion.setText(objeto.descripcion);
            }
        });
    }
}