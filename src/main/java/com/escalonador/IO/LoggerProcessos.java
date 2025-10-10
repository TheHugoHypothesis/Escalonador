package com.escalonador.IO;

import com.escalonador.Processos.BCP;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/*
 * Essa é a classe que faz escrita em arquivo externo
 * com formato padrão de saída. Recebe como entrada o path
 * e escreve no arquivo usando BufferedWriter.
 */
public class LoggerProcessos {
    private BufferedWriter writer;

    public LoggerProcessos(String pathSaida) {
        try {
            writer = new BufferedWriter(new FileWriter(pathSaida, false));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void escreverLinha(String linha) {
        /* Faz saída imediata do arquivo, útil para fazer logging parcialmente */
        try {
            writer.write(linha);
            writer.newLine();
            writer.flush();
        } catch (IOException ignored) { }
    }

    public void carregaProcesso(BCP processo) {
        escreverLinha("Carregando " + processo.getNomePrograma());
    }
    public void executaProcesso(BCP processo) {
        escreverLinha("Executando " + processo.getNomePrograma());
    }
    public void interrompeProcesso(BCP processo, int instrucoes) {
        if (instrucoes == 1) {
            escreverLinha("Interrompendo " + processo.getNomePrograma() + " após " + instrucoes + " instrução");
        } else {
            escreverLinha("Interrompendo " + processo.getNomePrograma() + " após " + instrucoes + " instruções");
        }

    }
    public void ESProcesso(BCP processo) {
        escreverLinha("E/S iniciada em " + processo.getNomePrograma());
    }
    public void terminaProcesso(BCP processo) {
        escreverLinha(
                processo.getNomePrograma() + " terminado. "
                        + "X=" + processo.getRegistradorX() + "."
                        + " Y=" + processo.getRegistradorY()
        );
    }

    public void escreveDiagnostico(float mediaTrocas, float mediaInstrucoes, int quantum) {
        // Usa-se Locale.US para formatar com "." ao invés de ","
        String mediaTrocasFormatada = String.format(Locale.US, "%.4f", mediaTrocas);
        escreverLinha("MEDIA DE TROCAS: " + mediaTrocasFormatada);

        String mediaInstrucoesFormatada = String.format(Locale.US,"%.4f", mediaInstrucoes);
        escreverLinha("MEDIA DE INSTRUCOES: " + mediaInstrucoesFormatada);

        escreverLinha("QUANTUM: " + quantum);
    }

    public void fechar() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
