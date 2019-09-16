package com.test.task.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "task_logs")
public class TaskLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "spent_time")
    private double spentTime;

    @Column(name = "comment", length = 1000)
    private String comment;

    public TaskLogs(Task task, String comment) {
        this.task = task;
        this.comment = comment;
    }

    public TaskLogs() {
    }

    public double getSpentTime() {
        return (double) Math.round(spentTime * 100) / 100;
    }
}