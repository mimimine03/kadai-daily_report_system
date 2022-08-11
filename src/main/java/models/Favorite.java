package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 日報データのDTOモデル
 *
 */

@Table(name = JpaConst.TABLE_FAV)
@NamedQueries({
@NamedQuery(
        name = JpaConst.Q_FAV_COUNT,
        query = JpaConst.Q_FAV_COUNT_DEF),
@NamedQuery(
        name = JpaConst.Q_FAV_REPORT_ALL_MINE,
        query = JpaConst.Q_FAV_REPORT_ALL_MINE_DEF),
@NamedQuery(
        name = JpaConst.Q_FAV_COUNT_REGISTERED_BY_EMP_AND_REP,
        query = JpaConst.Q_FAV_COUNT_REGISTERED_BY_EMP_AND_REP_DEF)
})


@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity

public class Favorite {

    @Id
    @Column(name = JpaConst.FAV_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //いいねした従業員
    @ManyToOne
    @JoinColumn(name = JpaConst.FAV_COL_EMP, nullable = false)
    private Employee employee_id;
    //いいねされた日報
    @ManyToOne
    @JoinColumn(name = JpaConst.FAV_COL_REP, nullable = false)
    private Report report_id;
}