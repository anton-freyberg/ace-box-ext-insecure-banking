package org.hdivsamples.security;

import java.util.ArrayList;
import java.util.List;

import org.hdivsamples.bean.Account;
import org.hdivsamples.controllers.ActivityController;
import org.hdivsamples.dao.AccountDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final AccountDao accountDao;
	private final String roleAdmin = "ROLE_ADMIN";
	private final String roleUser = "ROLE_USER";

	Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	public CustomAuthenticationProvider(final AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		// Editable validation for Spring security Login page.
		// The login page is not generated using Spring MVC Form tags (it is not possible with Spring Security)
		// Stop login process and show login page again
		Object aux = RequestContextHolder.getRequestAttributes().getAttribute("org.hdiv.action.EDITABLE_PARAMETER_ERROR",
				RequestAttributes.SCOPE_REQUEST);

		String sessionId = "[" + RequestContextHolder.getRequestAttributes().getSessionId() + "]"; //"[-]";

		if (aux != null) {
			logger.info(sessionId + " Bad credentials");
			throw new BadCredentialsException("Bad Credentials");
		}

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		logger.info(sessionId + " Starting findUsersByUsernameAndPassword of user: " + username);

		List<Account> listAccounts = new ArrayList<>();
		try {
			listAccounts = accountDao.findUsersByUsernameAndPassword(username.toLowerCase(), password);
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new InternalAuthenticationServiceException(e.getMessage());
		}

		if (listAccounts.isEmpty()) {
			logger.info(sessionId + " No users found for requested username: " + username);
			throw new BadCredentialsException("Bad Credentials");
		}

		logger.info(sessionId + " retrieved matching list of size " + listAccounts.size());

		List<GrantedAuthority> authList = new ArrayList<>(1);

		String role = username.equalsIgnoreCase("john") ? roleAdmin : roleUser;
		/*if (username.equalsIgnoreCase("john")) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		}
		else {
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		}*/
		authList.add(new SimpleGrantedAuthority(role));

		User user = new User(listAccounts.get(0).getUsername(), listAccounts.get(0).getPassword(), authList);

		UsernamePasswordAuthenticationToken ret = new UsernamePasswordAuthenticationToken(user, password, authList);
		logger.info(sessionId + " successfully authenticated: " + user.getUsername() + "; granted role: " + role);

		return ret;
	}

	@Override
	public boolean supports(final Class<?> arg0) {
		return true;
	}

}