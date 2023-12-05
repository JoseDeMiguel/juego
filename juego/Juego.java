package juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import control.Teclado;

// Extendemos el canvas, util para dibujuar a gran velocidad (Asi se heredan cosas de la clase canvas)
// Necesitamos una interfaz, debemos añadir el metodo que esta sin emplementar
public class Juego extends Canvas implements Runnable {

	// Añadimos el identificador de serie, por si guardamos la clase y la volvemos a
	// usar habiendo cambiado
	private static final long serialVersionUID = 1;

	// Utilizamos un final para definir un valor que no va a cambiar nunca (En
	// mayusculas por convenio)
	private static final int ancho = 800;
	private static final int alto = 600;

	private static final String nombre = "Juego";

	// Creamos las variables para mostrar los aps y fps
	private static int aps = 0;
	private static int fps = 0;

	// Creamos un booleano para verificar si esta ejecutandose el juego y lo
	// inicializamos en false por defecto
	// Utilizamos volatile ya que es peligroso y asi evitamos que el
	// programa se cuelgue (no se utilizara a la vez en los threads)
	private static volatile boolean enFuncionamiento = false;

	// Creamos la ventana con el JFrame, y le llamamos ventana (Statica ya que solo
	// habrá una ventana)
	private static JFrame ventana;

	public static Thread thread;

	private static Teclado teclado; // Metemos la clase teclado

	private Juego() {
		setPreferredSize(new Dimension(ancho, alto));

		teclado = new Teclado();
		// Esto hace que detecte toda tecla que se pulse en esta clase
		addKeyListener(teclado);

		// Iniciamos el objecto ventana (utilizando JFrame) y le ponemos el nombre
		ventana = new JFrame(nombre);
		// Hacemos que al cerrar la ventana se cierre la aplicación
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Hacemos que el usuario no pueda cambiar el tamaño de la ventana
		ventana.setResizable(false);
		// Añadimos un diseño (a nivel interno)
		ventana.setLayout(new BorderLayout());
		// Añadimos la clase canvas (con this marcamos esta misma clase y despues una
		// organicacion, para que este en el centro de la pantalla)
		ventana.add(this, BorderLayout.CENTER);
		// Para que siempre abra la ventana a su máximo
		ventana.pack();
		// Establecemos la posición de la ventana dentro del escitorio, null para que
		// sea en el centro del escritorio
		ventana.setLocationRelativeTo(null);
		// Para que pueda verse la ventana
		ventana.setVisible(true);
	}

	// Metodo main para que se pueda ejecutar
	public static void main(String[] args) {
		// Inicializamos la clase
		Juego juego = new Juego();

		// Llamamos la función iniciar para poder iniciarlo
		juego.iniciar();
	}

// Creamos un metodo para que este mas claro y legible la instancia del thread
// En este lo iniciamos
// Añadimos la palabra synchronized para sincronizar los threads y la variable
// enFuncionamiento pueda ser volatile
	private synchronized void iniciar() {
		// Cambiamos el booleano a true al ejecutar el juego
		enFuncionamiento = true;

		thread = new Thread(this, "Graphics");
		thread.start();
	}

//Añadimos la palabra synchronized para sincronizar los threads y la variable
//enFuncionamiento pueda ser volatile
	private synchronized void detener() {
		// Cambiamos el booleano a false cuando se detenga
		enFuncionamiento = false;

		// Utilizamos un try catch para que rimero, pruebe a ajecutar el join (en vez de
		// un stop) y en caso de que fallara el catch
		// haría que no se colgara el programa para el usuario (printara por consola el
		// error)
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

// Aqui escribiremos lo necesario para ir actualizando las variables del juego
	private void actualizar() {
		// Introducimos el teclado en actualizar para que tenga efecto
		teclado.actualizar();

		// De momento para ver si funciona lo veremos con un syso, ya que no tenemos
		// personaje todavia
		if (teclado.arriba) {
			System.out.println("Arriba");
		} else if (teclado.abajo) {
			System.out.println("Abajo");
		} else if (teclado.izq) {
			System.out.println("Izquierda");
		} else if (teclado.der) {
			System.out.println("Derecha");
		}

		// Aumentamos los aps en 1 cuando se actualice
		aps++;
	}

// Aqui escribiremos lo necesario para ir dibujando los graficos
	private void mostrar() {
		// Aumentamos los fps en 1 cuando se muestre
		fps++;
	}

// Este es el metodo implementado automaticamente
// Aqui se ejecutará el segundo thread
	public void run() {
		// Escribimos la equivalencia de segundos = nanosegundos
		final int NS_POR_SEGUNDO = 1000000000;
		// Escribimos cuantas veces queremos que se actualice por segundo
		final byte APS_OBJETIVO = 60;
		// Hacemos el calculo de cuantos nanosegundos transcurren en una actualizacion
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

		// Este long atribuirá una cantidad de nanosegundos en ese momento exacto
		long referenciaActualizacion = System.nanoTime();
		// Creamos la referencia para crear el contador de fps
		long referenciaContador = System.nanoTime();

		// Aqui creamos las variables tiempoTranscurrido y delta
		double tiempoTranscurrido;
		// Iniciamos delta en 0, se llama delta por convencion en los videojuegos, es la
		// cantidad de tiempo
		// transcurrido hasta que se realiza una actualizacion
		double delta = 0;

		requestFocus();

		// Creamos el bucle principal que entrara siempre que enFuncionamiento sea true
		while (enFuncionamiento) {
			// Esta variable toma el valor en nanosegundos (diferente al de arriba)
			final long inicioBucle = System.nanoTime();

			// tiempoTranscurrido seria igual a la variable anterior - la primera referencia
			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			// Reseteamos la primera referencia con el segundo valor
			referenciaActualizacion = inicioBucle;

			// Ahora dividimos ese tiempo anterior por los nanosegundos por actualizacion y
			// se irán sumando
			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			// Eventualmente delta llegará a 1, ahi actualizaremos y restaremos 1 a delta
			while (delta >= 1) {
				actualizar();
				delta--;
			}
			mostrar();

			// Aqui tomamos como referencia momento concreto y se le resta la referencia del
			// contador
			// si es mas de 1 segundo hará una actualización
			if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
				// Escribimos en la cabecera de la ventana los fps
				ventana.setTitle(nombre + " || APS: " + aps + " || FPS: " + fps);
				// Reiniciamos las variables
				aps = 0;
				fps = 0;
				// Actualizamos la referencia del contador
				referenciaContador = System.nanoTime();
			}
		}
	}

}