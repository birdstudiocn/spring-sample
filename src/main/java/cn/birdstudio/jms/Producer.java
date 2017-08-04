package cn.birdstudio.jms;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	public void send(Queue queue, Message msg) {
		this.jmsMessagingTemplate.convertAndSend(queue, msg);
	}
}
