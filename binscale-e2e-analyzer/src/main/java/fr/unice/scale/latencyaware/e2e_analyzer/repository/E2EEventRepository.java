package fr.unice.scale.latencyaware.e2e_analyzer.repository;

import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEvent;
import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEventTracker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class E2EEventRepository {
    private final SessionFactory sessionFactory;

    public E2EEventRepository() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    // E2EEventTracker have to be persist before.
    public void save(E2EEvent event) {
        if (event == null || event.getTracker() == null) {
            throw new IllegalArgumentException("Event and Tracker must not be null");
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                E2EEventTracker tracker = session.merge(event.getTracker());
                event.setTracker(tracker);

                session.persist(event);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    public void saveAll(List<E2EEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            for (int i = 0; i < events.size(); i++) {
                E2EEvent event = events.get(i);
                if (event == null || event.getTracker() == null) {
                    throw new IllegalArgumentException("Event and Tracker must not be null");
                }

                E2EEventTracker tracker = session.merge(event.getTracker());
                event.setTracker(tracker);

                session.persist(event);

                if (i % 20 == 19) {
                    session.flush();
                    session.clear();
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Échec de la sauvegarde des événements", e);
        }
    }

    public void close() {
        HibernateUtil.shutdown();
    }

    public void cleanTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM E2EEvent").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Échec du nettoyage de la table E2EEvent", e);
        }
    }
}