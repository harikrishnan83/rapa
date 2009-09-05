package org.rest.rapa;

import org.rest.rapa.formatter.Formats;

public class RestClientBuilder {
	private String url;
	private String password = "";
	private String username = "";
	private String host;
	private int port;
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
	public RestClientBuilder withHost(String host){
		this.host = host;
		return this;
	}
	
	public RestClientBuilder withPort(int port){
		this.port = port;
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
	
	public RestClient build(){
		return new RestClient(url, username, password, host, port, scheme, realm, format, formatAsExtenstion);
	}
}
