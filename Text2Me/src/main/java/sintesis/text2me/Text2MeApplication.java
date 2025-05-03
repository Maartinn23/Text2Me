package sintesis.text2me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//TODO: connectar bbdd atraves de api rest para recuperar datos y que se puedan actualizar desde profile mediante un update en bbdd...
//TODO: Recuperar mensajes persistidos en bbdd...
//TODO: Montar front-end para mostrar mensajes persistidos...
//TODO: Pulir front-end: Gestión de errores, interfaz gráfica amigable...

@SpringBootApplication
public class Text2MeApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(Text2MeApplication.class, args);
	}

}
