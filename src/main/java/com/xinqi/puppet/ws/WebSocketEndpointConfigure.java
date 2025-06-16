package com.xinqi.puppet.ws;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 6/30/21 2:49 PM
 **/
public class WebSocketEndpointConfigure extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

	private static volatile BeanFactory context;

	@Override
	public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
		return context.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		WebSocketEndpointConfigure.context = applicationContext;
	}

}
