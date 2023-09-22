package org.hdivsamples.controllers;

import java.security.Principal;
import java.util.List;

import org.hdivsamples.bean.Account;
import org.hdivsamples.dao.AccountDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	AccountDao accountDao;

	@RequestMapping
	public String admin(final Model model, final Principal principal) {

		Account account = accountDao.findUsersByUsername(principal.getName()).get(0);

		List<Account> accounts = accountDao.findAllUsers();

		logger.info(getSessionId() + " Displaying admin view of " + accounts.size() + " user details");

		model.addAttribute("account", account);
		model.addAttribute("accounts", accounts);

		return "admin";
	}

	private String getSessionId() {
		return  "[" + RequestContextHolder.getRequestAttributes().getSessionId() + "]";
	}


}