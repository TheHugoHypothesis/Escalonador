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
    
    // tem que ser chamado quando um processo vai ser executado
    public void excluiProcessos_prontos(BCP bcp){
        if (bcp != null) {
            processos_prontos.remove(bcp);
        }
    }

    // tem que mudar o estado do processo antes de chamar essa função
    public void bloqueiaProcessos(BCP bcp) {
        if (bcp != null) {
            processos_bloqueados.add(bcp);
        }
    }

    // tem que mudar o estado do processo antes de chamar essa função
    public void ativaProcessos_bloqueados(BCP bcp) {
        if (bcp != null && processos_bloqueados.contains(bcp)) {
            processos_bloqueados.remove(bcp);
            processos_prontos.add(bcp);
        }
    }

    public List<BCP> getProcessos_prontos() {return processos_prontos;}

    public List<BCP> getProcessos_bloqueados() {return processos_bloqueados;}
}
