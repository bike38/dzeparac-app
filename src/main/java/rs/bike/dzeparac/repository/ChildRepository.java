package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.Child;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findByActiveTrue();

    List<Child> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}
