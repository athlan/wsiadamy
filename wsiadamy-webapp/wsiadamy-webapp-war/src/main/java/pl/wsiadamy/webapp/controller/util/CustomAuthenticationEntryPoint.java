package pl.wsiadamy.webapp.controller.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

@Component(value = "customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private String loginPageUrl;
	
	private boolean returnParameterEnabled;
	
	private String returnParameterName;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException reason) throws IOException, ServletException {
		
		if(null == loginPageUrl || "".equals(loginPageUrl))
			throw new RuntimeException("loginPageUrl has not been defined");
		
		String redirectUrl = loginPageUrl;
		
		if(isReturnParameterEnabled()) {
			String redirectUrlReturnParameterName = getReturnParameterName();
			
			if(null == redirectUrlReturnParameterName || "".equals(redirectUrlReturnParameterName))
				throw new RuntimeException("redirectUrlReturnParameterName has not been defined");
			
			redirectUrl += "?" + redirectUrlReturnParameterName + "=" + request.getServletPath();
		}
		
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, redirectUrl);
		
		return;
	}

	public String getLoginPageUrl() {
		return loginPageUrl;
	}

	public void setLoginPageUrl(String loginPageUrl) {
		this.loginPageUrl = loginPageUrl;
	}

	public boolean isReturnParameterEnabled() {
		return returnParameterEnabled;
	}

	public void setReturnParameterEnabled(boolean returnParameterEnabled) {
		this.returnParameterEnabled = returnParameterEnabled;
	}

	public String getReturnParameterName() {
		return returnParameterName;
	}

	public void setReturnParameterName(String returnParameterName) {
		this.returnParameterName = returnParameterName;
	}

}
