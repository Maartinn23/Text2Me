package sintesis.text2me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//TODO: profundizar mas en el websocket, añadir backend del websocket con la bbdd mediante json...
//TODO: acabar interfaz basica de profile para desarrollo (cambiar en producción)...
//TODO: connectar bbdd atraves de api rest para recuperar datos y que se puedan actualizar desde profile mediante un update en bbdd...


@SpringBootApplication
public class Text2MeApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(Text2MeApplication.class, args);
	}

}
