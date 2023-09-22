package com.javarush.jira.profile.internal.web;

import com.javarush.jira.common.util.validation.ValidationUtil;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.ProfileUtil;
import com.javarush.jira.profile.internal.model.Profile;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractProfileController {
    public static final String REST_URL = "/api/profile";


    @Autowired
    protected ProfileMapper profileMapper;


    @Autowired
    private ProfileRepository profileRepository;


    @GetMapping
    public ProfileTo get(@AuthenticationPrincipal AuthUser authUser) {
	return super.get(authUser.id());
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ProfileTo profileTo, @AuthenticationPrincipal AuthUser authUser) {
	log.info("update {}, user {}", profileTo, authUser.id());
	ValidationUtil.assureIdConsistent(profileTo, authUser.id());
	ValidationUtil.assureIdConsistent(profileTo.getContacts(), authUser.id());
	ProfileUtil.checkContactsExist(profileTo.getContacts());
	Profile profile = profileMapper.updateFromTo(profileRepository.getOrCreate(profileTo.id()), profileTo);
	profileRepository.save(profile);
    }
}

