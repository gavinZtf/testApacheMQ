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
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张腾飞--Gavin
 * 2017年6月21日
 *
 */
public class MySyncMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MySyncMessageConsumer.class);

    public static void main(String[] args) {
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

            // 等待一段时间，使得“拉”数据请求返回结果
            message = (TextMessage) consumer.receive(1000);

            System.out.println("Received message: " + message);
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
}
