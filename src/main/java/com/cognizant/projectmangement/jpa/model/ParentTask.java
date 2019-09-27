package com.cognizant.projectmangement.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the parent_task database table.
 * @author CTS
 * 
 */
@Entity
@Table(name="parent_task")
@NamedQuery(name="ParentTask.findAll", query="SELECT p FROM ParentTask p")
@Getter
@Setter
@NoArgsConstructor
public class ParentTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="parent_id", unique=true, nullable=false)
	private int parentId;

	@Column(name="parent_task", nullable=false, length=200)
	private String parentTask;

	//uni-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
}