package com.ztf.api.test;

import org.apache.activemq.broker.BrokerService;

public class Test {

	public static void main(String[] args) {
		try {
			BrokerService broker = new BrokerService();
			// configure the broker
			broker.setBrokerName("fred");
			broker.addConnector("tcp://192.168.187.128:61616");
			 
			broker.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
