package org.hdivsamples.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;

import java.util.Map;

@Controller
public class AuthenticationController {
	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@RequestMapping("/login")
	public String login(final Model model) {
		return "login";
	}

	@RequestMapping("/")
	public String home(final Model model) {
		return "redirect:/dashboard";
	}

	@RequestMapping("/403")
	public String error403(final Model model) {
		return "error403";
	}

	private String getSessionId() {
		return  "[" + RequestContextHolder.getRequestAttributes().getSessionId() + "]";
	}
}