package com.javarush.jira.bugtracking.sprint;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.sprint.to.SprintTo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.bugtracking.sprint.SprintTestData.NOT_FOUND;
import static com.javarush.jira.bugtracking.sprint.SprintTestData.*;
import static com.javarush.jira.common.BaseHandler.REST_URL;
import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SprintControllerTest extends AbstractControllerTest {
    private static final String SPRINTS_REST_URL = REST_URL + "/sprints/";


    private static final String SPRINTS_BY_PROJECT_REST_URL = SPRINTS_REST_URL + "by-project";


    private static final String SPRINTS_BY_PROJECT_AND_STATUS_REST_URL = SPRINTS_REST_URL + "by-project-and-status";


    private static final String MNGR_SPRINTS_REST_URL = REST_URL + "/mngr/sprints";


    private static final String MNGR_SPRINTS_REST_URL_SLASH = REST_URL + "/mngr/sprints/";


    private static final String PROJECT_ID = "projectId";


    private static final String STATUS_CODE = "statusCode";


    private static final String ENABLED = "enabled";


    @Autowired
    SprintRepository repository;


    @Order(1)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_REST_URL + SPRINT1_ID))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(SPRINT_TO_MATCHER.contentJson(sprintTo1))
		.andExpect(jsonPath("$.enabled", is(false)));
    }


    @Order(2)
    @Test
    void getUnauthorized() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_REST_URL + SPRINT1_ID))
		.andExpect(status().isUnauthorized());
    }


    @Order(3)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_REST_URL + NOT_FOUND))
		.andExpect(status().isNotFound());
    }


    @Order(4)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByProject() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_BY_PROJECT_REST_URL)
		.param(PROJECT_ID, String.valueOf(PROJECT1_ID)))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(SPRINT_TO_MATCHER.contentJson(sprintTo4, sprintTo3, sprintTo2, sprintTo1));
    }


    @Order(5)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByProjectEnabled() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_BY_PROJECT_REST_URL)
		.param(PROJECT_ID, String.valueOf(PROJECT1_ID))
		.param(ENABLED, "true"))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(SPRINT_TO_MATCHER.contentJson(sprintTo4, sprintTo3, sprintTo2));
    }


    @Order(6)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByProjectNotFound() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_BY_PROJECT_REST_URL)
		.param(PROJECT_ID, String.valueOf(NOT_FOUND)))
		.andExpect(status().isNotFound());
    }


    @Order(7)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByProjectAndStatus() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_BY_PROJECT_AND_STATUS_REST_URL)
		.param(PROJECT_ID, String.valueOf(PROJECT1_ID))
		.param(STATUS_CODE, ACTIVE))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(SPRINT_TO_MATCHER.contentJson(sprintTo3, sprintTo2));
    }


    @Order(8)
    @Test
    void getAllByProjectAndStatusUnauthorized() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_BY_PROJECT_AND_STATUS_REST_URL)
		.param(PROJECT_ID, String.valueOf(PROJECT1_ID))
		.param(STATUS_CODE, ACTIVE))
		.andExpect(status().isUnauthorized());
    }


    @Order(9)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByProjectAndStatusNotFound() throws Exception {
	perform(MockMvcRequestBuilders.get(SPRINTS_BY_PROJECT_AND_STATUS_REST_URL)
		.param(PROJECT_ID, String.valueOf(NOT_FOUND))
		.param(STATUS_CODE, ACTIVE))
		.andExpect(status().isNotFound());
    }


