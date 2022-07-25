package actions.views;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class FavoriteView {

    /**
     * id
     */
    private Integer id;

    /**
     * いいねした従業員
     */
    private EmployeeView employee_id;

    /**
     * いいねした日報
     */
    private ReportView report_id;

}
