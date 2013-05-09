package pl.wsiadamy.common.security;

import java.io.Serializable;
import java.util.Map;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Athlan
 *
 */
@Component
public class BasePermissionEvaluator implements PermissionEvaluator, Serializable {
	private Map<String, Permission> permissionNameToPermissionMap;
	
	private BasePermissionEvaluator() {
	}
	
	public BasePermissionEvaluator(Map<String, Permission> permissionNameToPermissionMap) {
		this.permissionNameToPermissionMap = permissionNameToPermissionMap;
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		
		if(authentication == null || targetDomainObject == null || !(permission instanceof String))
			return false;
		
		if(null == permissionNameToPermissionMap || !permissionNameToPermissionMap.containsKey(permission))
			return false;
		
		Permission permissionObject = permissionNameToPermissionMap.get(permission);
		
		if(null == permissionObject)
			return false;
		
		return permissionObject.isAllowed(authentication, targetDomainObject);
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new RuntimeException("Id and Class permissions are not supperted by " + this.getClass().toString());
	}

}
