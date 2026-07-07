package fr.unice.scale.latencyaware.e2e_analyzer.repository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                String dbUrl = System.getenv("DB_URL");
                String dbUser = System.getenv("DB_USER");
                String dbPassword = System.getenv("DB_PASSWORD");

                Map<String, Object> properties = new HashMap<>();
                properties.put("spring.datasource.url", dbUrl);
                properties.put("spring.datasource.user", dbUser);
                properties.put("spring.datasource.password", dbPassword);

                EntityManagerFactory emf = Persistence.createEntityManagerFactory("event-analysis-pu", properties);
                sessionFactory = emf.unwrap(SessionFactory.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}