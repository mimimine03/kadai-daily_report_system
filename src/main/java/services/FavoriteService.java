package services;

import java.util.List;

import actions.views.FavoriteConverter;
import actions.views.FavoriteCountView;
import actions.views.FavoriteView;
import constants.JpaConst;
import models.Favorite;
import models.validators.FavoriteValidator;

public class FavoriteService extends ServiceBase{

    /**
     * 日報一覧に表示するいいね件数を取得し、返却する
     * @param i
     * @return
     * @return データの件数
     */

    public  List<FavoriteCountView> countListUp(int i) {


        List<FavoriteCountView> fav_count =em.createNamedQuery(JpaConst.Q_FAV_REPORT_ALL_MINE, FavoriteCountView.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, i)
                .getResultList();
        return fav_count;
    }


    /**
     * idを条件に取得したデータをFavoriteViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public FavoriteView findOne(int id) {
        return FavoriteConverter.toView(findOneInternal(id));
    }


    /**
     * いいねの登録内容を元にデータを1件作成し、いいねテーブルに登録する
     * @param fv いいねの登録内容
     */
    public List<String> create(FavoriteView fv) {

       //登録内容のバリデーションを行う
        List<String> errors = FavoriteValidator.validate(this, fv, true);

        if (errors.size() == 0) {
            createInternal(fv);
        }
      //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    public long countOver(FavoriteView fv) {

        long favorites_over = (long) em.createNamedQuery(JpaConst.Q_FAV_COUNT_REGISTERED_BY_EMP_AND_REP, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, fv.getReport_id())
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE,fv.getEmployee_id())
                .getSingleResult();
        return favorites_over;
    }



    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Favorite findOneInternal(int id) {
        return em.find(Favorite.class, id);
    }

        /**
         * いいねデータを1件登録する
         * @param fv 日報データ
         */
        private void createInternal(FavoriteView fv) {

            em.getTransaction().begin();
            em.persist(FavoriteConverter.toModel(fv));
            em.getTransaction().commit();
        }



    }
