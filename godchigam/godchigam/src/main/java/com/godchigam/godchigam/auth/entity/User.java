package com.godchigam.godchigam.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String address;

    @Column(name="profile_image_url", nullable = false)
    @ColumnDefault("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973461_960_720.png")
    private String profileImageUrl;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;
}
