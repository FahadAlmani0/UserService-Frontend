package rosol.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Class that defines the User model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUser {

    Long id;
    String name;
    String lastname;
    private LocalDate dateOfBirth;
    private char gender;
    private String nationality;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String entityId;
    private int nationalId;
    private String role;
    private int permission;
    private String missionId;
    boolean active;

    public AppUser() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return gender == appUser.gender && nationalId == appUser.nationalId && role == appUser.role && permission == appUser.permission && active == appUser.active && Objects.equals(id, appUser.id) && Objects.equals(name, appUser.name) && Objects.equals(lastname, appUser.lastname) && Objects.equals(dateOfBirth, appUser.dateOfBirth) && Objects.equals(nationality, appUser.nationality) && Objects.equals(phone, appUser.phone) && Objects.equals(email, appUser.email) && Objects.equals(username, appUser.username) && Objects.equals(password, appUser.password) && Objects.equals(entityId, appUser.entityId) && Objects.equals(missionId, appUser.missionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, dateOfBirth, gender, nationality, phone, email, username, password, entityId, nationalId, role, permission, missionId, active);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", nationality='" + nationality + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", entityId='" + entityId + '\'' +
                ", nationalId=" + nationalId +
                ", role=" + role +
                ", permission=" + permission +
                ", missionId='" + missionId + '\'' +
                ", active=" + active +
                '}';
    }
}
