package com.example.learning.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepozytory studentRepozytory;

    @Autowired
    public StudentService(StudentRepozytory studentRepozytory) {
        this.studentRepozytory = studentRepozytory;
    }

    public List<Student> getStudents(){
       return studentRepozytory.findAll();
    }

    public Student getStudent(Long studentId){
        Optional<Student> studentOptional = studentRepozytory.findById(studentId);
        if(studentOptional.isPresent()){
            return studentOptional.get();
        }else{
            throw new IllegalStateException("student not present");
        }
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepozytory.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        studentRepozytory.save(student);
    }

    public void deleteStudent(Long studentId){
        boolean exists = studentRepozytory.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepozytory.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email, LocalDate dob){
        Student student = studentRepozytory.findById(studentId).orElseThrow(() -> new IllegalStateException("student with the id " + studentId + " does not exists"));
        //System.out.println("----------------------------------------" + studentId + " " + name + " " + email + " " + dob);
        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if(dob != null && dob.toString().length() > 0 && !Objects.equals(student.getDob(), dob)){
            student.setDob(dob);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional = studentRepozytory.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }

    }
}
