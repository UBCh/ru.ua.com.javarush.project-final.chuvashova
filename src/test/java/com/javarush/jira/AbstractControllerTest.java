package com.javarush.jira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


//https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications
@ActiveProfiles("test")
//@Sql(scripts = {"classpath:db/changelog/db.changelogH2-master.yaml"}, config = @SqlConfig(encoding = "UTF-8"))
@AutoConfigureMockMvc
//https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications-testing-with-mock-environment
public abstract class AbstractControllerTest extends BaseTests {

    @Autowired
    private MockMvc mockMvc;


    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
	return mockMvc.perform(builder);
    }

}
