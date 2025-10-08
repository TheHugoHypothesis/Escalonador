package com.escalonador.Kernel;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.EstadoProcesso;

public class Dispatcher {
    private LoggerProcessos loggerProcessos;

    public Dispatcher(LoggerProcessos log){ this.loggerProcessos = log; }

    //Retorna True se o processo terminou
    public DispatcherFeedback Rodar(BCP processo){
        if (processo.getEstadoProcesso() != EstadoProcesso.EXECUTANDO) {
            processo.setEstadoProcesso(EstadoProcesso.EXECUTANDO);
        }

        String linhaDeCodigo = processo.getCodigo().get(processo.getContadorDePrograma());

        //Pegando o contexto (Simbolicamente, porque na pratica não será usado para nada)
        int X = processo.getRegistradorX();
        int Y = processo.getRegistradorY();

        if (linhaDeCodigo.contains("X")) {
            String[] partes = linhaDeCodigo.split("=");
            int valorRegX = Integer.parseInt(partes[1].trim());
            processo.setRegistradorX(valorRegX);
            processo.incrementarContadorDePrograma();
            return DispatcherFeedback.NADA;
        }

        if (linhaDeCodigo.contains("Y")) {
            String[] partes = linhaDeCodigo.split("=");
            int valorRegY = Integer.parseInt(partes[1].trim());
            processo.setRegistradorY(valorRegY);
            processo.incrementarContadorDePrograma();
            return DispatcherFeedback.NADA;
        }

        if (linhaDeCodigo.contains("COM")) {
            processo.incrementarContadorDePrograma();
            return DispatcherFeedback.NADA;
        }

        if (linhaDeCodigo.equals("E/S")) {
            processo.setEstadoProcesso(EstadoProcesso.BLOQUEADO);
            loggerProcessos.ESProcesso(processo);
            processo.incrementarContadorDePrograma();
            return DispatcherFeedback.ES;
        }

        if (linhaDeCodigo.equals("SAIDA")) {
            return DispatcherFeedback.FEITO;
        }

        return DispatcherFeedback.NADA;
    }
    
}
