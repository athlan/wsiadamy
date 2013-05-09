package pl.wsiadamy.webapp.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.security.BasePermissionEvaluator;

@Service(value="permissionHelper")
public class PermissionEvaluatorHelper {
	
	@Autowired
	BasePermissionEvaluator permissionEvaluator;
	
	public boolean hasPermission(Object targetDomainObject, Object permission) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);
	}
}
