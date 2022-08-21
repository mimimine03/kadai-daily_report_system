package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FavoriteCountView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.FavoriteService;
import services.ReportService;

/**
 * トップページに関する処理を行うActionメソッド
 *
 */
public class TopAction extends ActionBase {

    private ReportService service;
    private FavoriteService favservice;

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();
        favservice = new FavoriteService();

        //メソッドを実行
        invoke();

        service.close();
        favservice.close();
    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中の従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<ReportView> reports = service.getMinePerPage(loginEmployee, page);

        //一覧内に表示するいいねの数
        List<FavoriteCountView> favoritesCounts = favservice.countListUp(255);

    for(ReportView rv : reports) {

        Long favCount= 0L;

        for(FavoriteCountView fcv : favoritesCounts) {
            if(rv.getId() == fcv.getReport().getId()) {
                favCount = fcv.getCount();
                break;
            }
        }
        rv.setFavCount(favCount);
        }



        //ログイン中の従業員が作成した日報データの件数を取得
        long myReportsCount = service.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT, myReportsCount); //ログイン中の従業員が作成した日報の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }
        //一覧画面を表示する
        forward(ForwardConst.FW_TOP_INDEX);
    }

}
