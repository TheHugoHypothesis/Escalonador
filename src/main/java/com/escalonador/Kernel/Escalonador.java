package com.escalonador.Kernel;

public class Escalonador {
    private Dispatcher dispatcher;


	public Escalonador() {
		this.dispatcher = new Dispatcher();
	}

    
	public Dispatcher getDispatcher() {
		return dispatcher;
	}

}
