package com.rpcl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rpcl.constant.AppConstants;
import com.rpcl.domain.UserLoginInfo;
import com.rpcl.properties.AppProperties;
import com.rpcl.service.UserDetailsService;

/**
 * This class is used to handle request from userLogin page
 * @author Ritesh
 *
 */
@Controller
public class UserLoginController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AppProperties app;
	
	/**
	 * This method is used to display login form on the browser
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/")
	public String showLoginForm(Model model) {
		UserLoginInfo userLoginInfo=new UserLoginInfo();
		model.addAttribute(AppConstants.USER_LOGIN_INFO, userLoginInfo);
		return AppConstants.USER_LOGIN;
	}
	
	/**
	 * This method is used to handle login button of userLogin page
	 * @param userLoginInfo
	 * @param attributes
	 * @return String
	 */
	@RequestMapping(value = "/handleLoginBtn",method = RequestMethod.POST)
	public String handleLoginBtn(@ModelAttribute("userLoginInfo") UserLoginInfo userLoginInfo,RedirectAttributes attributes) {
		boolean isValid = userDetailsService.checkEmailAndPwdValid(userLoginInfo);
		boolean statusUnlocked = userDetailsService.checkUserStatus(userLoginInfo);
		if(!isValid) {
			attributes.addFlashAttribute(AppConstants.ERROR_MSG, app.getMessages().get(AppConstants.INVALID_CREDENTIALS));
		}else if(!statusUnlocked) {
			attributes.addFlashAttribute(AppConstants.ERROR_MSG, app.getMessages().get(AppConstants.ACCOUNT_LOCKED));
		}else {
			return "redirect:/displayDashboard";
		}
		
		return "redirect:/";
	}
	
	/**
	 * This method is used to display the dashboard
	 * @return String
	 */
	@RequestMapping(value = "/displayDashboard")
	public String displayDashboard() {
		return AppConstants.DASHBOARD;
	}

}
