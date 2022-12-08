package com.example.proyectocodm08;

import java.util.ArrayList;
import java.util.List;

public class ObjetoRepositorio {

    List<Objeto> objetos = new ArrayList<>();

    interface Callback {
        void cuandoFinalice(List<Objeto> objetos);
    }

    List<Objeto> obtener() {
        return objetos;
    }

    void insertar(Objeto objeto, Callback callback){
        objetos.add(objeto);
        callback.cuandoFinalice(objetos);
    }

    void eliminar(Objeto objeto, Callback callback) {
        objetos.remove(objeto);
        callback.cuandoFinalice(objetos);
    }
}