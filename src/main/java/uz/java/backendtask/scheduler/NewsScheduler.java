package uz.java.backendtask.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.java.backendtask.repository.NewsRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NewsScheduler {

    private final NewsRepository newsRepository;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void handlePublishing() {

        LocalDateTime now = LocalDateTime.now();

        int published = newsRepository.publishScheduled(now);
        int unpublished = newsRepository.unpublishExpired(now);
    }
}
