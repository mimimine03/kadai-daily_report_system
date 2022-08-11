package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FavoriteView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.FavoriteService;
import services.ReportService;

public class FavoriteAction extends ActionBase {

    private FavoriteService service;
    private ReportService repService;

    @Override
    public void process() throws ServletException, IOException {
        service = new FavoriteService();
        repService = new ReportService();

        //メソッドを実行
        invoke();
        repService.close();
        service.close();
    }


    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //いいねした日報を指定する方法
            ReportView rv = repService.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //パラメータの値をもとにいいね情報のインスタンスを作成する
            FavoriteView fv = new FavoriteView(
                    null,
                    ev, //ログインしている従業員を、日報作成者として登録する
                    rv);//いいねした日報のidを登録する
            //いいね情報登録
            List<String> errors = service.create(fv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.FAVORITE, fv); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //一覧に戻る
                forward(ForwardConst.FW_REP_INDEX);

            }else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_FAVORITE.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_REP,ForwardConst.CMD_INDEX);

            }

        }
    }

}
