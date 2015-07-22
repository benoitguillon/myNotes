package org.bgi.hibernate.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.hibernate.search.backend.IndexingMonitor;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.impl.lucene.LuceneBackendQueueProcessor;
import org.hibernate.search.indexes.spi.DirectoryBasedIndexManager;
import org.hibernate.search.spi.WorkerBuildContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

public class JmsBackendQueueProcessor extends LuceneBackendQueueProcessor implements MessageListener {
	
	private JmsTemplate jmsTemplate;
	
	private SimpleMessageListenerContainer listener;
	
	@Override
	public void initialize(Properties props, WorkerBuildContext context, DirectoryBasedIndexManager indexManager) {
		super.initialize(props, context, indexManager);
		this.jmsTemplate = new JmsTemplate();
		this.listener = new SimpleMessageListenerContainer();
		this.listener.setMessageListener(this);
		this.listener.start();
	}

	@Override
	public void close() {
		this.listener.stop();
		super.close();
	}

	@Override
	public void applyStreamWork(LuceneWork singleOperation, IndexingMonitor monitor) {
		this.sendMessage(Arrays.asList(singleOperation));
		super.applyStreamWork(singleOperation, monitor);
	}

	@Override
	public void applyWork(List<LuceneWork> workList, IndexingMonitor monitor) {
		this.sendMessage(workList);
		super.applyWork(workList, monitor);
	}
	
	private void sendMessage(List<LuceneWork> workList) {
		final ArrayList<LuceneWork> workListToSend = new ArrayList<LuceneWork>(workList);
		this.jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(workListToSend);
				return message;
			}
		});
	}
	
	public void onMessage(Message message){
		
	}
	

}
