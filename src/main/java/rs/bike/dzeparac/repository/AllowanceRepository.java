package rs.bike.dzeparac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.bike.dzeparac.model.Allowance;
import rs.bike.dzeparac.model.Child;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllowanceRepository extends JpaRepository<Allowance, Long> {

    // Pronađi džeparac za dete u nedelji
    Optional<Allowance> findByChildAndWeekIndexAndSchoolYear(Child child, int weekIndex, int schoolYear);

    // Pronađi sve džeparce za dete u godini
    List<Allowance> findByChildAndSchoolYear(Child child, int schoolYear);

    // Ako koristiš childId direktno
    List<Allowance> findByChild_IdAndWeekIndexBetween(Long childId, int startWeek, int endWeek);
}
