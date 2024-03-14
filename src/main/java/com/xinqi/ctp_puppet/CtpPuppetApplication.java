package com.xinqi.ctp_puppet;


import com.xinqi.ctp_puppet.netty.tcp.server.TradeTcpServerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableAsync
@EnableScheduling
@ComponentScan(value = {"com.xinqi.ctp_puppet.netty"})
public class CtpPuppetApplication {

	public static void main(String[] args) throws IOException, DeploymentException {
		int port = 8089;
		SpringApplication app = new SpringApplication(CtpPuppetApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", port));
		app.run(args);
		log.info("Spring Boot服务启动成功，端口号为" + port);

		// 打印硬件信息
		getLocalHostInfo();

		TradeTcpServerFactory.initTradeTcpServer();

		InetAddress localHost = InetAddress.getLocalHost();
		TradeTcpServerFactory.initWSClient(localHost.getHostName());
	}

	public static void getLocalHostInfo() {
		try {
			log.info("当前设备操作系统 : " + System.getProperty("os.name"));
			InetAddress ip = InetAddress.getLocalHost();
			log.info("当前设备主机名 : " + ip.getHostName());
			log.info("当前设备IP : " + ip.getHostAddress());

			List<String> macAddress = getMACAddress();
			for (String address : macAddress) {
				log.info("当前设备MAC地址 : " + address);
			}
		} catch (Exception e) {
			log.error("获取机器信息失败", e);
		}
	}
	private static List<String> getMACAddress() throws SocketException {
		Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
		List<String> list = new ArrayList<>();
		while (nis.hasMoreElements()) {
			NetworkInterface ni = nis.nextElement();
			if (ni != null && ni.isUp()) {
				byte[] bytes = ni.getHardwareAddress();
				if (bytes != null && bytes.length == 6) {
					StringBuilder sb = new StringBuilder();
					for (byte b : bytes) {
						sb.append(Integer.toHexString((b & 240) >> 4));
						sb.append(Integer.toHexString(b & 15));
						sb.append("-");
					}
					sb.deleteCharAt(sb.length() - 1);
					list.add(sb.toString().toUpperCase());
				}
			}
		}
		return list;
	}

}
