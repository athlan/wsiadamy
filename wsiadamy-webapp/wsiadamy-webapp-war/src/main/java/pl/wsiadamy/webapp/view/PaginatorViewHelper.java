package pl.wsiadamy.webapp.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.webapp.controller.util.Paginator;

@Service(value="paginatorViewHelper")
public class PaginatorViewHelper {
	@Autowired
	ParametersViewHelper parametersHelper;
	
	public String display(Paginator paginator) {
		String paginatorParameterName = "p";
		
		StringBuilder out = new StringBuilder();
		
		int totalPages = paginator.getTotalPages();
		
		if(totalPages <= 1)
			return "";
		
		out.append("<div class=\"pagination\">");
		
		String url;
		
		// previous page
		if(paginator.getPage() != 1) {
			url = buildUrlPage(1, paginatorParameterName);
			out.append("<a href=\"" + url + "\" class=\"pageFirst\">&laquo;</a>");
			
			url = buildUrlPage(paginator.getPage() - 1, paginatorParameterName);
			out.append("<a href=\"" + url + "\" class=\"pagePrevious\">&lsaquo;</a>");
		}
		
		// iterate by pages
		for (int i = 1; i <= totalPages; i++) {
			url = buildUrlPage(i, paginatorParameterName);
			List<String> css = new ArrayList<String>();
			css.add("page");
			
			if(i == paginator.getPage())
				css.add("pageCurrent");
			
			out.append("<a href=\"" + url + "\" class=\"" + Joiner.on(" ").join(css) + "\">" + i + "</a>");
		}
		
		// next page
		if(paginator.getPage() != totalPages) {
			url = buildUrlPage(paginator.getPage() + 1, paginatorParameterName);
			out.append("<a href=\"" + url + "\" class=\"pageNext\">&rsaquo;</a>");
			
			url = buildUrlPage(totalPages, paginatorParameterName);
			out.append("<a href=\"" + url + "\" class=\"pageLast\">&raquo;</a>");
		}
		
		out.append("</div>");
		
		return out.toString();
	}
	
	protected String buildUrlPage(int page, String paginatorParameterName)
	{
		if(null == paginatorParameterName)
			paginatorParameterName = "p";
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put(paginatorParameterName, Integer.toString(page));
		return parametersHelper.buildUrl(urlParams);
	}
}
