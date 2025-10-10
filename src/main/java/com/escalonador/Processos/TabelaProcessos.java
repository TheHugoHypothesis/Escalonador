package com.escalonador.Processos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TabelaProcessos {
    private Queue<BCP> processos_prontos = new LinkedList<>();
    private Queue<BCP> processos_bloqueados = new LinkedList<>();
    private List<BCP> processos = new ArrayList<>();

    /* Função de carregar processos novos. */
    public void adicionaProcessos(BCP bcp) {
        if (bcp != null) {
            processos.add(bcp);
        }
    }

    /* Função de remover processos concluídos. */
    public void excluiProcessos(BCP bcp) {
        if (bcp != null) {
            processos.remove(bcp);
        }
    }

    /* Função de para adicionar processos a lista de prontos. */
    public void adicionaProcessos_prontos(BCP bcp) {
        if (bcp != null) {
            processos_prontos.add(bcp);
        }
    }

    /* Troca a execução do processo atual para um novo, marcando-o como PRONTO. */
    public void trocaExecucao(BCP bcp) {
        if (bcp != null && bcp.getEstadoProcesso() == EstadoProcesso.EXECUTANDO) {
            bcp.setEstadoProcesso(EstadoProcesso.PRONTO);
            processos_prontos.add(bcp);
        }
    }

    /* Bloqueia processo para E/S por um tempo de espera. */
    public void bloqueiaProcessos(BCP bcp, int tempoEspera) {
        if (bcp != null) {
            bcp.setTempoEspera(tempoEspera);
            processos_bloqueados.add(bcp);
        }
    }

    public Queue<BCP> getProcessos_prontos() { return processos_prontos; }
    public List<BCP> getProcessos() { return processos; }
    public Queue<BCP> getProcessos_bloqueados() { return processos_bloqueados; }
}
