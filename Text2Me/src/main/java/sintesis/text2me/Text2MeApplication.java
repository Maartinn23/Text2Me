package sintesis.text2me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//TODO: Añadir opciones por defecto para profile_image y createdAt (para registrar fecha de creacion de cuenta)


@SpringBootApplication
public class Text2MeApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(Text2MeApplication.class, args);
	}

}
