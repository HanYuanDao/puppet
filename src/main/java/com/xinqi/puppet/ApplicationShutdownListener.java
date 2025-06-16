package com.xinqi.puppet;


import com.xinqi.puppet.netty.gateway.tcp.server.TradeTcpServerFactory;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/3 14:15
 **/
@Component
public class ApplicationShutdownListener {

	@EventListener
	public void onApplicationEvent(ContextClosedEvent event) {
		TradeTcpServerFactory.shutdownTradeTcpServer();
	}
}
