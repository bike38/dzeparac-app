package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.bike.dzeparac.model.Allowance;

public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
}