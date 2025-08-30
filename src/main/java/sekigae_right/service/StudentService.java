package sekigae_right.service;

import java.util.List;
import java.util.Optional;
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

  /**
   * 論理削除するAPI(メソッド)
   *
   * @param id 生徒ID
   */
  public void deleteStudent(Integer id) {
    Optional<Student> studentOptional = repository.findById(id);
    if (studentOptional.isPresent()) {
      Student student = studentOptional.get();
      student.setDeleted(true); // 論理削除フラグを立てる
      repository.save(student); // 更新を保存
    }
  }

  public List<Student> getAllActiveUsers() {
    return repository.findAllByDeletedFalse(); // 論理削除フラグがfalseのデータのみ取得
  }
  
}
