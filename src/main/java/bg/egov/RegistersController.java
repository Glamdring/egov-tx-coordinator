package bg.egov;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/registers")
public class RegistersController {

	@ResponseBody
	public void addRegister(@RequestBody AddRegisterRequest request) {
		//TODO check and store
	}
}
