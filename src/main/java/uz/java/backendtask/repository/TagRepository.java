package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository
        extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    boolean existsByCode(String name);

    boolean existsByCodeAndIdNot(String name, Long id);

    Set<Tag> findAllByIdIn(Set<Long> ids);
}
