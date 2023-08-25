package com.javarush.jira.bugtracking.task;

import com.javarush.jira.common.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Transactional(readOnly = true)
public interface TagRepository extends BaseRepository<Tag> {

    void delete(Tag entity);

    Optional<Tag> findById(Long id);


    Tag save(Tag entity);


    List<Tag> findAll();

    Set<Tag> findAllByTaskId(Task taskId);


    long count();
}
