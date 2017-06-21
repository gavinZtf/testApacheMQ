/**
 * 
 */
package com.ztf.mymq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张腾飞--Gavin 2017年6月21日
 *
 */
public class MyProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(MyProducer.class);

	public static void main(String[] args) {
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer producer = null;
		Message message = null;
		boolean useTransaction = false;

		try {
			connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.187.128:61616");
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);

			destination = session.createQueue("TEST.QUEUE");

			producer = session.createProducer(destination);

			message = session.createTextMessage("this is a test");

			producer.send(message);

		} catch (JMSException jmsEx) {
			logger.error("", jmsEx);
		} finally {
			if (producer != null) {
				try {
					producer.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}
	}

}
