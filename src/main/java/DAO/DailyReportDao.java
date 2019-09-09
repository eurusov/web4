package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public DailyReport get(long id) {
        return (DailyReport) session.get(DailyReport.class, id);
    }

    public long insertDailyReport(Long earnings, Long soldCars) {
        return (Long) session.save(new DailyReport(earnings, soldCars));
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastDailyReport() {
        Transaction transaction = session.beginTransaction();
        List dailyReports = session.createQuery("FROM DailyReport r WHERE r.date in (select max(r.date) FROM r)").list();
        transaction.commit();
        session.close();
        return (DailyReport) dailyReports.get(0);
    }
}
