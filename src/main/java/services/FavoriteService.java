package services;

import actions.views.FavoriteConverter;
import actions.views.FavoriteView;
import constants.JpaConst;

public class FavoriteService extends ServiceBase{

    /**
     * いいねテーブルのデータの件数を取得し、返却する
     * @return データの件数
     */

    public long countAllMine() {


        long favorites_Count = (long) em.createNamedQuery(JpaConst.Q_FAV_REPORT_ALL_MINE, Long.class)
                .getSingleResult();
        return favorites_Count;
    }

        /**
         * いいねデータを1件登録する
         * @param fv 日報データ
         */
        private void create(FavoriteView fv) {

            em.getTransaction().begin();
            em.persist(FavoriteConverter.toModel(fv));
            em.getTransaction().commit();

        }
    }

