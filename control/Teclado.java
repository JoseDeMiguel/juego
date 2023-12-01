package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Teclado implements KeyListener { // KeyListener para leer las teclas

	private final static int numeroTeclas = 120;

	private final boolean[] teclas = new boolean[numeroTeclas];

	// Usamos public y no geters porque se usaran mucho y queremos velocidad
	public boolean arriba;
	public boolean abajo;
	public boolean izq;
	public boolean der;

	public void actualizar() {
		// Asignamos los controles
		arriba = teclas[KeyEvent.VK_W];
		abajo = teclas[KeyEvent.VK_S];
		izq = teclas[KeyEvent.VK_A];
		der = teclas[KeyEvent.VK_D];

	}

	// Metodo para mantener pulsado
	public void keyPressed(KeyEvent e) {
		// Cuando se pulsa se obtiene el codigo numerico de la tecla pulsada
		teclas[e.getKeyCode()] = true;

	}

	// Metodo para cuando dejas de pulsar
	public void keyReleased(KeyEvent e) {
		// Cuando se deja de pulsar se deja de obtener el codigo numerico de la tecla
		// pulsada
		teclas[e.getKeyCode()] = false;
	}

	// Metodo para pulsar 1 vez
	public void keyTyped(KeyEvent e) {

	}
}
