package com.manage.applepestmanagement.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * User: Swang
 * Date: 2018-12-29
 * Time: 11:06
 */
public class ActiveMqTest {

    //消息发送方
    @Test
    public void testSend() throws JMSException {
        //创建连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //从工厂中获取一个连接对象
        Connection connection = connectionFactory.createConnection();
        //连接mq服务
        connection.start();
        //获得session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//自动应答
        //通过session对象创建Topic
        Topic topic = session.createTopic("wangTopic");
        //通过session对象创建消息发送者
        MessageProducer producer = session.createProducer(topic);
        //通过session创建消息对象
        TextMessage textMessage = session.createTextMessage("hello word");
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    //消息接收端
    @Test
    public void testConsumer() throws JMSException {
        //创建连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //从工厂中获取一个连接对象
        Connection connection = connectionFactory.createConnection();
        //连接mq服务
        connection.start();
        //获得session对象
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);//自动应答
        //通过session对象创建Topic
        Topic topic = session.createTopic("wangTopic");
        //通过session对象创建消息消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //指定消息监听器
        consumer.setMessageListener(new MessageListener() {
            //当监听的topic中存在消息，这个方法自动执行
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    if (textMessage.getText().equals("1hello word")) {
                        System.out.println("消费者接收到的消息："+textMessage.getText());
                        //客户端手动应答
                        message.acknowledge();
                    } else {
                        System.out.println("消息处理失败了");
                        //通知mq进行消息重发，最多重发6次
                        session.recover();
                        int i = 1/0;
                        //客户端手动应答
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //资源不能关闭，需要一直监听消息队列
        while(true) {

        }
    }
}