//    @Order(10)
//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void createWithLocationWhenAdmin() throws Exception {
//	createWithLocation();
//    }
//
//
//    @Order(11)
//    @Test
//    @WithUserDetails(value = MANAGER_MAIL)
//    void createWithLocationWhenManager() throws Exception {
//	createWithLocation();
//    }
//
//
//    private void createWithLocation() throws Exception {
//	SprintTo newTo = getNewTo();
//	ResultActions action = perform(MockMvcRequestBuilders.post(MNGR_SPRINTS_REST_URL)
//		.contentType(MediaType.APPLICATION_JSON)
//		.content(writeValue(newTo)))
//		.andDo(print())
//		.andExpect(status().isConflict());
//
//	Sprint created = SPRINT_MATCHER.readFromJson(action);
//	long newId = created.id();
//	Sprint newSprint = new Sprint(newId, newTo.getCode(), newTo.getStatusCode(), newTo.getProjectId());
//	SPRINT_MATCHER.assertMatch(created, newSprint);
//	SPRINT_MATCHER.assertMatch(repository.getExisted(newId), newSprint);
//    }


    @Order(12)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
	perform(MockMvcRequestBuilders.post(MNGR_SPRINTS_REST_URL)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(getNewTo())))
		.andExpect(status().isForbidden());
    }


    @Order(13)
    @Test
    void createUnauthorized() throws Exception {
	perform(MockMvcRequestBuilders.post(MNGR_SPRINTS_REST_URL)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(getNewTo())))
		.andExpect(status().isUnauthorized());
    }


    @Order(14)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
	SprintTo invalidTo = new SprintTo(null, "", null, PROJECT1_ID);
	perform(MockMvcRequestBuilders.post(MNGR_SPRINTS_REST_URL)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(invalidTo)))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity());
    }


    @Order(15)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicateCode() throws Exception {
	SprintTo duplicateCodeTo = new SprintTo(null, sprintTo1.getCode(), ACTIVE, PROJECT1_ID);
	perform(MockMvcRequestBuilders.post(MNGR_SPRINTS_REST_URL)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(duplicateCodeTo)))
		.andDo(print())
		.andExpect(status().isConflict());
    }


    @Order(16)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWhenProjectNotExists() throws Exception {
	SprintTo notExistsProjectTo = new SprintTo(null, "new code", ACTIVE, NOT_FOUND);
	perform(MockMvcRequestBuilders.post(MNGR_SPRINTS_REST_URL)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(notExistsProjectTo)))
		.andDo(print())
		.andExpect(status().isConflict());
    }


    @Order(17)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateWhenAdmin() throws Exception {
	update();
    }


    @Order(18)
    @Test
    @WithUserDetails(value = MANAGER_MAIL)
    void updateWhenManager() throws Exception {
	update();
    }


    private void update() throws Exception {
	SprintTo updatedTo = getUpdatedTo();
	perform(MockMvcRequestBuilders.put(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(updatedTo)))
		.andDo(print())
		.andExpect(status().isNoContent());

	Sprint updated = new Sprint(updatedTo.getId(), updatedTo.getCode(), updatedTo.getStatusCode(), updatedTo.getProjectId());
	SPRINT_MATCHER.assertMatch(repository.getExisted(SPRINT1_ID), updated);
    }


    @Order(19)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
	perform(MockMvcRequestBuilders.put(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(getUpdatedTo())))
		.andExpect(status().isForbidden());
    }


    @Order(20)
    @Test
    void updateUnauthorized() throws Exception {
	perform(MockMvcRequestBuilders.put(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(getUpdatedTo())))
		.andExpect(status().isUnauthorized());
    }


    @Order(21)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateWhenProjectNotExists() throws Exception {
	SprintTo notExistsProjectTo = new SprintTo(SPRINT1_ID, "updated code", ACTIVE, NOT_FOUND);
	perform(MockMvcRequestBuilders.put(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(notExistsProjectTo)))
		.andDo(print())
		.andExpect(status().isConflict());
    }


    @Order(22)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
	SprintTo invalidTo = new SprintTo(SPRINT1_ID, "", null, PROJECT1_ID);
	perform(MockMvcRequestBuilders.put(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(invalidTo)))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity());
    }


    @Order(23)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateIdNotConsistent() throws Exception {
	perform(MockMvcRequestBuilders.put(MNGR_SPRINTS_REST_URL_SLASH + (SPRINT1_ID + 1))
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(getUpdatedTo())))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity());
    }


    @Order(24)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicateCode() throws Exception {
	SprintTo duplicateCodeTo = new SprintTo(SPRINT1_ID, sprintTo2.getCode(), ACTIVE, PROJECT1_ID);
	perform(MockMvcRequestBuilders.put(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(duplicateCodeTo)))
		.andDo(print())
		.andExpect(status().isNoContent());
    }


    @Order(25)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateWhenChangeProject() throws Exception {
	SprintTo changedProjectTo = new SprintTo(SPRINT1_ID, "updated code", ACTIVE, PROJECT1_ID + 1);
	perform(MockMvcRequestBuilders.put(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.contentType(MediaType.APPLICATION_JSON)
		.content(writeValue(changedProjectTo)))
		.andDo(print())
		.andExpect(status().isConflict());
    }


    @Order(26)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void changeStatusCodeWhenAdmin() throws Exception {
	changeStatusCode();
    }


    @Order(27)
    @Test
    @WithUserDetails(value = MANAGER_MAIL)
    void changeStatusCodeWhenManager() throws Exception {
	changeStatusCode();
    }


    private void changeStatusCode() throws Exception {
	perform(MockMvcRequestBuilders.patch(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID + "/change-status")
		.param(STATUS_CODE, ACTIVE))
		.andDo(print())
		.andExpect(status().isNoContent());

	assertEquals(ACTIVE, repository.getExisted(SPRINT1_ID).getStatusCode());
    }


    @Order(28)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void changeStatusCodeNotFound() throws Exception {
	perform(MockMvcRequestBuilders.patch(MNGR_SPRINTS_REST_URL_SLASH + NOT_FOUND + "/change-status")
		.param(STATUS_CODE, ACTIVE))
		.andDo(print())
		.andExpect(status().isNotFound());
    }


    @Order(29)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void changeStatusCodeForbidden() throws Exception {
	perform(MockMvcRequestBuilders.patch(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID + "/change-status")
		.param(STATUS_CODE, ACTIVE))
		.andExpect(status().isForbidden());
    }


    @Order(30)
    @Test
    void changeStatusCodeUnauthorized() throws Exception {
	perform(MockMvcRequestBuilders.patch(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID + "/change-status")
		.param(STATUS_CODE, ACTIVE))
		.andExpect(status().isUnauthorized());
    }


    @Order(40)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void enableWhenAdmin() throws Exception {
	enable();
    }


    @Order(41)
    @Test
    @WithUserDetails(value = MANAGER_MAIL)
    void enableWhenManager() throws Exception {
	enable();
    }


    private void enable() throws Exception {
	assertTrue(enable(SPRINT1_ID, true));
    }


    private boolean enable(long id, boolean enabled) throws Exception {
	perform(MockMvcRequestBuilders.patch(MNGR_SPRINTS_REST_URL_SLASH + id)
		.param(ENABLED, String.valueOf(enabled)))
		.andDo(print())
		.andExpect(status().isNoContent());
	return repository.getExisted(id).isEnabled();
    }


    @Order(42)
    @Test
    @WithUserDetails(value = USER_MAIL)
    void enableForbidden() throws Exception {
	perform(MockMvcRequestBuilders.patch(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.param(ENABLED, "true"))
		.andExpect(status().isForbidden());
    }


    @Order(43)
    @Test
    void enableUnauthorized() throws Exception {
	perform(MockMvcRequestBuilders.patch(MNGR_SPRINTS_REST_URL_SLASH + SPRINT1_ID)
		.param(ENABLED, "true"))
		.andExpect(status().isUnauthorized());
    }


    @Order(44)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void enableNotFound() throws Exception {
	perform(MockMvcRequestBuilders.patch(MNGR_SPRINTS_REST_URL_SLASH + NOT_FOUND)
		.param(ENABLED, "true"))
		.andExpect(status().isNotFound());
    }


    @Order(45)
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void disable() throws Exception {
	assertFalse(enable(sprintTo2.getId(), false));
    }
}