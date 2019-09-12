package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.DailyReport;
import model.SimpleReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import model.VirtualDate;
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

    public SimpleReport getDailyReport(long id) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        DailyReport dailyReport = new DailyReportDao(sess).getDailyReport(id);

        tx.commit();
        sess.close();
        return new SimpleReport(dailyReport);
    }

    public List<SimpleReport> getAllDailyReports() {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        List<DailyReport> dailyReportList = new DailyReportDao(sess).getAllDailyReport();

        tx.commit();
        sess.close();

        return dailyReportList.stream()
                .map(SimpleReport::new)
                .collect(Collectors.toList());
    }

    public SimpleReport getLastReport() {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        DailyReport dailyReport = new DailyReportDao(sess).getDailyReportForDate(VirtualDate.getYesterdayDate());

        tx.commit();
        sess.close();

        if (dailyReport == null) {
            dailyReport = new DailyReport(0L, 0L);
        }
        return new SimpleReport(dailyReport);
    }

    public Long saveDailyReport(Long earnings, Long soldCars) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        Long id = new DailyReportDao(sess).saveDailyReport(new DailyReport(earnings, soldCars));

        tx.commit();
        sess.close();

        return id;
    }

    public void generateDailyReport() {
        Session sess = sessionFactory.openSession();
        CarDao carDao = new CarDao(sess);
        Transaction tx = sess.beginTransaction();

        Long soldAmount = carDao.soldAmount();
        long soldCount = new CarDao(sess).removeSoldCars();

        tx.commit();
        sess.close();

        soldAmount = soldAmount == null ? 0 : soldAmount;
        saveDailyReport(soldAmount, soldCount);
    }
}
