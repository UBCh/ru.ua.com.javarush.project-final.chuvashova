package com.javarush.jira.bugtracking.task;

import com.javarush.jira.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "task_tag")
@Getter
@Setter
@ToString
@NoArgsConstructor

@Builder
@Entity
public class Tag extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "task_id")
    Task taskId;


    @Column(name = "tag")
    String tag;


    public Tag(Task taskId, String tag) {
	this.taskId = taskId;
	this.tag = tag;
    }


    public Tag(Long id, String tag) {
	super(id);
	this.tag = tag;
    }
}
