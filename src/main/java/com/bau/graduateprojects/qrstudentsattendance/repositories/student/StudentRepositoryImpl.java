package com.bau.graduateprojects.qrstudentsattendance.repositories.student;

import com.bau.graduateprojects.qrstudentsattendance.entities.StudentEntity;
import com.bau.graduateprojects.qrstudentsattendance.exception.DuplicatedUsernameException;
import com.bau.graduateprojects.qrstudentsattendance.exception.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final SpringJpaStudentRepository jpaStudentRepository;

    public StudentRepositoryImpl(SpringJpaStudentRepository jpaStudentRepository) {
        this.jpaStudentRepository = jpaStudentRepository;
    }

    @Override
    public List<StudentEntity> list() {
        return jpaStudentRepository.findAll();
    }

    @Override
    public StudentEntity getByUsername(String username) {
        return jpaStudentRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("student not found with username = " + username));
    }

    @Override
    public StudentEntity insert(StudentEntity studentEntity) {
        isExistUsername(studentEntity.getUsername());
        studentEntity.setPassword(new BCryptPasswordEncoder()
                .encode(studentEntity.getPassword()));

        return jpaStudentRepository.save(studentEntity);
    }

    @Override
    public StudentEntity getById(Long id) {
        return jpaStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("student not found with id = " + id));
    }

    @Override
    public StudentEntity update(StudentEntity studentEntity) {
        return jpaStudentRepository.save(studentEntity);
    }

    @Override
    public void removeById(Long id) {
        jpaStudentRepository.deleteById(id);
    }

    @Override
    public Long getCount() {
        return jpaStudentRepository.count();
    }

    @Override
    public boolean existById(Long studentId) {
        return jpaStudentRepository.existsById(studentId);
    }

    private void isExistUsername(String username) {
        if (jpaStudentRepository.existsStudentEntityByUsername(username)) {
            throw new DuplicatedUsernameException(username + " username is already taken");
        }
    }
}
