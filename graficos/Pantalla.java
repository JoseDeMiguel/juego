package graficos;

public final class Pantalla {

	private final int ancho;
	private final int alto;

	// De nuevo igual que en sprite y hojaSprites
	public final int[] pixeles;

	public Pantalla(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		pixeles = new int[ancho * alto];

	}

	// Primer metodo
	// Este metodo limpiara la pantalla para volver a pintar sobre ella, osea dejara
	// la pantalla en blanco
	public void limpiar() {
		// bucle for para movernos por el array
		for (int i = 0; i < pixeles.length; i++) {
			// El 0 es el color negro, por lo que cambiara los pixeles por negro
			pixeles[i] = 0;

		}
	}

	// Metodo para dibujar la pantalla
	// Compensar es el movimiento que haga el jugador en los ejes hacia donde se
	// mueva
	public void mostrar(final int compensarX, final int compensarY) {
		// 2 Bucles for para crear los dos ejes
		// Primero para el eje Y
		for (int i = 0; i < alto; i++) {
			int posicionY = i + compensarY;

			// Condicion para salir del bucle (para no salirte del mapa)
			if (posicionY < 0 || posicionY >= alto) {
				continue;
			}

			// Segundo con el eje X
			for (int j = 0; j < ancho; j++) {
				int posicionX = j + compensarX;

				// De nuevo la condicion apra salir del bucle y no salirte del mapa
				if (posicionX < 0 || posicionX >= ancho) {
					continue;
				}

				// codigo para redibujar
			}

		}
	}

}
