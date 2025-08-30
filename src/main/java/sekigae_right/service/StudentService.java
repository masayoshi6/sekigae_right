package sekigae_right.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sekigae_right.data.Student;
import sekigae_right.repository.StudentRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

  private final StudentRepository repository;

  /**
   * 生徒の全件検索
   */
  public List<Student> allStudent() {
    return repository.findAll();
  }

  /**
   * 生徒の新規登録
   */
  public Student register(Student student) {
    return repository.save(student);
  }

}
