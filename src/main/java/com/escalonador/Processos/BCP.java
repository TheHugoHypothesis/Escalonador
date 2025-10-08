package com.escalonador.Processos;

import java.util.ArrayList;

/* Bloco de Controle de Processos
* Deve conter as informações para os quais os processos devem ter
* ao ser restaurados. Cada processo corresponde a um único BCP,
* e guarda suas informações no BCP.
*  */
public class BCP {
    private int contadorDePrograma;
    private EstadoProcesso estadoProcesso;
    private int registradorX;
    private int registradorY;
    private final ArrayList<String> codigo;
    private final String nomePrograma;
    private int tempoEspera;

    public BCP(ArrayList<String> codigo, String nomePrograma) {
        this.contadorDePrograma = 0;
        this.codigo = codigo;

        /* Os registradores são iniciados como 0 pois "Se o programa não usar uma das variáveis, ela será zero." pag 5 */
        this.registradorX = 0;
        this.registradorY = 0;
        this.tempoEspera = 0;
        this.estadoProcesso = EstadoProcesso.PRONTO;
        this.nomePrograma = nomePrograma;
    }



    public int getContadorDePrograma() {
        return contadorDePrograma;
    }
    public EstadoProcesso getEstadoProcesso() {
        return estadoProcesso;
    }
    public int getRegistradorX() {
        return registradorX;
    }
    public int getRegistradorY() {
        return registradorY;
    }
    public String getNomePrograma() {
        return nomePrograma;
    }
    public ArrayList<String> getCodigo() {
        return this.codigo;
    }

    public void incrementarContadorDePrograma() {
        contadorDePrograma = contadorDePrograma + 1;
    }
    public void setEstadoProcesso(EstadoProcesso estadoProcesso) {
        if (this.estadoProcesso.equals(estadoProcesso)) {
            System.out.println("O estado do processo: " + getNomePrograma() + " foi alterado para o estado em que já estava: " + getEstadoProcesso());
            System.exit(1);
            return;
        }

        if (getEstadoProcesso().equals(EstadoProcesso.BLOQUEADO) && estadoProcesso.equals(EstadoProcesso.EXECUTANDO)) {
            System.out.println("O processo com BCP: " + getNomePrograma() + " tentou mudar de BLOQUEADO para EXECUTANDO.");
            System.exit(1);
            return;
        }

        if(getEstadoProcesso().equals(EstadoProcesso.PRONTO) && estadoProcesso.equals(EstadoProcesso.BLOQUEADO)) {
            System.out.println("O processo com BCP: " + getNomePrograma() + " tentou mudar de PRONTO para BLOQUEADO.");
            System.exit(1);
            return;
        }

        this.estadoProcesso = estadoProcesso;
    }


    
    public void setRegistradorX(int registradorX) { this.registradorX = registradorX; }
    public void setRegistradorY(int registradorY) { this.registradorY = registradorY; }
}
