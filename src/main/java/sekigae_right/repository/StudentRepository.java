package sekigae_right.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sekigae_right.data.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
