package com.rpcl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rpcl.constant.AppConstants;
import com.rpcl.domain.UserForgotPwdInfo;
import com.rpcl.properties.AppProperties;
import com.rpcl.service.UserDetailsService;
/**
 * This class is used to handle request from forgotPassword Page
 * @author Ritesh
 *
 */
@Controller
public class ForgotPasswordController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	AppProperties app;
	
	/**
	 * This method is used to display the forgot password form
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/forgotPassword")
	public String showForgotPwdForm(Model model) {
		UserForgotPwdInfo userForgotPwdInfo=new UserForgotPwdInfo();
		model.addAttribute(AppConstants.USER_FORGOT_PSWD_INFO, userForgotPwdInfo);
		return AppConstants.FORGOT_PSWD;
	}
	
	/**
	 * This method is used to handle the submit button of forgot password form page
	 * @param forgotPwdInfo
	 * @param attributes
	 * @return String
	 */
	@RequestMapping(value = "/handleSubmitBtn" , method = RequestMethod.POST)
	public String handleSubmitBtn(@ModelAttribute("userForgotPwdInfo") UserForgotPwdInfo forgotPwdInfo,RedirectAttributes attributes) {
		boolean isValid = userDetailsService.checkUserByEmail(forgotPwdInfo);
		boolean isUnLocked = userDetailsService.checkStatusByEmail(forgotPwdInfo);
		if(!isValid) {
			attributes.addFlashAttribute(AppConstants.ERROR_MSG, app.getMessages().get(AppConstants.INVALID_EMAIL));
		}else if(isUnLocked) {
			attributes.addFlashAttribute(AppConstants.SUCCESS_MSG, app.getMessages().get(AppConstants.CHECK_EMAIL_LOGIN));
		}else {
			attributes.addFlashAttribute(AppConstants.ERROR_MSG, app.getMessages().get(AppConstants.ACCOUNT_LOCKED));
		}
		return "redirect:/forgotPassword";
	}
}
