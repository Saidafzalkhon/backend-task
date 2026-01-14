package uz.java.backendtask.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.News;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface NewsRepository
        extends JpaRepository<News, Long>,
        JpaSpecificationExecutor<News> {


    Optional<News> findFirstByIdAndIsDeletedFalse(Long id);

    @Query("""
    select n
    from NewsTranslation nt
    join nt.news n
    where nt.slug = :slug
      and nt.lang = :lang
      and n.isDeleted = false
      and n.status = 'PUBLISHED'
""")
    Optional<News> findNewsBySlugAndLang(
            @Param("slug") String slug,
            @Param("lang") String lang
    );

    @Modifying
    @Query("""
        update News n
        set n.status = 'PUBLISHED'
        where n.status <> 'PUBLISHED'
          and n.publishAt is not null
          and n.publishAt <= :now
          and (n.unpublishAt is null or n.unpublishAt > :now)
          and n.isDeleted = false
    """)
    int publishScheduled(@Param("now") LocalDateTime now);


    @Modifying
    @Query("""
        update News n
        set n.status = 'UNPUBLISHED'
        where n.status = 'PUBLISHED'
          and n.unpublishAt is not null
          and n.unpublishAt <= :now
          and n.isDeleted = false
    """)
    int unpublishExpired(@Param("now") LocalDateTime now);
}
