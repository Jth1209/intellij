package edu.du.sb1014_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.du.sb1014_2.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select count(*) from User u where u.id = :id and u.password = :password")
    int userCount(@Param("id")String id , @Param("password")String password);

    @Query("select new User(u.uid , u.id , u.password,u.name , u.email) from User u where u.id = :id and u.password = :password")
    User selectUser(@Param("id")String id , @Param("password")String password);

    @Modifying
    @Transactional
    @Query("update User u set u.password = :p , u.name = :n , u.email = :e where u.uid = :uid")
    void updateUser(@Param("p")String password, @Param("n")String name , @Param("e")String email, @Param("uid")Long uid);

    @Modifying
    @Transactional
    @Query("delete from User u where u.uid = :uid")
    void deleteUser(@Param("uid")Long uid);
}
