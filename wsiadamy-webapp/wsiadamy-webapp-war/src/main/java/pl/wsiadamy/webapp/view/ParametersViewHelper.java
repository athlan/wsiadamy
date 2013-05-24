package pl.wsiadamy.webapp.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Joiner;

import org.springframework.stereotype.Service;

@Service(value = "parametersViewHelper")
public class ParametersViewHelper {
	public Map<String, String> getRequestParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		
		String param;
		for (@SuppressWarnings("unchecked")
		java.util.Enumeration<String> paramsNames = request.getParameterNames(); paramsNames.hasMoreElements();) {
			param = paramsNames.nextElement();
			params.put(param, request.getParameter(param));
		}
		
		return params;
	}

	public Map<String, String> buildParams(Map<String, String> params) {
		return buildParams(params, null);
	}

	public Map<String, String> buildParams(Map<String, String> params,
			Map<String, String> baseParams) {
		if (null == baseParams) {
			baseParams = new HashMap<String, String>();
		}

		for (Map.Entry<String, String> entry : params.entrySet()) {
			baseParams.put(entry.getKey(), entry.getValue());
		}

		return baseParams;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String buildUrl(Map<String, String> params) {
		return buildUrl(params, (Map) null);
	}

	public String buildUrl(Map<String, String> params,
			HttpServletRequest request) {
		return buildUrl(params, getRequestParams(request));
	}

	public String buildUrl(Map<String, String> params,
			Map<String, String> baseParams) {
		baseParams = buildParams(params, baseParams);

		if (baseParams.size() == 0)
			return "";

		String[] urlParams = new String[baseParams.size()];

		int i = 0;
		for (Map.Entry<String, String> entry : baseParams.entrySet()) {
			urlParams[i] = entry.getKey() + '=' + entry.getValue();
			++i;
		}

		return "?" + Joiner.on("&").join(urlParams);
	}
}
