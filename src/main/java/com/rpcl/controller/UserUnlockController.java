package com.rpcl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rpcl.constant.AppConstants;
import com.rpcl.domain.UserUnlockInfo;
import com.rpcl.properties.AppProperties;
import com.rpcl.service.UserDetailsService;

/**
 * This class is used to handle request from userUnlock page
 * @author Ritesh
 *
 */
@Controller
public class UserUnlockController {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AppProperties app;
	
	/**
	 * This method is used to display the unlock form on the browser
	 * @param model
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/unlockAccount")
	public String showUnlockForm(Model model,HttpServletRequest request) {
		UserUnlockInfo userUnlockInfo=new UserUnlockInfo();
		String email = request.getParameter("email");
		if(email!=null && !email.isEmpty()) {
			userUnlockInfo.setUserEmail(email);
			model.addAttribute(AppConstants.USER_UNLOCK_INFO, userUnlockInfo);
		}
		
		return AppConstants.USER_UNLOCK;
	}
	
	/**
	 * This method is used to handle unlock button of userUnlock form
	 * @param userUnlockInfo
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/handleUnlockBtn",method=RequestMethod.POST)
	public String handleUnlockBtn(@ModelAttribute("userUnlockInfo") UserUnlockInfo userUnlockInfo,RedirectAttributes attributes) {
		boolean isUnlocked = userDetailsService.getPasswordByEmail(userUnlockInfo);
		if(isUnlocked)
			attributes.addFlashAttribute(AppConstants.SUCCESS_MSG, app.getMessages().get(AppConstants.ACCOUNT_UNLOCKED_SUCCESS));
		else
			attributes.addFlashAttribute(AppConstants.ERROR_MSG, app.getMessages().get(AppConstants.ACCOUNT_UNLOCKED_ERROR));
			
		return "redirect:/unlockAccount?email="+userUnlockInfo.getUserEmail();
	}

}
