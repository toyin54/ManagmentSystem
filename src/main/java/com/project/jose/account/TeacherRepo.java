package com.project.jose.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM teachers t WHERE t.username = :username")
    Optional<Teacher> findByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM teachers u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM teachers u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

//    @Query("select firstName , lastName , age from teachers where age<= :age")
//    public List<Teacher> findByAgeLessThanEqual(@Param("age") long age);

    //@Query("SELECT t from teachers where t.firstName ILIKE %:name%")
    public List<Teacher> findByFirstName(@Param("name")String name);



}
