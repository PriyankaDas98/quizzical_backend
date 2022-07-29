package com.exam;

import com.exam.exception.UserNotFoundException;
import com.exam.model.User;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import com.exam.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExamserverApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
