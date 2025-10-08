package com.escalonador;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.IO.ProgramLoader;
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
            tabelaProcessos.adicionaProcessos(
                    programLoader.carregarPrograma("programas/" + formatado + ".txt")
                    
            );
        }

        /* Carrega os processos em ordem pronta */
        for (BCP processo : tabelaProcessos.getProcessos()) {
            loggerProcessos.carregaProcesso(processo);
            tabelaProcessos.adicionaProcessos_prontos(processo);
        }

        /* Mostra o tamanho do Quantum e os processos carregados. */
        System.out.println(tamanhoQuantum);
        System.out.println(tabelaProcessos.getProcessos_prontos());

        Escalonador escalonador = new Escalonador(tamanhoQuantum, tabelaProcessos, loggerProcessos);
        escalonador.iniciar();

        loggerProcessos.escreveDiagnostico(escalonador.numeroDeTrocas/10, escalonador.somaDeQuantumRodou / escalonador.numeroDeTrocas, tamanhoQuantum);
        loggerProcessos.fechar();
    }
}