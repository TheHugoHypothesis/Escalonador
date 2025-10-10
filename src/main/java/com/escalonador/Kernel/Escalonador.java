package com.escalonador.Kernel;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.EstadoProcesso;
import com.escalonador.Processos.TabelaProcessos;

import java.util.Iterator;


public class Escalonador {
    private static final int TEMPOBLOQUEIO = 2;

    private Dispatcher dispatcher;
    private int quantum;
    private TabelaProcessos tabelaProcessos;
    private LoggerProcessos loggerProcessos;

    public float somaDeQuantumRodou;
    public float numeroDeTrocas;

	public Escalonador(int quant, TabelaProcessos tabelaProcessos, LoggerProcessos loggerProcessos) {
		this.dispatcher = new Dispatcher(loggerProcessos);
        this.quantum = quant;
        this.tabelaProcessos = tabelaProcessos;
        this.loggerProcessos = loggerProcessos;
	}

    public void iniciar(){
        while (!tabelaProcessos.getProcessos().isEmpty()) {

            diminuiTempoEspera();

            if(!tabelaProcessos.getProcessos_prontos().isEmpty()){

                BCP processo = tabelaProcessos.getProcessos_prontos().poll();
                loggerProcessos.executaProcesso(processo);

                for (int i = 0; i < quantum; i++){
                    DispatcherFeedback SaidaProcesso = dispatcher.Rodar(processo);

                    if(SaidaProcesso == DispatcherFeedback.ES){
                        tabelaProcessos.bloqueiaProcessos(processo, TEMPOBLOQUEIO);                
                        loggerProcessos.interrompeProcesso(processo, i+1);
                        somaDeQuantumRodou += i+1;
                        numeroDeTrocas++;
                        break;
                    }

                    if(SaidaProcesso == DispatcherFeedback.NADA){
                        if(i == quantum-1) {
                            tabelaProcessos.trocaExecucao(processo);
                            loggerProcessos.interrompeProcesso(processo, i+1);
                            somaDeQuantumRodou += i+1;
                            numeroDeTrocas++;
                        } 

                        continue;
                    }

                    if(SaidaProcesso == DispatcherFeedback.FEITO){
                        //Exclui processo terminados (Retornaram FEITO)

                        tabelaProcessos.excluiProcessos(processo);
                        somaDeQuantumRodou += i+1;
                        numeroDeTrocas++;

                        // REMOVER ISSO DEPOIS (NORTON?)
                        //loggerProcessos.interrompeProcesso(processo, i+1);
                        loggerProcessos.terminaProcesso(processo);

                        break;
                    }
                }
            }        
        }
    }

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

	public Dispatcher getDispatcher() {
		return dispatcher;
	}
}
