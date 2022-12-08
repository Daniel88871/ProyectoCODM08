package com.example.proyectocodm08;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ObjetosViewModel extends AndroidViewModel {

    ObjetoRepositorio objetoRepositorio;

    MutableLiveData<List<Objeto>> listObjetosMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Objeto> objetoSeleccionado = new MutableLiveData<>();

    public ObjetosViewModel(@NonNull Application application) {
        super(application);

        objetoRepositorio = new ObjetoRepositorio();

        listObjetosMutableLiveData.setValue(objetoRepositorio.obtener());
    }

    MutableLiveData<List<Objeto>> obtener(){
        return listObjetosMutableLiveData;
    }

    void insertar(Objeto objeto){
        objetoRepositorio.insertar(objeto, new ObjetoRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Objeto> objetos) {
                listObjetosMutableLiveData.setValue(objetos);
            }
        });
    }

    void eliminar(Objeto objeto){
        objetoRepositorio.eliminar(objeto, new ObjetoRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Objeto> objetos) {
                listObjetosMutableLiveData.setValue(objetos);
            }
        });
    }

    void seleccionar(Objeto objeto){
        objetoSeleccionado.setValue(objeto);
    }

    MutableLiveData<Objeto> seleccionado(){
        return objetoSeleccionado;
    }
}
