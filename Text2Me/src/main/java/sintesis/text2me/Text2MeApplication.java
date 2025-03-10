package sintesis.text2me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//TODO: profundizar mas en el websocket, añadir backend del websocket con la bbdd mediante json, Arreglar api rest en chatController para recuperar info de logueo actual...


@SpringBootApplication
public class Text2MeApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(Text2MeApplication.class, args);
	}

}
