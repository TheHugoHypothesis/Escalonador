package com.escalonador.Processos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TabelaProcessos {
    private Queue<BCP> processos_prontos = new LinkedList<>();
    private Queue<BCP> processos_bloqueados = new LinkedList<>();
    private List<BCP> processos = new ArrayList<>();

   public void adicionaProcessos(BCP bcp) {
        if (bcp != null) {
            processos.add(bcp);
        }
    }    

   public void excluiProcessos(BCP bcp) {
        if (bcp != null) {
            processos.remove(bcp);
        }
    }

    public void adicionaProcessos_prontos(BCP bcp) {
        if (bcp != null) {
            processos_prontos.add(bcp);
        }
    }

    public void trocaExecucao(BCP bcp) {
       if (bcp != null && bcp.getEstadoProcesso() == EstadoProcesso.EXECUTANDO) {
           bcp.setEstadoProcesso(EstadoProcesso.PRONTO);
           processos_prontos.add(bcp);
       }
    }

    // tem que mudar o estado do processo antes de chamar essa função
    public void bloqueiaProcessos(BCP bcp, int tempoEspera) {
        if (bcp != null) {
            bcp.setTempoEspera(tempoEspera);
            processos_bloqueados.add(bcp);
        }
    }

    // tem que mudar o estado do processo antes de chamar essa função
    public void ativaProcessos_bloqueados(BCP bcp) {
        if (bcp != null && processos_bloqueados.contains(bcp)) {
            bcp.setEstadoProcesso(EstadoProcesso.PRONTO);
            processos_bloqueados.remove(bcp);
            processos_prontos.add(bcp);
        }
    }

    public Queue<BCP> getProcessos_prontos() {return processos_prontos;}
    public List<BCP> getProcessos() {return processos;}
    public Queue<BCP> getProcessos_bloqueados() {return processos_bloqueados;}

}
