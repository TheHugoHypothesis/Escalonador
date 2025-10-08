package com.escalonador.Kernel;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.EstadoProcesso;
import com.escalonador.Processos.TabelaProcessos;

public class Escalonador {

    private Dispatcher dispatcher;
    private int quantum;
    private TabelaProcessos tabelaProcessos;


    
	public Escalonador(int quant, TabelaProcessos tabelaProcessos, LoggerProcessos loggerProcessos) {
		this.dispatcher = new Dispatcher(loggerProcessos);
        this.quantum = quant;
        this.tabelaProcessos = tabelaProcessos;
	}




    public void iniciar(){


        while (!tabelaProcessos.getProcessos().isEmpty()) {
                
            if(!tabelaProcessos.getProcessos_prontos().isEmpty()){

                BCP processo = tabelaProcessos.getProcessos_prontos().poll();
                //Tem que Mudar estado para executando

                for (int i = 0; i < quantum; i++){
                    
                    if(dispatcher.Rodar(processo) == DispatcherFeedback.ES){
                        //Exclui processo terminados (Retornaram FEITO)

                        //FALTA tratar ES
                        processo.
                        tabelaProcessos.getProcessos_bloqueados().add(processo);
                        break;
                    }

                    if(dispatcher.Rodar(processo) == DispatcherFeedback.NADA){
                        if(i == quantum-1) tabelaProcessos.getProcessos_prontos().add(processo);
                        continue;
                    }


                    if(dispatcher.Rodar(processo) == DispatcherFeedback.FEITO){
                        //Exclui processo terminados (Retornaram FEITO)
                        tabelaProcessos.excluProcessos(processo);
                        break;
                    }
                }
            }


            tempoEsperaBloqueados();
            
        
        }
    }




	public Dispatcher getDispatcher() {
		return dispatcher;
	}





}
