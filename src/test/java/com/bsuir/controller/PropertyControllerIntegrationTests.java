package com.bsuir.controller;

import com.bsuir.entity.Bookmark;
import com.bsuir.entity.Property;
import com.bsuir.entity.Rating;
import com.bsuir.enums.PropertyStatus;
import com.bsuir.repository.BookmarkRepository;
import com.bsuir.repository.PropertyRepository;
import com.bsuir.repository.RatingRepository;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.AuthenticationService;
import com.bsuir.utils.UserSetup;
import com.google.common.net.MediaType;
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

import java.util.List;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.PROPERTY_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PropertyControllerIntegrationTests {

    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private RatingRepository ratingRepository;
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
    @Order(2)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void createProperty_ValidBody_Success() throws Exception {
        Long id = 1L;

        mockMvc.perform(post("/property/create")
                        .contentType(String.valueOf(MediaType.FORM_DATA))
                        .param("title", "test")
                        .param("description", "test")
                        .param("price", "123")
                        .param("propertyCategoryId", "1"))
                .andExpect(status().is3xxRedirection());

        assertThat(propertyRepository.existsById(id))
                .isTrue();
    }

    @Test
    @Order(1)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void createProperty_InValidBody_BadRequest() throws Exception {
        mockMvc.perform(post("/property/create")
                        .contentType(String.valueOf(MediaType.FORM_DATA))
                        .param("description", "test")
                        .param("price", "123")
                        .param("propertyCategoryId", "1"))
                .andExpect(status().isOk());

        assertThat(propertyRepository.findAll().isEmpty())
                .isTrue();
    }

    @Test
    @Order(1)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void getById_UnExistingId_NotFound() throws Exception {
        Long id = 1L;

        mockMvc.perform(get(String.format("/property/%d", id)))
                .andExpect(content().string(containsString(PROPERTY_NOT_FOUND)));

        assertThat(propertyRepository.findAll().isEmpty())
                .isTrue();
    }

    @Test
    @Order(4)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void updateProperty_ValidBody_Success() throws Exception {
        Long id = 1L;
        String updatedTitle = "test2";
        mockMvc.perform(post("/property/create")
                        .contentType(String.valueOf(MediaType.FORM_DATA))
                        .param("id", "1")
                        .param("title", updatedTitle)
                        .param("description", "test")
                        .param("price", "123")
                        .param("propertyCategoryId", "1"))
                .andExpect(status().is3xxRedirection());

        Property property = propertyRepository.findById(id)
                .orElseThrow();
        property.setPropertyStatus(PropertyStatus.AVAILABLE);
        propertyRepository.save(property);
        assertThat(property.getTitle())
                .isEqualTo(updatedTitle);
    }

    @Test
    @Order(5)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void getAllProperty_ExistingProperty_Success() throws Exception {

        mockMvc.perform(get("/property"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("Сейчас нету данных, которые могли бы отобразиться!"))));
    }

    @Test
    @Order(10)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void createBookmark_ExistingProperty_Success() throws Exception {
        Long propertyId = 1L;

        mockMvc.perform(post("/bookmark/create/"+propertyId))
                .andExpect(status().is3xxRedirection());

        Bookmark bookmark = bookmarkRepository.findByUserUsername("test")
                .orElseThrow();
        Boolean isCreated = bookmark.getProperty().stream()
                .map(Property::getId)
                .anyMatch(propertyId::equals);
        assertThat(isCreated)
                .isTrue();
    }

    @Test
    @Order(6)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void createRatingForUser_ExistingProperty_Success() throws Exception {

        mockMvc.perform(post("/rating/1")
                        .contentType(String.valueOf(MediaType.FORM_DATA))
                        .param("userId", "1")
                        .param("rating", "5")
                        .param("comment", "test"))
                .andExpect(status().is3xxRedirection());

        List<Rating> ratings = ratingRepository.findAllByForUserId(1L);
        Boolean isCreated = ratings.stream()
                .map(Rating::getComment)
                .anyMatch("test"::equals);
        assertThat(isCreated)
                .isTrue();
    }

    @Test
    @Order(7)
    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Sql(scripts = "classpath:data/data.sql")
    public void deleteRatingForUser_ExistingProperty_Success() throws Exception {

        mockMvc.perform(get("/rating/delete/1/1"))
                .andExpect(status().is3xxRedirection());

        List<Rating> ratings = ratingRepository.findAllByForUserId(1L);
        Boolean isCreated = ratings.stream()
                .map(Rating::getComment)
                .anyMatch("test"::equals);
        assertThat(isCreated)
                .isFalse();
    }
}