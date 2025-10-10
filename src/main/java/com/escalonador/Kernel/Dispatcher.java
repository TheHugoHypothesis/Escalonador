package com.escalonador.Kernel;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.EstadoProcesso;

public class Dispatcher {
    private LoggerProcessos loggerProcessos;

    public Dispatcher(LoggerProcessos log){
        this.loggerProcessos = log;
    }

    public DispatcherFeedback Rodar(BCP processo) {
        /* Conferência extra para colocar o processo como EXECUTANDO. */
        if (processo.getEstadoProcesso() != EstadoProcesso.EXECUTANDO) {
            processo.setEstadoProcesso(EstadoProcesso.EXECUTANDO);
        }

        String linhaDeCodigo = processo.getCodigo().get(processo.getContadorDePrograma());

        // Pegando o contexto (Simbolicamente, como se fosse o Dispatcher obtendo o contexto)
        int X = processo.getRegistradorX();
        int Y = processo.getRegistradorY();

        // Caso corresponda a uma linha de mudança do Registrador X.
        if (linhaDeCodigo.contains("X")) {
            String[] partes = linhaDeCodigo.split("=");
            int valorRegX = Integer.parseInt(partes[1].trim());
            processo.setRegistradorX(valorRegX);
            processo.incrementarContadorDePrograma();
            return DispatcherFeedback.NADA;
        }

        // Caso corresponda a uma linha de mudança do Registrador Y.
        if (linhaDeCodigo.contains("Y")) {
            String[] partes = linhaDeCodigo.split("=");
            int valorRegY = Integer.parseInt(partes[1].trim());
            processo.setRegistradorY(valorRegY);
            processo.incrementarContadorDePrograma();
            return DispatcherFeedback.NADA;
        }

        // Caso corresponda a uma linha de mudança de comando
        if (linhaDeCodigo.contains("COM")) {
            processo.incrementarContadorDePrograma();
            return DispatcherFeedback.NADA;
        }

        // Caso corresponda a uma linha de E/S
        if (linhaDeCodigo.equals("E/S")) {
            processo.setEstadoProcesso(EstadoProcesso.BLOQUEADO);
            loggerProcessos.ESProcesso(processo);
            processo.incrementarContadorDePrograma();
            return DispatcherFeedback.ES;
        }

        // Caso corresponda a uma saída
        if (linhaDeCodigo.equals("SAIDA")) {
            return DispatcherFeedback.FEITO;
        }

        // Caso não for uma linha válida
        return DispatcherFeedback.NADA;
    }
    
}
