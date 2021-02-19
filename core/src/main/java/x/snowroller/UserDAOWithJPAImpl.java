package x.snowroller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDAOWithJPAImpl implements UserDAO {

  EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo");

  @Override
  public void create(User u) {
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    em.persist(u);
    em.getTransaction().commit();
  }

  @Override
  public List<User> getByName(String name) {
    List<User> list;
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    list = em.createQuery("from User u where u.UserName = :namn", User.class)
            .setParameter("namn", name).getResultList();
    em.getTransaction().commit();
    return list;
  }

  @Override
  public boolean updatePassword(String ID, String newPassword) {
    boolean success = false;
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    User u = em.find(User.class, ID);
    if (u != null ) {
      u.setPassword(newPassword);
      success = true;
    }
    em.getTransaction().commit();
    return success;
  }

  @Override
  public boolean remove(String ID) {
    boolean success = false;
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    User u = em.find(User.class, ID);
    if (u != null ) {
      em.remove(u);
      success = true;
    }
    em.getTransaction().commit();
    return success;
  }
}


