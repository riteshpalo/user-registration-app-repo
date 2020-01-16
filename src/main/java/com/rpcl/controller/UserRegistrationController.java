package com.rpcl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rpcl.constant.AppConstants;
import com.rpcl.domain.UserInfo;
import com.rpcl.properties.AppProperties;
import com.rpcl.service.UserDetailsService;

/**
 * This class is used to handle request from registrationForm page
 * @author Ritesh
 *
 */
@Controller
public class UserRegistrationController {
	
	@Autowired
	private UserDetailsService userDtlsService;
	@Autowired
	private AppProperties app;
	
	/**
	 * This method is used to display registration form on the browser
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/signUp")
	public String showRegistrationForm(Model model) {
		UserInfo userInfo=new UserInfo();
		model.addAttribute(AppConstants.USER_INFO, userInfo);
		return AppConstants.REGISTRATION_FORM;
	}
	
	/**
	 * This method is used to handle registration button of registrationForm page
	 * @param userInfo
	 * @param attributes
	 * @return String
	 */
	@RequestMapping(value = "/registerUser",method = RequestMethod.POST)
	public String handleRegisterBtn(@ModelAttribute("userInfo") UserInfo userInfo,RedirectAttributes attributes) {
		boolean isSaved = userDtlsService.registerUser(userInfo);
		if(isSaved)
			attributes.addFlashAttribute(AppConstants.SUCCESS_MSG, app.getMessages().get(AppConstants.REGISTRATION_ALMOST_COMPLETE));
		else
			attributes.addFlashAttribute(AppConstants.ERROR_MSG, app.getMessages().get(AppConstants.REGISTRATION_FAILED));
		return "redirect:/signUp";
	}
	
	/**
	 * This method is used to validate the email id asynchronously
	 * @param request
	 * @return
	 */
	@GetMapping("/validateEmail")
	@ResponseBody public String validateUserEmail(HttpServletRequest request) {
		String email = request.getParameter("email");
		return userDtlsService.checkUserByEmail(email);
	}

}
