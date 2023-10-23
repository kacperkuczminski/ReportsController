package pl.reportsController.users;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.reportsController.roles.ERole;
import pl.reportsController.roles.RoleEntity;

import java.util.Optional;
import java.util.Set;


public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Query("SELECT u.id FROM UserEntity u WHERE u.login = ?1 AND u.password = ?2")
    Long findIdByUsernameAndPassword(String login, String password);

    @Query("SELECT login FROM UserEntity u where u.login=?1")
    String checkLoginExists(String login);

    @Query("select email from UserEntity u where u.email=?1")
    String checkEmailExists(String email);

    @Query("SELECT u.id FROM UserEntity u WHERE u.login = ?1")
    String findByLogin(String login);

    @Query("SELECT u.password FROM UserEntity u WHERE u.login = ?1")
    String findPasswordByLogin(String login);

    @Query("SELECT u.id FROM UserEntity u WHERE u.email = ?1")
    Long findIdByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.password = ?3, u.lastPasswordReset = ?4 WHERE u.id = ?1 AND u.email = ?2")
    int updateUserPassword(Long id, String email, String randomPassword, LocalDateTime resetDate);

    @Query("SELECT u.lastPasswordReset FROM UserEntity u where u.id = ?1")
    LocalDateTime getUserLastResetPasswordDate(Long idUser);

    @Query("SELECT r FROM UserEntity u JOIN u.roles r WHERE u.idUser = :userId")
    Set<RoleEntity> findRolesByUserId(@Param("userId") Long userId);

    UserEntity getById(Long idUser);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.customerEntity WHERE u.idUser = :id")
    Optional<UserEntity> findUserWithCustomerById(@Param("id") Long id);



}
