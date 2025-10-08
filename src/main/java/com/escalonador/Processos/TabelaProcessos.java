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

    public void executaProcesso(BCP bcp) {
       if (bcp != null && bcp.getEstadoProcesso() == EstadoProcesso.PRONTO) {
           bcp.setEstadoProcesso(EstadoProcesso.EXECUTANDO);
           processos_prontos.remove(bcp);
       }
    }

    public void trocaExecucao(BCP bcp) {
       if (bcp != null && bcp.getEstadoProcesso() == EstadoProcesso.EXECUTANDO) {
           processos_prontos.add(bcp);
           bcp.setEstadoProcesso(EstadoProcesso.PRONTO);
       }
    }
    
    // tem que ser chamado quando um processo vai ser executado
    public void excluiProcessos_prontos(BCP bcp)  {
        if (bcp != null) {
            processos_prontos.remove(bcp);
        }
    }

    // tem que mudar o estado do processo antes de chamar essa função
    public void bloqueiaProcessos(BCP bcp, int tempoEspera) {
        if (bcp != null && bcp.getEstadoProcesso() == EstadoProcesso.EXECUTANDO) {
            processos_bloqueados.add(bcp);
            bcp.setTempoEspera(tempoEspera);
            bcp.setEstadoProcesso(EstadoProcesso.BLOQUEADO);
        }
    }

    // tem que mudar o estado do processo antes de chamar essa função
    public void ativaProcessos_bloqueados(BCP bcp) {
        if (bcp != null && processos_bloqueados.contains(bcp)) {
            processos_bloqueados.remove(bcp);
            processos_prontos.add(bcp);
            bcp.setEstadoProcesso(EstadoProcesso.PRONTO);
        }
    }

    public Queue<BCP> getProcessos_prontos() {return processos_prontos;}
    public List<BCP> getProcessos() {return processos;}
    public Queue<BCP> getProcessos_bloqueados() {return processos_bloqueados;}
}
