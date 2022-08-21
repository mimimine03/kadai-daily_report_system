package services;

import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
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
     * ログイン中の社員の社員番号と日報のIDを取得し、返却する
     *
     */
    public Boolean isAlreadyFavorite(EmployeeView employee,ReportView report){

        List<Long> fav_search =em.createNamedQuery(JpaConst.Q_FAV_SEARCH_ALREADY_FAVORITE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT,ReportConverter.toModel(report))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE,EmployeeConverter.toModel(employee))
                .getResultList();

        boolean isAlreadyFavorite = false ;

        if (fav_search.size() == 0) {
            isAlreadyFavorite = false;
        } else if (fav_search.size() >= 1) {
            for(Long count : fav_search) {
              if(count > 0) {
                isAlreadyFavorite = true;
                break;
              }
            }
        }
        return isAlreadyFavorite;

}
    public FavoriteView seachFavorite(EmployeeView employee,ReportView report){
        Favorite favorite = null;
        try{
          favorite =em.createNamedQuery(JpaConst.Q_FAV_SEARCH_FAVORITE,Favorite.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT,ReportConverter.toModel(report))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE,EmployeeConverter.toModel(employee))
                .getSingleResult();
        } catch (NoResultException e) {
          favorite = new Favorite(); //NullPointerException回避
        }

        return FavoriteConverter.toView(favorite);
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


        /**
         * いいねデータを1件削除する
         * @param fv 日報データ
         */
        public void destroy(FavoriteView fv) {

            Favorite f = findOneInternal(fv.getId());
            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();


        }



    }
