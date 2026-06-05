package fr.unice.scale.latencyaware.e2e_analyzer.repository;

import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEventTracker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class E2EEventTrackerRepository {
    private final SessionFactory sessionFactory;

    public E2EEventTrackerRepository() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void save(List<E2EEventTracker> trackers) {
        if (trackers == null || trackers.isEmpty()) {
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                for (int i = 0; i < trackers.size(); i++) {
                    session.persist(trackers.get(i));

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
                throw new RuntimeException("Échec de la sauvegarde des trackers: " + e.getMessage(), e);
            }
        }
    }

    public void save(E2EEventTracker tracker) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(tracker);
            transaction.commit();
        } catch (Exception e) {
            throw e;
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw e;
        }
    }

    public Optional<E2EEventTracker> findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM E2EEventTracker WHERE id = :id",
                            E2EEventTracker.class
                    )
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public Optional<E2EEventTracker> findById(String id, String entityGraphName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM E2EEventTracker WHERE id = :id",
                            E2EEventTracker.class
                    )
                    .setParameter("id", id)
                    .setHint("jakarta.persistence.fetchgraph", session.getEntityGraph(entityGraphName))
                    .uniqueResultOptional();
        }
    }

    public Optional<E2EEventTracker> findByIdWithEvents(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM E2EEventTracker t LEFT JOIN FETCH t.events WHERE t.id = :id",
                            E2EEventTracker.class
                    )
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public List<E2EEventTracker> findAll(String entityGraphName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM E2EEventTracker", E2EEventTracker.class)
                    .setHint("jakarta.persistence.fetchgraph", session.getEntityGraph(entityGraphName))
                    .getResultList();
        }
    }

    public boolean exist(String id) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(*) FROM E2EEventTracker WHERE id = :id",
                            Long.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }

    public void close() {
        HibernateUtil.shutdown();
    }

    public void cleanTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM E2EEventTracker").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Échec du nettoyage de la table E2EEventTracker", e);
        }
    }
}