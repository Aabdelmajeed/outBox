package com.outbox.MsArchitecture.entity;

import com.outbox.MsArchitecture.model.Aggregate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggregate")
    private Aggregate aggregate;


    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "is_delivered", nullable = false)
    private Boolean isDelivered = false;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    public Outbox(Aggregate aggregate, String message, Boolean isDelivered) {
        this.aggregate = aggregate;
        this.message = message;
        this.isDelivered = isDelivered;
    }
}

