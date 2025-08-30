package sekigae_right.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sekigae_right.data.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

  // 論理削除されていないデータを取得
  List<Student> findAllByDeletedFalse();

  // 指定idの生徒が論理削除されていないインスタンスを取得
  Optional<Student> findByIdAndDeletedFalse(Integer id);
}
