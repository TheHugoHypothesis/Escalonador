package com.escalonador;

import com.escalonador.IO.LoggerProcessos;
import com.escalonador.IO.ProgramLoader;
import com.escalonador.Kernel.Escalonador;
import com.escalonador.Processos.BCP;
import com.escalonador.Processos.TabelaProcessos;

public class Main {
    // Constantes para configuração do programa
    public static int NUMERO_DE_PROGRAMAS = 10;
    public static String PATH_QUANTUM = "programas/quantum.txt";
    public static String PATH_PROGRAMAS = "programas/";

    public static void main(String[] args) {
        ProgramLoader programLoader = new ProgramLoader();
        TabelaProcessos tabelaProcessos = new TabelaProcessos();

        // Carrega o LoggerProcessos, formatando a saída no formato de saída logXX.txt
        int tamanhoQuantum;
        tamanhoQuantum = programLoader.carregarQuantum(PATH_QUANTUM);
        String tamanhoQuantumFormatado = String.format("%02d", tamanhoQuantum);
        LoggerProcessos loggerProcessos = new LoggerProcessos(
                PATH_PROGRAMAS + "log" + tamanhoQuantumFormatado + ".txt"
        );

        // Carrega os programas na tabela de processo
        for (int i = 1; i <= NUMERO_DE_PROGRAMAS; i++) {
            String formatado = String.format("%02d", i);
            tabelaProcessos.adicionaProcessos(
                    programLoader.carregarPrograma(PATH_PROGRAMAS + formatado + ".txt")
            );
        }

        // Faz logging do carregamento dos processos em ordem pronta
        for (BCP processo : tabelaProcessos.getProcessos()) {
            loggerProcessos.carregaProcesso(processo);
            tabelaProcessos.adicionaProcessos_prontos(processo);
        }

        Escalonador escalonador = new Escalonador(tamanhoQuantum, tabelaProcessos, loggerProcessos);
        escalonador.iniciar();

        // Escrita do logging do diagnóstico
        loggerProcessos.escreveDiagnostico(
                escalonador.numeroDeTrocas / NUMERO_DE_PROGRAMAS,
                escalonador.somaDeQuantumRodou / escalonador.numeroDeTrocas,
                tamanhoQuantum
        );
        //System.out.println("MTT: " + escalonador.TempoFinalizacaoSoma / NUMERO_DE_PROGRAMAS);

        loggerProcessos.fechar();
    }
}