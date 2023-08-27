package com.javarush.jira.bugtracking.task;

import com.javarush.jira.bugtracking.Handlers;
import com.javarush.jira.bugtracking.UserBelongRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = TagController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TagController {

    public static final String REST_URL = "/api/tasks/tag";


    private final TaskService taskService;


    private final ActivityService activityService;


    private final Handlers.TaskHandler handler;


    private final Handlers.ActivityHandler activityHandler;


    private final UserBelongRepository userBelongRepository;


    @GetMapping("/")
    public List<Tag> getAllTags() {
	log.info("get Tags all");
	return taskService.finByAllTags();
    }


    @GetMapping("/{id}")
    public Set<String> getAllTagsByTask(@RequestParam long taskId) {
	log.info("get all Tags for task" + taskId);
	return taskService.getAllTagsFoTask(taskId);
    }


    @PostMapping(path = "/addTag", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createNewTag(@Valid @RequestBody Tag tag) {
	return taskService.addTagInTable(tag);
    }


    @PostMapping(path = "/addTag?{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Task addNewTagFoTask(@Valid @RequestBody Task task, @PathVariable long id) {
	Tag byIdTag = taskService.getByIdTag(id);
	return taskService.addTagToTask(task, byIdTag);
    }


    @DeleteMapping("/deleteTag/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
	taskService.deleteTag(id);
    }

}


