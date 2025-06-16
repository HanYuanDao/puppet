package com.xinqi.puppet.ws;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 6/30/21 10:34 AM
 **/
@Configuration
public class WebSocketConfig {

	@Bean
	public ServerEndpointExporter serverEndpointExporter(){
		return new ServerEndpointExporter();
	}

	@Bean
	public WebSocketEndpointConfigure newConfigure() {
		return new WebSocketEndpointConfigure();
	}
}
