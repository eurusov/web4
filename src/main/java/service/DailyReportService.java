package service;

import DAO.DailyReportDao;
import model.DailyReport;
import model.SimpleReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<SimpleReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport()
                .stream()
                .map(SimpleReport::new)
                .collect(Collectors.toList());
    }

    public SimpleReport getLastReport() {
        DailyReport dailyReport = new DailyReportDao(sessionFactory.openSession()).getLastDailyReport();
        return new SimpleReport(dailyReport);
    }
}
