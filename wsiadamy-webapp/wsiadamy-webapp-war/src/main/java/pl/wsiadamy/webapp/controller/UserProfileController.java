package pl.wsiadamy.webapp.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.FeedbackBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.Feedback;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.webapp.controller.util.Paginator;

@Controller
@RequestMapping("/")
public class UserProfileController {
	
	@Autowired
	UserBO userBO;
	
	@Autowired
	FeedbackBO feedbackBO;
	
	@RequestMapping(value = "/userProfile/get/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'RouteView')")
    public String showRoute(@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		
		User user = userBO.getById(id);
		
		if(null == user)
			return "forward:/errors/404";
		
		model.addAttribute("user", user);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getId());
		
		Long itemsCount = feedbackBO.listFeedbackCount(params);
		int itemsPage = 1;
		
		if(null != request.getParameter("p"))
			itemsPage = Integer.valueOf(request.getParameter("p"));
		
		Paginator paginator = new Paginator(1, itemsCount.intValue(), itemsPage);
		
		List<Feedback> feedback = feedbackBO.listFeedback(params, paginator.getLimit(), paginator.getOffset());
		model.addAttribute("feedbacks", feedback);
		model.addAttribute("paginator", paginator);
		
        return "user/display";
    }
}