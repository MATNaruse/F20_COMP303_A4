package comp303.a4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
}
