package com.zagbor.click.model;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
public class Link {

    @Id
    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "number_of_clicks")
    private int numberOfClicks;

    @Column(name = "limit_of_clicks")
    private int limitOfClicks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}