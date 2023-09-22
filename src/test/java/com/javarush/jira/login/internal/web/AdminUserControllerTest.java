package com.javarush.jira.login.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.login.internal.UserRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.AdminUserController.REST_URL;
import static com.javarush.jira.login.internal.web.UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminUserControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';


    @Autowired
    private UserRepository userRepository;


    @Order(1)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
	perform(MockMvcRequestBuilders.get(REST_URL_SLASH + ADMIN_ID))
		.andExpect(status().isOk())
		.andDo(print())
		// https://jira.spring.io/browse/SPR-14472
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(USER_MATCHER.contentJson(admin));
    }


    @Order(2)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
	perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
		.andDo(print())
		.andExpect(status().isNotFound());
    }


    @Order(3)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByEmail() throws Exception {
	perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-email?email=" + admin.getEmail()))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(USER_MATCHER.contentJson(admin));
    }


    @Order(4)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
	perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + USER_ID))
		.andDo(print())
		.andExpect(status().isNoContent());
	assertFalse(userRepository.findById(USER_ID).isPresent());
    }


    @Order(5)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
	perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + NOT_FOUND))
		.andDo(print())
		.andExpect(status().isNotFound());
    }


    @Order(6)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void enableNotFound() throws Exception {
	perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + NOT_FOUND)
		.param("enabled", "false")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
    }


    @Order(7)
    @Test
    void getUnauthorized() throws Exception {
	perform(MockMvcRequestBuilders.get(REST_URL))
		.andExpect(status().isUnauthorized());
	Thread.sleep(5000);
    }


//    @Order(8)
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void getForbidden() throws Exception {
//	perform(MockMvcRequestBuilders.get(REST_URL))
//		.andExpect(status().isForbidden());
//	Thread.sleep(5000);
//    }
//
//
//    @Order(9)
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void update() throws Exception {
//	User updated = getUpdated();
//	updated.setId(null);
//	perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
//		.contentType(MediaType.APPLICATION_JSON)
//		.content(jsonWithPassword(updated, "newPass")))
//		.andDo(print())
//		.andExpect(status().isNoContent());
//
//	USER_MATCHER.assertMatch(userRepository.getExisted(USER_ID), getUpdated());
//	Thread.sleep(5000);
//    }


//    @Order(10)
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void createWithLocation() throws Exception {
//	User newUser = getNew();
//	ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
//		.contentType(MediaType.APPLICATION_JSON)
//		.content(jsonWithPassword(newUser, "newPass")))
//		.andExpect(status().isConflict());
//
//	User created = USER_MATCHER.readFromJson(action);
//	long newId = created.id();
//	newUser.setId(newId);
//	USER_MATCHER.assertMatch(created, newUser);
//	USER_MATCHER.assertMatch(userRepository.getExisted(newId), newUser);
//	Thread.sleep(5000);
//    }


//    @Order(11)
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getAll() throws Exception {
//	perform(MockMvcRequestBuilders.get(REST_URL))
//		.andExpect(status().isOk())
//		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//		.andExpect(USER_MATCHER.contentJson(admin, guest, manager, user));
//	Thread.sleep(5000);
//    }


//    @Order(12)
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void enable() throws Exception {
//	perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + USER_ID)
//		.param("enabled", "false")
//		.contentType(MediaType.APPLICATION_JSON))
//		.andDo(print())
//		.andExpect(status().isNoContent());
//
//	assertFalse(userRepository.getExisted(USER_ID).isEnabled());
//    }


    @Order(13)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
	User invalid = new User(null, "", null, "Aa", "", "", Role.DEV, Role.ADMIN);
	perform(MockMvcRequestBuilders.post(REST_URL)
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonWithPassword(invalid, "newPass")))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity());
    }


    @Order(14)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
	User invalid = new User(user);
	invalid.setFirstName("");
	perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(invalid)))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity());
    }


    @Order(15)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
	User updated = new User(user);
	updated.setFirstName("<script>alert(123)</script>");
	perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(updated)))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity());
	Thread.sleep(5000);
    }


    @Order(16)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
	User updated = new User(admin);
	updated.setEmail(USER_MAIL);
	perform(MockMvcRequestBuilders.put(REST_URL_SLASH + ADMIN_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(updated)))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity())
		.andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
	Thread.sleep(5000);
    }


    @Order(17)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicateEmail() throws Exception {
	User expected = new User(null, USER_MAIL, "newPass", "duplicateFirstName", "duplicateLastName",
		"duplicateDisplayName", Role.DEV);
	perform(MockMvcRequestBuilders.post(REST_URL)
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonWithPassword(expected, "newPass")))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity())
		.andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
	Thread.sleep(5000);
    }


//    @Order(18)
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void changePassword() throws Exception {
//	perform(MockMvcRequestBuilders.post(REST_URL_SLASH + USER_ID + "/change_password")
//		.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//		.param("oldPassword", "password")
//		.param("newPassword", "changedPassword"))
//		.andDo(print())
//		.andExpect(status().isNoContent());
//
//	assertTrue(PASSWORD_ENCODER.matches("changedPassword", userRepository.getExisted(USER_ID).getPassword()),
//		"Wrong changed password");
//	Thread.sleep(5000);
//    }


    @Order(19)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void changePasswordInvalid() throws Exception {
	perform(MockMvcRequestBuilders.post(REST_URL_SLASH + USER_ID + "/change_password")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
		.param("oldPassword", "password")
		.param("newPassword", "cp"))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity());
    }
}
