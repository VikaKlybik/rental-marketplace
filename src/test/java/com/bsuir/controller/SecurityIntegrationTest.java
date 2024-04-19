package com.bsuir.controller;

import com.bsuir.repository.UserRepository;
import com.bsuir.service.AuthenticationService;
import com.bsuir.utils.UserSetup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecurityIntegrationTest {

    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
        setupUser();
    }

    private void setupUser() {
        UserSetup.registerUser(authenticationService, userRepository);
    }

    @Test
    @Order(1)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void getAdminPage_NotValidRole_AccessDenied() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                        .andExpect(content().string(containsString("Ошибка доступа")));
    }

    @Test
    @Order(2)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void createArticle_ValidBody_Success() throws Exception {
        mockMvc.perform(get("/article/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ошибка доступа")));
    }

}