package sekigae_right.service;

import java.util.Collections;
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

  /**
   * 生徒の座席をシャッフルします
   *
   * @param rows    座席の行数
   * @param columns 座席の列数
   * @return シャッフル後の座席配置
   */
  public Student[][] shuffleSeatingChart(int rows, int columns) {
    // 🔥 修正点1：アクティブな生徒のみ取得
    List<Student> allStudents = repository.findAllByDeletedFalse();
    Collections.shuffle(allStudents); // ← ランダムに並び替え

    Student[][] chart = new Student[rows][columns];
    int index = 0;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        if (index < allStudents.size()) {
          Student student = allStudents.get(index++);

          // 🔥 修正点2：新しい座席位置を設定
          student.setSeatRow(r + 1);     // 1-based indexing
          student.setSeatColumn(c + 1);  // 1-based indexing

          // 🔥 修正点3：データベースに保存
          repository.save(student);

          chart[r][c] = student;
        }
      }
    }

    return chart;
  }
}
