package com.vz89.hometask.dto;

import com.vz89.hometask.model.Role;
import com.vz89.hometask.model.Status;
import com.vz89.hometask.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private LocalDate created;
    private LocalDate updated;
    private LocalDate lastPasswordChangeDate;
    private Status status;
    private String phoneNumber;
    private Set<Role> roles;

    public UserDTO(Long id, String username, LocalDate created, LocalDate updated, LocalDate lastPasswordChangeDate, Status status, String phoneNumber, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.created = created;
        this.updated = updated;
        this.lastPasswordChangeDate = lastPasswordChangeDate;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getCreated(),
                user.getUpdated(),
                user.getLastPasswordChangeDate(),
                user.getStatus(),
                user.getPhoneNumber(),
                user.getRoles()
        );
    }
}
