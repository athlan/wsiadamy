package pl.wsiadamy.webapp.controller;
 
import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserData;
import pl.wsiadamy.common.security.util.AthenticationUtil;

@Controller
@RequestMapping("/account/data")
public class AccountDataJoinFacebookController {
	@Autowired
	UserBO userBO;

	@Resource
	Properties applicationSettings;

	@RequestMapping(value="/joinFacebookRemove", method = RequestMethod.GET)
	public String joinRemove(ModelMap model, HttpServletResponse response) throws IOException {
		User user = userBO.getById(AthenticationUtil.getUser().getId());
		UserData userData = user.getUserData();
		
		if(null != userData.getFacebookId()) {
			userData.setFacebookId(null);
			userBO.update(user);
		}
		
		return "redirect:/account/data";
	}
	
	@RequestMapping(value="/joinFacebook", method = RequestMethod.GET)
	public void login(ModelMap model, HttpServletResponse response) throws IOException {
		
		String appId = applicationSettings.getProperty("facebook.app.id");
		String appSecret = applicationSettings.getProperty("facebook.app.secret");
		String appScope = applicationSettings.getProperty("facebook.app.scope");
		
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(appId, appSecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope(appScope);
		params.setRedirectUri("http://wsiadamy.pl:8080/wsiadamy-webapp-war/account/data/joinFacebookCallback");
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, params);
		response.sendRedirect(authorizeUrl);
	}
	
	@RequestMapping(value="/joinFacebookCallback", method = RequestMethod.GET)
	public String loginCallback() {
		return "auth/facebookCallback";
	}
	
	@RequestMapping(value="/joinFacebookCallback", method = RequestMethod.GET, params = {"access_token"})
	public String loginCallback(ModelMap model, @RequestParam("access_token") String token) {
		
		String appId = applicationSettings.getProperty("facebook.app.id");
		String appSecret = applicationSettings.getProperty("facebook.app.secret");
		
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(appId, appSecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		
		AccessGrant accessGrant = new AccessGrant(token);
		Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
		
		FacebookTemplate facebook = new FacebookTemplate(token);
		FacebookProfile facebookProfile = facebook.userOperations().getUserProfile();
		
		User user2 = AthenticationUtil.getUser();
		
		if(null != user2) {
			Long userFacebookId = user2.getUserData().getFacebookId();
			
			if(null == userFacebookId) {
				userBO.createUserFacebookJoinAccount(user2, Long.valueOf(facebookProfile.getId()), facebookProfile.getEmail(), facebookProfile.getFirstName(), facebookProfile.getLastName());
			}
		}
		
		return "redirect:/account/data";
	}
}