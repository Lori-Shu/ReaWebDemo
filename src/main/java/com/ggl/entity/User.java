package com.ggl.entity;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ggl_t_user",schema="public")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name="id")
  private String id;
  @Column(name = "name")
  private String name;
  @Column(name="age")
  private String age;
  @Column(name="sex")
  private Boolean sex;
}
