package com.escalonador.Kernel;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.EstadoProcesso;


public class Dispatcher {

    private LoggerProcessos loggerProcessos;

    public Dispatcher(LoggerProcessos log){
        this.loggerProcessos = log;
    }


    //Retorna True se o processo terminou
    public boolean Rodar(BCP processo){

        String linhaDeCodigo = processo.getCodigo().get(processo.getContadorDePrograma());

        if (linhaDeCodigo.contains("X")) {
            String[] partes = linhaDeCodigo.split("=");
            int valorRegX = Integer.parseInt(partes[1].trim());
            processo.setRegistradorX(valorRegX);
            processo.incrementarContadorDePrograma();
            return false;
        }


        if (linhaDeCodigo.contains("Y")) {
            String[] partes = linhaDeCodigo.split("=");
            int valorRegY = Integer.parseInt(partes[1].trim());
            processo.setRegistradorY(valorRegY);
            processo.incrementarContadorDePrograma();
            return false;
        }


        if (linhaDeCodigo.equals("E/S")) {
            processo.setEstadoProcesso(EstadoProcesso.BLOQUEADO);
            loggerProcessos.ESProcesso(processo);


            processo.incrementarContadorDePrograma();
            return false;
        }


        if (linhaDeCodigo.equals("SAIDA")) {
            //Falta por coisa aki

            loggerProcessos.terminaProcesso(processo);
            return true;
        }

        return false;
    }
    
}
