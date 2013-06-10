package pl.wsiadamy.webapp.controller;
 
import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserData;
import pl.wsiadamy.common.model.input.AccountDataInput;
import pl.wsiadamy.common.model.input.validator.AccountDataValidator;
import pl.wsiadamy.common.security.util.AthenticationUtil;
import pl.wsiadamy.webapp.authentication.CustomAuthenticationProvider;

@Controller
@RequestMapping("/account")
public class AccountDataController {

	@Autowired
	UserBO userBO;
	
	@Autowired
	AccountDataValidator accountDataValidator;
	
	@ModelAttribute("accountDataInput")
    public AccountDataInput getFormAddRoute() {
        return new AccountDataInput();
    }

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
    public String showForm(
		@ModelAttribute("accountDataInput")
        AccountDataInput form,
        ModelMap model) {
		User user = userBO.getById(AthenticationUtil.getUser().getId());
		UserData userData = user.getUserData();

		model.addAttribute("userData", userData);
		
		if(null != userData) {
			if(null != userData.getFirstname())
				form.setFirstname(userData.getFirstname());

			if(null != userData.getLastname())
				form.setLastname(userData.getLastname());
			
			if(null != userData.getContactPhone())
				form.setContactPhone(userData.getContactPhone());

			if(null != userData.getBirthday()) {
				Calendar birthday = Calendar.getInstance();
				
				birthday.setTime(userData.getBirthday());
				form.setBirthday(birthday);
			}
			
//			if(null != userData.getCarCombustion())
				form.setCarCombustion(userData.getCarCombustion());

		}
		
        return "account/data";
    }
	
	@RequestMapping(value = "/data", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
    public String validateForm(
        @ModelAttribute("accountDataInput")
        @Valid AccountDataInput form,
        BindingResult result, ModelMap model) {
		
		User user = userBO.getById(AthenticationUtil.getUser().getId());
		UserData userData = user.getUserData();
		
		model.addAttribute("userData", userData);
		
		accountDataValidator.validate(form, result);
		
        if (result.hasErrors()) {
            return "account/data";
        }
        
		if(null == userData)
			userData = new UserData(user);
		
		userData.setFirstname(form.getFirstname());
		userData.setLastname(form.getLastname());
		userData.setBirthday(form.getBirthdayObject().getTime());
		userData.setContactPhone(form.getContactPhone());
		userData.setCarCombustion(form.getCarCombustion());
		
		userBO.update(user);
		
		CustomAuthenticationProvider.authenticateUser(user);
		
        return "redirect:data";
    }
}