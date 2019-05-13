package com.training;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.training.bean.TestDIBean;
import com.training.bean.UserBean;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Training11ApplicationTests {
	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	TestDIBean testDIBean;

	@Test
	public void contextLoads() {
		UserBean user = testRestTemplate.getForObject("/returnData/bean", UserBean.class);
		assertThat(user).isEqualToComparingFieldByField(new UserBean(1, "mogo"));

		testDIBean.setId(1);
		assertThat(testDIBean.getId()).isEqualTo(1);
		
//		String userInfo = testRestTemplate.getForObject("/restTemplate/restTemplateBuilder", String.class);
//		assertThat(userInfo).isEqualTo("1 mogo");
	}
}
