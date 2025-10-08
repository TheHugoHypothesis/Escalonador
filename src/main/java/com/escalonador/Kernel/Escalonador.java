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
        for (BCP processo : tabelaProcessos.getProcessos_prontos()) {
            
            for (int i = 0; i < quantum; i++){
                
                if(dispatcher.Rodar(processo) == DispatcherFeedback.ES){
                    tabelaProcessos.excluiProcessos_prontos(processo); //Exclui processo terminados (Retornaram FEITO)
                    break;



                if(dispatcher.Rodar(processo) == DispatcherFeedback.NADA){
                    tabelaProcessos.excluiProcessos_prontos(processo); //Exclui processo terminados (Retornaram FEITO)
                    continue;



                if(dispatcher.Rodar(processo) == DispatcherFeedback.FEITO){
                    tabelaProcessos.excluiProcessos_prontos(processo); //Exclui processo terminados (Retornaram FEITO)
                    break;



                }
            }
        }
    }




	public Dispatcher getDispatcher() {
		return dispatcher;
	}





}
