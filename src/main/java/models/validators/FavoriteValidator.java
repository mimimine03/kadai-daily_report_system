package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.FavoriteView;
import constants.MessageConst;
import services.FavoriteService;

public class FavoriteValidator {

    /**
     *
     * @param service FavoriteServiceのインスタンス
     * @return 従業員テーブルに登録されている同一社員番号のデータの件数
     */
    public static List<String> validate(
            FavoriteService service, FavoriteView fv, Boolean favDuplicateCheckFlag){
        List<String> errors = new ArrayList<String>();

        //社員番号と日報IDの重複チェック
        String favError = validateFav(service, fv,favDuplicateCheckFlag);
        if (!favError.equals("")) {
            errors.add(favError);
        }
        return errors;
    }


    private static String validateFav(FavoriteService service,FavoriteView fv, Boolean favDuplicateCheckFlag) {

        //社員IDと日報IDの重複チェックを実施
        if (favDuplicateCheckFlag) {

            long favoritesCount = isDuplicateFavorite(service,fv);

          //同一データが既に登録されている場合はエラーメッセージを返却
            if (favoritesCount > 0) {
                return MessageConst.E_FAV_DUPLICATE.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";

     }

    private static long isDuplicateFavorite(FavoriteService service, FavoriteView fv) {

        long favorites_over = service.countOver(fv);
        return favorites_over;

    }

}
