package Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controllerrequest")
public class SpringController 
{
	public String hello()
	{
		System.out.println("I am a controller");
		return "Hello i am a controller";
	}
}
