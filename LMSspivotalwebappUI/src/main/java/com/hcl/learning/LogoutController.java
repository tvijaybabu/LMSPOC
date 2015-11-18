package com.hcl.learning;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcl.util.ManagerDetails;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LogoutController {
	
	private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String home(Locale locale, Model model) {
		logger.info("Entered Logout controller....");
		model.addAttribute("result", "Log Out successfully." );
		HashMap Ml = new HashMap();
		//ManagerDetails 
		Ml=ManagerDetails.getManagerList();
		model.addAttribute("ManagerMap", Ml);
		return "home";
	}
	
}
