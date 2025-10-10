package com.escalonador.IO;

import com.escalonador.Processos.BCP;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
 *
 */
public class ProgramLoader {
    public BCP carregarPrograma(String pathPrograma) {
        ArrayList<String> codigo = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathPrograma));
            String nomePrograma = br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                codigo.add(linha.trim());
            }

            return new BCP(codigo, nomePrograma);
        } catch (IOException e) {
            System.out.println("Falha ao carregar o programa de diretório: " + pathPrograma + " " + e.getMessage());
            return null;
        }
    }

    public int carregarQuantum(String pathQuantum) {
        int tamanhoQuantum = 0;
        try {
            Scanner scanner = new Scanner(new File(pathQuantum));
            tamanhoQuantum = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível encontrar o arquivo de quantum: " + e.getMessage());
            System.exit(1);
        }
        return tamanhoQuantum;
    }

}
