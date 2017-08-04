/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.birdstudio;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.birdstudio.order.service.OrderService;
import cn.birdstudio.user.domain.User;

/**
 * @author Sam Zhang
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@EnableTransactionManagement
@EnableJms
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Resource
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Queue sellerQueue() {
		return new ActiveMQQueue("seller");
	}

	@Bean
	public Queue buyerQueue() {
		return new ActiveMQQueue("buyer");
	}

	@PostConstruct
	public void doSomething() {
		User jenni = new User(1, "Jenni");
		User emma = new User(2, "Emma");
		int amount = 1;
		orderService.sold(jenni.getId(), emma.getId(), 1);
		logger.info("Jenni sold " + amount + " book/books");
		logger.info("Jenni bounght " + amount + " book/books");
	}

}
