package sekigae_right.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "名前を入力せよ")
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "性別を選択してください")
  @Column(nullable = false)
  private String gender;


  //@Columnアノテーションをつけない場合はフィールド名(seatRow)がそのままカラム名になる
  @NotNull(message = "座席の行を入力してください")
  @Min(value = 3, message = "座席（行）は3以上にしてください")
  @Max(value = 7, message = "座席（行）は7以下にしてください")
  private Integer seatRow;

  @NotNull(message = "座席の列を入力してください")
  @Min(value = 3, message = "座席（列）は3以上にしてください")
  @Max(value = 7, message = "座席（列）は7以下にしてください")
  private Integer seatColumn;

  @Builder.Default//クラス宣言の上に@Builderをつけたが、
  // さらに自分でデフォルト値を設定したい場合に使用するアノテーション
  @Column(name = "deleted", nullable = false)
  private boolean deleted = false; // 論理削除フラグ（デフォルトはfalse）

}
