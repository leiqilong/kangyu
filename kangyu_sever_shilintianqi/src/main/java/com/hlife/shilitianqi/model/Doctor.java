package com.hlife.shilitianqi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@Document(value = "doctor")
public class Doctor implements Serializable {

	/**
	 * id 唯一标识
	 */
	@Field("id")
	private String id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 职称
	 */
	private String zhicheng;
	/**
	 * 擅长领域
	 */
	private String scly;
	/**
	 * 学术成就
	 */
	private String scholarship;
	/**
	 * 头像
	 */
	private String headpto;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 级别
	 */
	private String level;
	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 科室名称
	 */
	private String nodeName;

	private String file;
}
