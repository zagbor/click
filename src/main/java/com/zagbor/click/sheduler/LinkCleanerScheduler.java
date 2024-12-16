package com.zagbor.click.sheduler;

import com.zagbor.click.service.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class LinkCleanerScheduler {

    private final LinkService linkService;


    // Задача для очистки устаревших ссылок, которая будет выполняться раз в день
    @Scheduled(cron = "0 0 * * * ?") // Запускать каждый день в полночь
    @Transactional
    public void cleanExpiredLinks() {
        System.out.println("Starting expired link cleanup process...");

        // Очистка устаревших ссылок из базы данных
        linkService.deleteExpiredLinks();

        System.out.println("Expired link cleanup completed.");
    }
}
