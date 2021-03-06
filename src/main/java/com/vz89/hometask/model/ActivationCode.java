package com.vz89.hometask.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "activation_code")
public class ActivationCode {

    public ActivationCode(Integer code, LocalDateTime expirationDate) {
        this.code = code;
        this.expirationDate = expirationDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "code")
    private Integer code;

    @Column(name = "expiration_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime expirationDate;
}
