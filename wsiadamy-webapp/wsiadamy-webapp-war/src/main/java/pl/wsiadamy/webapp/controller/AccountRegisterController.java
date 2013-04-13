package pl.wsiadamy.webapp.controller;
 
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.AccountRegisterInput;
import pl.wsiadamy.common.model.input.validator.AccountRegisterValidator;
import pl.wsiadamy.webapp.authentication.CustomAuthenticationProvider;

@Controller
@RequestMapping("/")
public class AccountRegisterController {

	@Autowired
	UserBO userBO;
	
	@Autowired
	AccountRegisterValidator accountRegisterValidator;
	
	@ModelAttribute("accountRegisterInput")
    public AccountRegisterInput getFormAddRoute() {
        return new AccountRegisterInput();
    }

	@RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showForm(ModelMap model) {
        return "account/register";
    }
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public String validateForm(
        @ModelAttribute("accountRegisterInput")
        @Valid AccountRegisterInput form,
        BindingResult result, ModelMap model) {
		
		accountRegisterValidator.validate(form, result);
		
        if (result.hasErrors()) {
            return "account/register";
        }
        
        User user = new User();
        user.setUsername(form.getEmail());
        user.setPassword(form.getPassword());
        
        userBO.save(user);
        
        CustomAuthenticationProvider.authenticateUser(user);
        
        return "redirect:/";
    }
}