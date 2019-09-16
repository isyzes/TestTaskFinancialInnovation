package com.test.task.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Getter
@Setter
@Table(name ="task_logs")
public class TaskLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "spent_time")
    private long spentTime;//потраченное время (целое число часов)

    @Column(name = "comment", length = 1000)
    private String comment;

    public TaskLogs(Task task, String comment) {
        this.task = task;
        this.comment = comment;
    }

    public TaskLogs() {
    }
}