package com.beehyv.nmsreporting.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import java.util.Arrays;

import static com.beehyv.nmsreporting.utils.Global.getPropertyValueApp;

@Configuration
@EnableJms
@ComponentScan(basePackages = {"com.beehyv.nmsreporting.listeners", "com.beehyv.nmsreporting.otherpackages"})
public class ActiveMQConfig {

    private static final String activeMqUrl = getPropertyValueApp("activeMqUrl");


    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(activeMqUrl);
        factory.setTrustAllPackages(false);
        factory.setTrustedPackages(Arrays.asList(
                "com.beehyv.nmsreporting.model",
                "com.beehyv.nmsreporting.entity",
                "java.util",
                "java.lang"
        ));
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // Optional: Configure additional settings
        factory.setConcurrency("1-5"); // 1-5 concurrent consumers
        factory.setRecoveryInterval(1000L); // 1 second recovery interval
        return factory;
    }
}