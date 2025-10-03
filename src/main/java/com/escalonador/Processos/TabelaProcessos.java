package com.escalonador.Processos;

import java.util.ArrayList;
import java.util.List;

public class TabelaProcessos {
    private List<BCP> processos_prontos = new ArrayList<>();
    private List<BCP> processos_bloqueados = new ArrayList<>();


    public void adicionaProcessos_prontos(BCP bcp) {
        if (bcp != null) {
            processos_prontos.add(bcp);
        }
    }


    public void excluiProcessos_prontos(BCP bcp){
        if (bcp != null) {
            processos_prontos.remove(bcp);
        }
    }


    public List<BCP> getProcessos_prontos() {
        return processos_prontos;
    }


}
