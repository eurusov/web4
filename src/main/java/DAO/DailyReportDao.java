package DAO;

import model.DailyReport;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import model.VirtualDate;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public DailyReport getDailyReport(long id) {
        return (DailyReport) session.get(DailyReport.class, id);
    }

    public DailyReport getDailyReportForDate(VirtualDate virtualDate) throws NonUniqueResultException {
        Query query = session.createQuery("FROM DailyReport WHERE date=:q_date");
        query.setParameter("q_date", virtualDate);
        return (DailyReport) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<DailyReport> getAllDailyReport() {
        return session.createQuery("FROM DailyReport").list();
    }

    public Long saveDailyReport(DailyReport dailyReport) {
        return (Long) session.save(dailyReport);
    }
}
