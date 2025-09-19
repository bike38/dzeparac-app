package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.Child;

public interface ChildRepository extends JpaRepository<Child, Long> {
}