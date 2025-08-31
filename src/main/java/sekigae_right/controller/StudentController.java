package sekigae_right.controller;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sekigae_right.data.Student;
import sekigae_right.service.StudentService;

@Valid
@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

  private final StudentService service;

  /**
   * 生徒一覧を表示して表示
   */
  @GetMapping
  public String allStudent(Model model) {
    List<Student> students = service.getAllActiveUsers();
    model.addAttribute("students", students);
    return "students/list";
  }

  /**
   * 新規生徒登録フォームの表示
   */
  @GetMapping("/new")
  public String show(Model model) {
    // List<Student> students = service.allStudent();
    model.addAttribute("student", new Student());
    return "students/create";
  }

  /**
   * 生徒の新規登録(POST)
   */
  @PostMapping
  public String register(@Valid @ModelAttribute Student student,
      BindingResult bindingResult, Model model) {
    //この student はcreate.htmlのth:object="${student}"と連動

    // バリデーションエラーがある場合は同じフォーム画面に戻る
    if (bindingResult.hasErrors()) {
      return "students/create";
      //これにより、名前が空欄だったり、座席の値が範囲外の場合に同じフォーム画面に戻り、
      //エラーメッセージが表示されます。

    }

    try {
      service.register(student);
    } catch (IllegalArgumentException e) {
      model.addAttribute("seatError", e.getMessage());
      return "students/create"; //studentsディレクトリ内のcreate.htmlを表示する
    }
    return "redirect:/students"; //redirect:/　を実行するとはURLが切り替わる
    //　　　　　　　　　↑ @GetMapping の allStudent()メソッドの呼び出し
  }

  @PostMapping("/delete/{id}")
  public String deleteStudent(@PathVariable Integer id) {
    service.deleteStudent(id);
    return "redirect:/students";
    // 生徒一覧にリダイレクト（@GetMappingのallStudentメソッドが動き、list.htmlを表示させる
  }

  // 🆕 座席表表示用メソッド
  @GetMapping("/seating")
  public String showSeatingChart(Model model) {
    List<Student> students = service.getAllActiveUsers();

    // 座席マップを作成（行,列 → 生徒の対応）
    Map<String, Student> seatMap = new HashMap<>();
    for (Student student : students) {
      String key = student.getSeatRow() + "," + student.getSeatColumn();
      seatMap.put(key, student);
    }

    model.addAttribute("seatMap", seatMap);
    return "students/seating";
  }

  @PostMapping("/shuffle")
  public String shuffleStudent(Model model) {
    try {
      Map<String, Student> seatMap = new HashMap<>();
      Student[][] students = service.shuffleSeatingChart(7, 7);

      for (Student[] stu1 : students) {
        for (Student stu2 : stu1) {
          if (stu2 != null) {
            String key = stu2.getSeatRow() + "," + stu2.getSeatColumn();
            seatMap.put(key, stu2);
          }
        }
      }

      model.addAttribute("seatMap", seatMap);
      return "students/seating";

    } catch (Exception e) {
      // エラーが発生した場合は元の座席表を表示
      return showSeatingChart(model);
    }
  }
}
