package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.login.internal.UserRepository;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


//@SpringBootTest
//@TestConfiguration
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProfileRestControllerTest extends AbstractControllerTest {


    private final ProfileRestController profileRestController = new ProfileRestController();


    @Autowired
    protected ProfileMapper profileMapper;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ProfileRepository profileRepository;


    @Test
    public void shouldReturnProfileToWhenCallingGetMethod() {
	User testUser = userRepository.getExistedByEmail("user@gmail.com");
	AuthUser authUserTest = new AuthUser(testUser).get();
	ProfileTo expected = profileMapper.toTo(profileRepository.getOrCreate(testUser.id()));
	ProfileTo actual = profileRestController.get(authUserTest);
	int sizeExpected = expected.getContacts().size();
	int sizeActual = actual.getContacts().size();
	String stringExpected = expected.toString();
	String stringActual = actual.toString();
	Assertions.assertEquals(sizeExpected, sizeActual);
	Assertions.assertEquals(sizeExpected, sizeActual);
	Assertions.assertEquals(stringExpected, stringActual);
	Assertions.assertEquals(expected, actual);
    }


    @Test
    public void shouldSetProfileToWhenCallingPutMethod() {

	User testUser = new User(10L, "bla@bla.com", "qwerty", "firstName", "lastName", "displayName", Role.MANAGER);
	AuthUser authUserTest = new AuthUser(testUser).get();
	ProfileTo profileToTest = ProfileTestData.USER_PROFILE_TO;
	profileRestController.update(profileToTest, authUserTest);
	Set<@Valid ContactTo> expectedSetContact = profileToTest.getContacts();
	ProfileTo actualProfileTo = profileMapper.toTo(profileRepository.getOrCreate(testUser.id()));
	Set<@Valid ContactTo> actualSetContact = actualProfileTo.getContacts();
	int sizeExpected = expectedSetContact.size();
	int sizeActual = actualSetContact.size();
	String stringExpected = expectedSetContact.toString();
	String stringActual = actualSetContact.toString();
	Assertions.assertEquals(sizeExpected, sizeActual);
	Assertions.assertEquals(sizeExpected, sizeActual);
	Assertions.assertEquals(stringExpected, stringActual);

    }


//    плюс тесты на сохранение разных профилей "гость" , невалидный и пр.
}


