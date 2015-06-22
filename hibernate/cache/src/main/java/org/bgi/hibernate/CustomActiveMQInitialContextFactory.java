package org.bgi.hibernate;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import net.sf.ehcache.distribution.jms.JMSUtil;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.jndi.ReadOnlyContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomActiveMQInitialContextFactory implements InitialContextFactory {
	
	private static final Log LOG = LogFactory.getLog(CustomActiveMQInitialContextFactory.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public Context getInitialContext(Hashtable environment) throws NamingException {
		Map<String, Object> data = new ConcurrentHashMap<String, Object>();
		
		String providerUrl = (String)environment.get("java.naming.provider.url");
		if(providerUrl == null){
			throw new NamingException("Expected " + JMSUtil.PROVIDER_URL + " property");
		}
		
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(providerUrl);
		
		String topicConnectionFactoryBindingName = (String) environment.get(JMSUtil.TOPIC_CONNECTION_FACTORY_BINDING_NAME);
		bind(data, topicConnectionFactoryBindingName, factory);
		
		String queueConnectionFactoryBindingName = (String) environment.get(JMSUtil.GET_QUEUE_CONNECTION_FACTORY_BINDING_NAME);
		bind(data, queueConnectionFactoryBindingName, factory);
		
		String topicBindingName = (String) environment.get(JMSUtil.REPLICATION_TOPIC_BINDING_NAME);
		ActiveMQTopic topic = new ActiveMQTopic(topicBindingName);
		bind(data, topicBindingName, topic);
		
		String queueBindingName = (String) environment.get(JMSUtil.GET_QUEUE_BINDING_NAME);
		ActiveMQQueue queue = new ActiveMQQueue(queueBindingName);
		bind(data, queueBindingName, queue);
		
		
		
		return new ReadOnlyContext(environment, data);
	}
	
	private void bind(Map<String, Object> data, String name, Object object){
		data.put(name, object);
		if(LOG.isDebugEnabled()){
			LOG.debug("Binding " + name + " to object " + object);
		}
		
	}
}
