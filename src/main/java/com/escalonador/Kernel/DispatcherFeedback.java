package com.escalonador.Kernel;

/*
 * Corresponde a uma saída possível de comunicação entre o Dispatcher
 * e o Escalonador.
 * (i) NADA: Corresponde que ocorreu uma execução normal, sem E/S ou término do processo;
 * (ii) ES: Corresponde que o processo requisitou E/S.
 * (iii) FEITO: Corresponde quando o programa faz uma SAÍDA.
 *  */
public enum DispatcherFeedback {
    NADA,
    ES,
    FEITO
}
