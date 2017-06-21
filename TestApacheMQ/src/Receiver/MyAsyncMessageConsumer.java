/**
 * 
 */
package Receiver;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张腾飞--Gavin 2017年6月21日
 *
 */
public class MyAsyncMessageConsumer implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MyAsyncMessageConsumer.class);

	public static void main(String[] args) throws InterruptedException {
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageConsumer consumer = null;
		Message message = null;
		boolean useTransaction = false;
		try {
			connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.187.128:61616");
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);

			destination = session.createQueue("TEST.QUEUE");

			consumer = session.createConsumer(destination);

			// 注册一个监听器，使得实现异步消费消息
			consumer.setMessageListener(new MyAsyncMessageConsumer());

			Thread.sleep(1000);
		} catch (JMSException jmsEx) {
			logger.error("", jmsEx);
		} finally {
			if (consumer != null) {
				try {
					consumer.close();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			System.out.println("Received Message:" + message);
		}
	}

}
