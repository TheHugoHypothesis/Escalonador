package com.escalonador.Kernel;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.TabelaProcessos;


public class Escalonador {
    private static final int TEMPOBLOQUEIO = 2;

    private Dispatcher dispatcher;
    private int quantum;
    private TabelaProcessos tabelaProcessos;
    private LoggerProcessos loggerProcessos;
    
	public Escalonador(int quant, TabelaProcessos tabelaProcessos, LoggerProcessos loggerProcessos) {
		this.dispatcher = new Dispatcher(loggerProcessos);
        this.quantum = quant;
        this.tabelaProcessos = tabelaProcessos;
        this.loggerProcessos = loggerProcessos;
	}

    public void iniciar(){
        while (!tabelaProcessos.getProcessos().isEmpty()) {
                
            if(!tabelaProcessos.getProcessos_prontos().isEmpty()){

                BCP processo = tabelaProcessos.getProcessos_prontos().poll();
                tabelaProcessos.executaProcesso(processo);

                for (int i = 0; i < quantum; i++){
                    
                    if(dispatcher.Rodar(processo) == DispatcherFeedback.ES){
                        tabelaProcessos.bloqueiaProcessos(processo, TEMPOBLOQUEIO);                
                        loggerProcessos.interrompeProcesso(processo, quantum);
                        break;
                    }

                    if(dispatcher.Rodar(processo) == DispatcherFeedback.NADA){
                        if(i == quantum-1) {
                            tabelaProcessos.trocaExecucao(processo);
                            loggerProcessos.interrompeProcesso(processo, quantum);
                        } 

                        continue;
                    }

                    if(dispatcher.Rodar(processo) == DispatcherFeedback.FEITO){
                        //Exclui processo terminados (Retornaram FEITO)
                        tabelaProcessos.excluiProcessos(processo);
                        break;
                    }
                }
            }


            diminuiTempoEspera();
            
        
        }
    }

	public void diminuiTempoEspera(){
        for (BCP processo : tabelaProcessos.getProcessos_bloqueados()) {
            processo.setTempoEspera(processo.getTempoEspera() - 1);
            if(processo.getTempoEspera() == 0){
                tabelaProcessos.ativaProcessos_bloqueados(processo);
            }
        }
    }




	public Dispatcher getDispatcher() {
		return dispatcher;
	}





}
