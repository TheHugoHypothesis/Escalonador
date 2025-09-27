package com.escalonador.Processos;

import java.util.ArrayList;
import java.util.List;

public class TabelaProcessos {
    private List<BCP> processos = new ArrayList<>();

    public void adicionaProcesso(BCP bcp) {
        if (bcp != null) {
            processos.add(bcp);
        }
    }

    public List<BCP> getProcessos() {
        return processos;
    }
}
