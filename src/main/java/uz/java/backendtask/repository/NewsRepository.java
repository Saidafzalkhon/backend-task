package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.News;

@Repository
public interface NewsRepository
        extends JpaRepository<News, Long>,
        JpaSpecificationExecutor<News> {
}
