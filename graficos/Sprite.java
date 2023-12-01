package graficos;

public final class Sprite {

	// Utilizamos lado en vez de tamaño por la ñ (cuanto mide el lado del sprite)
	private final int lado;

	// Hacemos los marcadores de las coordenadas de los pixeles
	private int x;
	private int y;

	// De nuevo un array para los colores
	public int[] pixeles;

	private final HojaSprites hoja;

	public Sprite(final int lado, final int columna, final int fila, final HojaSprites hoja) {
		// Igualamos la variable
		this.lado = lado;

		// Multiplicamos los lados para tener el tamaño del array
		pixeles = new int[lado * lado];

		// Le damos el valor de x e y
		this.x = columna * lado;
		this.y = fila * lado;
		this.hoja = hoja;

		// Usamos dos for para la operacion
		// Ira leyendo de izquiera a derecha y de arriba a abajo pixel a pixel de la
		// imagen
		for (int i = 0; i < lado; i++) {
			for (int j = 0; j < lado; j++) {
				// Nos "inventamos" nuestro sistema de cordenadas con esta operacion, usamos el
				// geter para obtener el ancho
				pixeles[j + i * lado] = hoja.pixeles[(j + this.x) + (i + this.y) * hoja.getAncho()];
			}
		}
	}
}