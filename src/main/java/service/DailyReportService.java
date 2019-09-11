package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.DailyReport;
import model.SimpleReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBException;
import util.DBHelper;

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

    public SimpleReport getDailyReport(long id) throws DBException {
        DailyReport dailyReport = new DailyReportDao(sessionFactory.openSession()).getDailyReport(id);
        return new SimpleReport(dailyReport);
    }

    public SimpleReport getLastReport() {
        DailyReport dailyReport = new DailyReportDao(sessionFactory.openSession()).getLastDailyReport();
        return new SimpleReport(dailyReport);
    }

    public Long addDailyReport(Long earnings, Long soldCars) {
        return new DailyReportDao(sessionFactory.openSession())
                .addDailyReport(earnings, soldCars);
    }

    public void createDailyReport() {
        Session sess = sessionFactory.openSession();
        CarDao carDao = new CarDao(sess);
        Transaction tx = sess.beginTransaction();

        Long soldAmount = carDao.soldAmount();
//        Long soldCount = carDao.soldCount();
        long soldCount = new CarDao(sess).removeSoldCars();

        tx.commit();
        sess.close();

        soldAmount = soldAmount == null ? 0 : soldAmount;
        addDailyReport(soldAmount, soldCount);
    }
}
