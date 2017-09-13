package cn.birdstudio.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import cn.birdstudio.domain.User;
import cn.birdstudio.service.UserService;

/**
 * 转换器
 * 
 * @author Sam Zhang
 */
public class SampleItemProcessor implements ItemProcessor<User, User> {
	private static final Logger logger = LoggerFactory.getLogger(SampleItemProcessor.class);
	@Autowired
	private UserService userService;

	@Override
	public User process(final User info) throws Exception {
		logger.info("process logic");
		User oldValue = userService.getUser(info.getName());
		if (oldValue != null)
			info.setId(oldValue.getId());
		return info;
	}

}
