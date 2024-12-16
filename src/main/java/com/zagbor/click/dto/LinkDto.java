package com.zagbor.click.dto;

import java.time.LocalDateTime;

public record LinkDto(String name, String shortUrl, String originalUrl, LocalDateTime expirationDate,
                      LocalDateTime creationDate, int numberOfClicks, int limitOfClicks) {
}
