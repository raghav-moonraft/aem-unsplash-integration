package com.mysite.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.mysite.core.service.UnsplashService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Component;

import com.mysite.core.utils.UnsplashHtmlBuilder;
import org.osgi.service.component.annotations.Reference;

@Component(service = Servlet.class, property = { ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/unsplash",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET })
public class UnsplashServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	@Reference
	private UnsplashService service;

	private UnsplashHtmlBuilder htmlBuilder = new UnsplashHtmlBuilder();

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {

		final String query = request.getRequestParameter("query").getString().trim();
		final String pageNum = request.getRequestParameter("page").getString();
		String responseHTML = null;
		HttpGet httpGet = new HttpGet(extractUrl(query, pageNum));
		final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		httpGet.addHeader("Content-Type", "application/json");
		final HttpResponse httpResponse = httpClient.execute(httpGet);
		final HttpEntity entity = httpResponse.getEntity();
		String content = EntityUtils.toString(entity);
		if (StringUtils.isNotBlank(query)) {
			final String[] results = content.split("results");
			content = results[1].substring(2, results[1].length()-1);
		}

		try {
			responseHTML = htmlBuilder.getHTMLResponse(content);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.getWriter().write(responseHTML);
	}

	/**
	 * Generate the URL
	 * @param query search query value
	 * @param pageNum page number for search/list photos result
	 * @return URL
	 */
	private String extractUrl(final String query, final String pageNum) {
		String url;

		final String unsplashUrl = service.getUnsplashUrl() + "?client_id=" + service.getClientId() + "&per_page="
				+ service.getLimitPerPage() + "&page=";
		final String unsplashSearchUrl = service.getUnsplashSearchUrl() + "?client_id=" + service.getClientId() + "&per_page="
				+ service.getLimitPerPage() + "&page=";

		if (StringUtils.isNotBlank(query)) {
			url = unsplashSearchUrl + pageNum + "&query=" + query;
		} else  {
			url = unsplashUrl + pageNum;
		}
		return url;
	}
}
