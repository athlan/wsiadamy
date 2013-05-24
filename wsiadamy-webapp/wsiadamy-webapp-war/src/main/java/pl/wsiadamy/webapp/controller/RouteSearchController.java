package pl.wsiadamy.webapp.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.input.RouteSearchSimpleInput;
import pl.wsiadamy.common.model.wrapper.RouteSearchResultWrapper;
import pl.wsiadamy.webapp.view.ParametersViewHelper;
import pl.wsiadamy.webapp.view.RouteWaypointsViewHelper;

@Controller
@RequestMapping("/route")
public class RouteSearchController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;
	
	@ModelAttribute("routeSearchSimpleInput")
    public RouteSearchSimpleInput getSearchSimpleForm() {
        return new RouteSearchSimpleInput();
    }
	
//	@RequestMapping(value = "/search", method = RequestMethod.GET)
//    public String searchForm(ModelMap model) {
//        return "route/search";
//    }

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
    public String searchFormDisplay(
        @ModelAttribute("routeSearchSimpleInput")
        RouteSearchSimpleInput form,
        ModelMap model) {
		
        return "route/searchSplash";
 
    }

	@Autowired
	RouteWaypointsViewHelper routeWaypointsViewHelper;
	
	@Autowired
	ParametersViewHelper parametersViewHelper;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, params={"locationDestination"})
    public String searchFormProcess(
        @ModelAttribute("routeSearchSimpleInput")
        @Valid RouteSearchSimpleInput form,
//        @RequestParam(required = false) String dateTokenAfter,
        BindingResult result, ModelMap model, HttpServletRequest request) {
		
        if (result.hasErrors()) {
            return "route/search";
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
//        
//        if(null != dateTokenAfter) {
//        	params.put("dateTokenAfter", dateTokenAfter);
//        }
//        
//        List<RouteSearchResultWrapper> results = routeBO.findRoutes(params, form);
        
        // TODO.
//        try {
//	        if(null != request.getParameter("dateDeparture"))
//	        	form.setDateDeparture(request.getParameter("dateDeparture"));
//        }
//        catch(Throwable e) {}
//        
//        try {
//	        if(null != request.getParameter("dateToken"))
//	        	form.setDateToken(request.getParameter("dateToken"));
//        }
//        catch(Throwable e) {}
        int limit = 1;
        List<RouteSearchResultWrapper> results = routeBO.findRoutes(form, limit + 1);
        
        boolean hasNextPage = results.size() > limit;
        
        if(hasNextPage) {
        	RouteSearchResultWrapper resultsLast = results.get(limit);
        	results.remove(limit);
        	
            Map<String, String> urlParams = new HashMap<String, String>();
            
            urlParams.put("dateDepartureOffset", form.getDateDepartureOffsetFormat().format(resultsLast.getRoute().getDateDeparture()));
            urlParams.put("dateToken", form.getDateTokenFormat().format(resultsLast.getRoute().getDateLastModified()));
            model.addAttribute("pagerNextPage", parametersViewHelper.buildUrl(urlParams, request));
            
        }
        
        model.addAttribute("routeWaypointsViewHelper", routeWaypointsViewHelper);
        model.addAttribute("routes", results);
        
        return "route/search";
 
    }
}