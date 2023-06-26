package com.project.jose.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

//
//    @Query("select firstName , lastName , age from teachers where age<= :age")
//    public List<Student> findByAgeLessThanEqual(@Param("age")long age);

    //@Query("SELECT * from students where firstName ILIKE %:name%")

    @Query("SELECT s FROM students s WHERE s.username = :username")
    Optional<Student> findByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM students u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM students u WHERE u.email = :email")
    boolean existsByEmail(@Param("email")String email);

    public List<Student> findByFirstName(@Param("name") String name);
}
