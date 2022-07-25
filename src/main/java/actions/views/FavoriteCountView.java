package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Report;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavoriteCountView {

    /**
     * 日報
     */
    private ReportView report;
    /**
     * いいね！数
     */
    private Long count;

    public FavoriteCountView(Report report, Long count) {
        this.report = ReportConverter.toView(report);
        this.count = count;
    }
}