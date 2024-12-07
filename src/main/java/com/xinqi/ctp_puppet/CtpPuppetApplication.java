package com.xinqi.ctp_puppet;


import com.xinqi.ctp_puppet.common.UserInfoVO;
import com.xinqi.ctp_puppet.netty.gateway.qdp.QdpTraderSpiImpl;
import com.xinqi.ctp_puppet.netty.gateway.tcp.server.TradeTcpServerFactory;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QryTradingAccountReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import qdp.qdptraderapi.CQdpFtdcTraderApi;
import qdp.qdptraderapi.QDP_TE_RESUME_TYPE;

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
		int port = 18089;
		SpringApplication app = new SpringApplication(CtpPuppetApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", port));
		app.run(args);
		log.info("Spring Boot服务启动成功，端口号为" + port);

		// 打印硬件信息
		getLocalHostInfo();

		//String libraryPath = System.getProperty("java.library.path");
		//log.info("Java Library Path: " + libraryPath);

		TradeTcpServerFactory.initTradeTcpServer();

		InetAddress localHost = InetAddress.getLocalHost();
		TradeTcpServerFactory.initWSClient(localHost.getHostName());
		log.info("创建ws客户端");

		//TODO JasonHan 触发qdp连接
		UserInfoVO faVO = new UserInfoVO(
				"tcp://140.207.230.90:20000", "guofu",
				"01020458","026429",
				"client_wxn_1.0","563d3da1757995e0c424a35ae6e02d00");
		TradeTcpServerFactory.start(faVO);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(10 * 1000);

						log.info("查询期货账号信息");
						QryTradingAccountReqVO qryTradingAccountReqVO = new QryTradingAccountReqVO();
						qryTradingAccountReqVO.setBrokerID(faVO.getBrokerId());
						qryTradingAccountReqVO.setInvestorID(faVO.getInvestorId());
						TradeTcpServerFactory.handler(qryTradingAccountReqVO);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
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
