package Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/controllerrequest")
public class SpringController 
{
	@RequestMapping(value="/hello")
	public String hello()
	{
		return "Hello i am a controller";
	}
}
