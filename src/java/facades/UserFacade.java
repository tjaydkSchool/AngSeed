package facades;

import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import security.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserFacade {

    private final Map<String, User> users = new HashMap<>();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AngSeedServerPU");
    private EntityManager em = emf.createEntityManager();
    private PasswordHash ph = new PasswordHash();

    public static void main(String[] args) {
        try {
            UserFacade f = new UserFacade();
            List<String> list = f.authenticateUser("User", "test");
            
            User user = f.getUserByUserId("User");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
            System.out.println(user.getPassword());
            System.out.println(PasswordHash.validatePassword("test", user.getPassword()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public UserFacade() {
//        
        
        if(em.find(User.class, "user") == null) {
            try {
//            Test Users
            String hashedPassUser = ph.createHash("test");
            String hashedPassAdmin = ph.createHash("test");
            String hashedPassBoth = ph.createHash("test");
            
            User user = new User("user", hashedPassUser);
            user.AddRole("User");
            
            User admin = new User("admin", hashedPassAdmin);
            admin.AddRole("Admin");
            
            User both = new User("user_admin", hashedPassBoth);
            both.AddRole("User");
            both.AddRole("Admin");
            
            em.getTransaction().begin();
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
            
//    users.put("admin",admin);
//    users.put("user",user );
//    users.put("user_admin",both );
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
    }

    public User getUserByUserId(String id) {
        
        return em.find(User.class, id);
    }
    /*
     Return the Roles if users could be authenticated, otherwise null
     */

    public List<String> authenticateUser(String userName, String password) {
        User user = em.find(User.class, userName);

        try {
            return user != null && (ph.validatePassword(password, user.getPassword())) ? user.getRoles() : null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
