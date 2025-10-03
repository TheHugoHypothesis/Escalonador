package com.escalonador;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.IO.ProgramLoader;
import com.escalonador.Kernel.Dispatcher;
import com.escalonador.Kernel.Escalonador;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.TabelaProcessos;

public class Main {
    public static void main(String[] args) {
        ProgramLoader programLoader = new ProgramLoader();
        TabelaProcessos tabelaProcessos = new TabelaProcessos();

        int tamanhoQuantum;
        tamanhoQuantum = programLoader.carregarQuantum("programas/quantum.txt");
        String tamanhoQuantumFormatado = String.format("%02d", tamanhoQuantum);

        LoggerProcessos loggerProcessos = new LoggerProcessos("programas/" + "log" + tamanhoQuantumFormatado + ".txt");

        for (int i = 1; i < 11; i++) {
            String formatado = String.format("%02d", i);
            System.out.println("Carregando programa: " + formatado + ".txt");
            tabelaProcessos.adicionaProcesso(
                    programLoader.carregarPrograma("programas/" + formatado + ".txt")
            );
        }

        

        /* Mostra o tamanho do Quantum e os processos carregados. */
        System.out.println(tamanhoQuantum);
        System.out.println(tabelaProcessos.getProcessos());



        /* Carrega os processos em ordem */
        for (BCP processo : tabelaProcessos.getProcessos()) {
            loggerProcessos.carregaProcesso(processo);
        }



        
        Escalonador escalonador = new Escalonador();






        /* Executa os programas em lote (batch) */
        for (BCP processo : tabelaProcessos.getProcessos()) {
            for (String linhaDeCodigo : processo.getCodigo()) {
 
                if (linhaDeCodigo.contains("X")) {
                    String[] partes = linhaDeCodigo.split("=");
                    int valorRegX = Integer.parseInt(partes[1].trim());
                    processo.setRegistradorX(valorRegX);
                }
 
                if (linhaDeCodigo.contains("Y")) {
                    String[] partes = linhaDeCodigo.split("=");
                    int valorRegY = Integer.parseInt(partes[1].trim());
                    processo.setRegistradorY(valorRegY);
                }
 
                if (linhaDeCodigo.equals("E/S")) {
                    loggerProcessos.ESProcesso(processo);
                }
 
                if (linhaDeCodigo.equals("SAIDA")) {
                    loggerProcessos.terminaProcesso(processo);
                }
            }
        }

 
 
 
 
 
 
 
 
 
        loggerProcessos.fechar();
    }
}