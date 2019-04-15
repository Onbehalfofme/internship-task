package ru.innopolis.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.demo.models.Good;

import java.util.List;
import java.util.Optional;

/**
 * JPA-repository for {@link Good} model
 */
@Repository
public interface GoodRepository extends JpaRepository<Good, Long> {
     List<Good> findAllByCategory(String category);
     Optional<Good> findDistinctByName(String name);
     void deleteAllByCategory(String category);
     @Transactional
     void deleteByName(String name);

     boolean existsByName(String name);

}
