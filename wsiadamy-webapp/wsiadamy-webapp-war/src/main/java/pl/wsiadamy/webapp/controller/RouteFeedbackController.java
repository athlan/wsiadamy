package pl.wsiadamy.webapp.controller;
 
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.ParticipanseBO;
import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.dao.RouteDao;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.RouteAddInput;
import pl.wsiadamy.common.security.util.AthenticationUtil;

import pl.wsiadamy.common.model.input.FeedbackInput;

@Controller
@RequestMapping("/route")
public class RouteFeedbackController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;

	@Autowired
	RouteDao routeDao;
	
	@Autowired
	ParticipanseBO participanseBO;
	
	@ModelAttribute("feedbackAddInput")
    public FeedbackInput getFormFeedbackAdd() {
        return new FeedbackInput();
    }
	
	@RequestMapping(value = "/feedback/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'FeedbackAddRoute')")
    public String feedback(@PathVariable("id") Integer id, ModelMap model) {
		Route route = routeBO.getById(id);
		User user = AthenticationUtil.getUser();
		
		model.addAttribute("route", route);
		
		if(route.getOwner().equals(user)) {
			return "redirect:/route/feedbackParticipants";
		}
		
		Participanse routeParticipanse = participanseBO.getByUserRoute(user, route);
		
		return "redirect:/route/feedbackParticipanse/" + routeParticipanse.getId();
    }

	@RequestMapping(value = "/feedbackParticipanse/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'FeedbackAddParticipanse')")
    public String feedbackParticipanse(
		@PathVariable("id") Integer id,
		ModelMap model) {
		
		Participanse participanse = participanseBO.getById(id);
		model.addAttribute("participanse", participanse);
		
		return "feedback/add";
	}
	
	@RequestMapping(value = "/feedbackParticipanse/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#id, 'FeedbackAddParticipanse')")
    public String feedbackParticipanse(
		@PathVariable("id") Integer id,
		
		@ModelAttribute("feedbackAddInput")
	    @Valid FeedbackInput form,
	    
	    BindingResult result, ModelMap model) {
		
		Participanse participanse = participanseBO.getById(id);
		
		return "feedback/add";
	}
	

    
}
