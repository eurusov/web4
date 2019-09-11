package DAO;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.DBDate;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public DailyReport getDailyReport(long id) {
        Transaction tx = session.beginTransaction();

        DailyReport dailyReport = (DailyReport) session.get(DailyReport.class, id);

        tx.commit();
        session.close();
        return dailyReport;
    }

    public DailyReport getLastDailyReport() {
        Transaction tx = session.beginTransaction();

//        List dailyReports = session.createQuery(
//                "FROM DailyReport r WHERE r.date in (select max(r.date) FROM r)"
//        ).list();
        Query yesterdayQuery = session.createQuery("FROM DailyReport WHERE date=:q_date");
        yesterdayQuery.setParameter("q_date", DBDate.yesterday());
        List dailyReports = yesterdayQuery.list();

        tx.commit();
        session.close();

        if (dailyReports.size() < 1) {
            return new DailyReport(0L, 0L);
        }
        DailyReport rep = (DailyReport) dailyReports.get(0);
        if (rep.getEarnings() == null) {
            rep.setEarnings(0L);
        }
        if (rep.getSoldCars() == null) {
            rep.setSoldCars(0L);
        }

        return (DailyReport) dailyReports.get(0);
    }

    // TODO:  Переделать чтобы принимал DailyReport вместо набора значений полей.
    public Long addDailyReport(Long earnings, Long soldCars) {
        Transaction tx = session.beginTransaction();

        Long id = (Long) session.save(new DailyReport(earnings, soldCars));

        tx.commit();
        session.close();
        return id;
    }

    @SuppressWarnings("unchecked")
    public List<DailyReport> getAllDailyReport() {
        Transaction tx = session.beginTransaction();

        List dailyReports = session.createQuery("FROM DailyReport").list();

        tx.commit();
        session.close();
        return dailyReports;
    }
}
