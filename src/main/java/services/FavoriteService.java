package services;

import java.util.List;

import actions.views.FavoriteConverter;
import actions.views.FavoriteCountView;
import actions.views.FavoriteView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Favorite;

public class FavoriteService extends ServiceBase{

    /**
     * 日報一覧に表示するいいね件数を取得し、返却する
     * @param i
     * @return
     * @return データの件数
     */

    public  List<FavoriteCountView> countListUp(int i) {


        List<FavoriteCountView> fav_count =em.createNamedQuery(JpaConst.Q_FAV_REPORT_ALL_MINE, FavoriteCountView.class)
                .getResultList();
        return fav_count;
    }

    //日報の詳細画面に表示するいいねの数

    public long countByReport(ReportView report) {

        long fav_report =em.createNamedQuery(JpaConst.Q_FAV_COUNT_BY_REPORT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT,ReportConverter.toModel(report))
                .getSingleResult();

        return fav_report;
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
        public void create(FavoriteView fv) {

            em.getTransaction().begin();
            em.persist(FavoriteConverter.toModel(fv));
            em.getTransaction().commit();
        }



    }
