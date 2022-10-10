package com.jasvindersingh.airlinebookingsystem.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.jasvindersingh.airlinebookingsystem.exceptions.AppException;
import com.jasvindersingh.airlinebookingsystem.models.User;


@Service
public class UserDetailsServiceImpl implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private IUserService userService;
	
	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new AppUserDetails(user);
	}*/

   @Override
   public Authentication authenticate(Authentication auth) throws AuthenticationException {
	  logger.info("In authenticate method");
	  Authentication retVal = null;
	  List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
	  if (auth != null)
      {
		  try {
			  String name = auth.getName();
		      String password = auth.getCredentials().toString();
		      User user = new User();
		      user.setEmail(name);
		      user.setPassword(password);
		      user = userService.authenticate(user);
		      if(user != null) {
		    	  if("ADMIN".equals(user.getRole())) {
		    		  grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
		    	  }
		    	  grantedAuths.add(new SimpleGrantedAuthority("USER"));
		    	  retVal = new UsernamePasswordAuthenticationToken(name, "", grantedAuths);
		      }
		      else {
		    	  retVal = new UsernamePasswordAuthenticationToken(null, null, grantedAuths);
		      }
		  }
		  catch(AppException e) {
			e.printStackTrace();
			logger.error("Error Occured in authenticate",e);
		  }
      }
	  logger.info("return from authenticate");
      return retVal;
   }
	
   @Override
   public boolean supports(Class<?> tokenType)
   {
      return tokenType.equals(UsernamePasswordAuthenticationToken.class);
   }

}
