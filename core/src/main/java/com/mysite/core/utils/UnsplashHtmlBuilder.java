package com.mysite.core.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysite.core.bean.UnsplashBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * HtmlBuilder. The purpose of this class is to build Coral-Card html for unsplash images. This html is rendered when
 * author selects Unsplash from dropdown list in assets and display the thumb images that are retrieved from Unsplash.com
 * as a JSON response via rest api call.
 */
public class UnsplashHtmlBuilder {
	
	private static final Logger LOG = LoggerFactory.getLogger(UnsplashHtmlBuilder.class);

	/**
	 * Create coral-card html from JSON Response
	 * @param response
	 * @return coral card HTML
	 * @throws JSONException
	 */
	public String getHTMLResponse(String response) throws JSONException, UnsupportedEncodingException {
		String html = null;
		UnsplashBean beanResponse = new UnsplashBean();
		JSONArray responseArray = new JSONArray(response);
		StringBuilder htmlBuilder = new StringBuilder();
		for (int i = 0; i < responseArray.length(); ++i) {
		    JSONObject object = responseArray.getJSONObject(i);
		    String title = (object.has("alt_description") && !object.isNull("alt_description")) ? object.getString("alt_description") : null;
		    String imageSrc = object.getJSONObject("urls").getString("thumb");
		    int imageWidth = object.getInt("width");
		    int imageHeight = object.getInt("height");
		    String username = object.getJSONObject("user").getString("name");
		    String userProfile = object.getJSONObject("user").getJSONObject("links").getString("html");
			String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.name());
		    
			htmlBuilder.append("<coral-card class=\"editor-Card-asset card-asset cq-draggable u-coral-openHand\" draggable=\"true\"" + "data-path=\"" + imageSrc + "&author=" + encodedUsername + "&profile=" + userProfile + "\"" + "data-asset-group=\"media\" data-type=\"Images\" data-param=\"{&#34;./imageMap@Delete&#34;:&#34;&#34;,&#34;./imageCrop@Delete&#34;:&#34;&#34;,&#34;./imageRotate@Delete&#34;:&#34;&#34;}\" data-asset-mimetype=\"image/png\">");
			htmlBuilder.append("<coral-card-asset>\r\n" + 
					"        <img class=\"cq-dd-image\" src=\"" + imageSrc + "alt=\"" + title + ">\r\n" + "\"" +
					"    </coral-card-asset>");
			htmlBuilder.append("<coral-card-content>\r\n" + 
					"        <coral-card-title class=\"foundation-collection-item-title\" title=\"" + title + ">" + title + "</coral-card-title>\r\n" + 
					"        <coral-icon class=\"editor-Card-viewInAdmin\" icon=\"edit\" size=\"XS\" aria-label=\"Edit\" title=\"Edit\"></coral-icon>\r\n" + 
					"        <coral-card-propertylist>\r\n" + 
					"            <coral-card-property>" + imageWidth +" x " + imageHeight + " | " + username + "</coral-card-property>\r\n" + 
					"        </coral-card-propertylist>\r\n" + 
					"    </coral-card-content>");
			htmlBuilder.append("</coral-card>");
			html = htmlBuilder.toString();
		}
		
		return html;
		
	}

}
