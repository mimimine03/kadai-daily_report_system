package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Favorite;


public class FavoriteConverter {


    public static Favorite toModel(FavoriteView fv) {
        return new Favorite(
                fv.getId(),
                EmployeeConverter.toModel(fv.getEmployee_id()),
                ReportConverter.toModel(fv.getReport_id()));
    }

    public static FavoriteView toView(Favorite f) {

        if (f == null) {
            return null;
        }

        return new FavoriteView(
                f.getId(),
                EmployeeConverter.toView(f.getEmployee_id()),
                ReportConverter.toView(f.getReport_id()));
    }
    public static List<FavoriteView> toViewList(List<Favorite> list) {
        List<FavoriteView> evs = new ArrayList<>();

        for (Favorite f : list) {
            evs.add(toView(f));
        }

        return evs;
    }




    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param f DTOモデル(コピー先)
     * @param fv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Favorite f, FavoriteView fv) {
        f.setId(fv.getId());
        f.setEmployee_id(EmployeeConverter.toModel(fv.getEmployee_id()));
        f.setReport_id(ReportConverter.toModel(fv.getReport_id()));
    }



}
