package entities;

import orm.Column;
import orm.Entity;
import orm.Id;

import java.time.LocalDate;

@Entity(name = "users")
public class User {
    @Id
    private Long id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "age")

    private int age;
    @Column(name = "registration_date")
    private LocalDate registration;

    public User(String username, int age, LocalDate registration) {
        this.username = username;
        this.age = age;
        this.registration = registration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }
}
