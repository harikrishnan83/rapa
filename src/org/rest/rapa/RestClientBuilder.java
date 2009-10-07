package org.rest.rapa;

import java.net.MalformedURLException;

import org.rest.rapa.formatter.FormatHandlerFactory;
import org.rest.rapa.formatter.Formats;

public class RestClientBuilder {
	private String url;
	private String password = "";
	private String username = "";
	private String scheme = "";
	private String realm = "";
	private Formats format;
	private boolean formatAsExtenstion;
	
	public RestClientBuilder withUrl(String url){
		this.url = url;
		return this;
	}
	public RestClientBuilder withUserName(String username){
		this.username = username;
		return this;
	}
	public RestClientBuilder withPassword(String password){
		this.password = password;
		return this;
	}
	
	public RestClientBuilder withScheme(String scheme){
		this.scheme = scheme;
		return this;
	}
	
	public RestClientBuilder withRealm(String realm){
		this.realm = realm;
		return this;
	}
	
	public RestClientBuilder withFormat(Formats format){
		this.format = format;
		return this;
	}
	
	public RestClientBuilder useFormatAsExtension(){
		this.formatAsExtenstion = true;
		return this;
	}

	public RestClientBuilder donotUseFormatAsExtension(){
		this.formatAsExtenstion = false;
		return this;
	}
	
	public RestClient build() throws MalformedURLException{
		FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();
		formatHandlerFactory.create(format);
		HttpClientAdapterImpl httpClientAdapter = new HttpClientAdapterImpl(
		username, password, host(), port(), realm, scheme);
		HttpMethodProvider httpMethodProvider = new HttpMethodProvider();
		
		HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(httpClientAdapter, httpMethodProvider);
		Url resourceUrl = new Url(url, format.toString(), formatAsExtenstion);
		return new RestClient(resourceUrl,formatHandlerFactory.create(format),httpMethodExecutor);
	}
	
	private String host() throws MalformedURLException{
		return new Url(url, "", false).getHostName();
	}
	
	private int port() throws MalformedURLException{
		return new Url(url, "", false).getPort();
	}
	
}
