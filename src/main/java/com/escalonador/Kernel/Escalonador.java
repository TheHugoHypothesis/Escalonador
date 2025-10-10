package com.escalonador.Kernel;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.EstadoProcesso;
import com.escalonador.Processos.TabelaProcessos;

import java.util.Iterator;


public class Escalonador {
    private static final int TEMPO_BLOQUEIO = 2;

    private Dispatcher dispatcher;
    private int quantum;
    private TabelaProcessos tabelaProcessos;
    private LoggerProcessos loggerProcessos;

    public float somaDeQuantumRodou;
    public float numeroDeTrocas;
    public float TempoFinalizacaoSoma;
    public int numeroInteracoes = 0;

	public Escalonador(int quant, TabelaProcessos tabelaProcessos, LoggerProcessos loggerProcessos) {
		this.dispatcher = new Dispatcher(loggerProcessos);
        this.quantum = quant;
        this.tabelaProcessos = tabelaProcessos;
        this.loggerProcessos = loggerProcessos;
	}

    /* Metodo para rodar os processos dado ao Escalonador */
    public void iniciar() {
        while (!tabelaProcessos.getProcessos().isEmpty()) {
            diminuiTempoEspera();

            if(!tabelaProcessos.getProcessos_prontos().isEmpty()){
                BCP processo = tabelaProcessos.getProcessos_prontos().poll();
                loggerProcessos.executaProcesso(processo);

                for (int i = 0; i < quantum; i++) {
                    DispatcherFeedback SaidaProcesso = dispatcher.Rodar(processo);

                    if (SaidaProcesso == DispatcherFeedback.ES) {
                        /* Escalonador recebe feedback que processo pediu E/S. */
                        tabelaProcessos.bloqueiaProcessos(processo, TEMPO_BLOQUEIO);
                        loggerProcessos.interrompeProcesso(processo, i+1);
                        somaDeQuantumRodou += i+1;
                        numeroDeTrocas++;
                        break;
                    }

                    if (SaidaProcesso == DispatcherFeedback.NADA) {
                        /* Escalonador verifica se é o último valor do quantum dado ao processo. */
                        if (i == quantum - 1) {
                            tabelaProcessos.trocaExecucao(processo);
                            loggerProcessos.interrompeProcesso(processo, i + 1);
                            somaDeQuantumRodou += i + 1;
                            numeroDeTrocas++;
                        } 

                        continue;
                    }

                    if (SaidaProcesso == DispatcherFeedback.FEITO) {
                        /* Escalonador verifica se está terminado. */
                        tabelaProcessos.excluiProcessos(processo);
                        somaDeQuantumRodou += i + 1;
                        numeroDeTrocas++;

                        loggerProcessos.terminaProcesso(processo);
                        TempoFinalizacaoSoma += numeroDeTrocas;

                        break;
                    }

                    numeroInteracoes++;
                }
            }        
        }
    }

    /*
    * Após cada ciclo de clock, essa função serve para diminuir o tempo
    * rodado dos processos bloqueados.
    *  */
	public void diminuiTempoEspera() {
        Iterator<BCP> iterator = tabelaProcessos.getProcessos_bloqueados().iterator();

        while (iterator.hasNext()) {
            BCP processo = iterator.next();
            processo.setTempoEspera(processo.getTempoEspera() - 1);

            if (processo.getTempoEspera() == 0) {
                iterator.remove();
                processo.setEstadoProcesso(EstadoProcesso.PRONTO);
                tabelaProcessos.adicionaProcessos_prontos(processo);
            }
        }
    }
}
