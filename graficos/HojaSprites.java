package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HojaSprites { // Esta clase carga los sprites

	private final int ancho;
	private final int alto;
	public final int[] pixeles;

	// Creamo el constructor
	public HojaSprites(final String ruta, final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		pixeles = new int[ancho * alto];

		// Usamos un try como en la clase juego para asegurarnos de que no haya ningun
		// problema
		// al ser una operación arriesgada
		try {
			// Aqui creamos una imagen y le atribuimos el valor de la ruta (Aun no la
			// tenemos creada)
			BufferedImage imagen = ImageIO.read(HojaSprites.class.getResource(ruta));

			// Volcamos los valores de la imagen de cada pixel corresponidiente (getRGB) y
			// le especificamos desde donde hasta donde,
			// a que array y cuanto
			imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Creamos el geter para que la clase Sprite pueda acceder a la variable
	// Lo hacemos de forma que retorne un valor
	public int getAncho() {

		return ancho;
	}

}
