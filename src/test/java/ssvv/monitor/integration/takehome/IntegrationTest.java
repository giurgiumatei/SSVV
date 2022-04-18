package ssvv.monitor.integration.takehome;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ssvv.monitor.domain.Nota;
import ssvv.monitor.domain.Student;
import ssvv.monitor.domain.Tema;
import ssvv.monitor.repository.NotaXMLRepo;
import ssvv.monitor.repository.StudentXMLRepo;
import ssvv.monitor.repository.TemaXMLRepo;
import ssvv.monitor.service.Service;
import ssvv.monitor.utils.FileClearer;
import ssvv.monitor.validation.NotaValidator;
import ssvv.monitor.validation.StudentValidator;
import ssvv.monitor.validation.TemaValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IntegrationTest {

    private Service service;

    @BeforeEach
    void setUp() {
        String filenameStudent = "src/test/resources/Studenti.xml";
        String filenameTema = "src/test/resources/Teme.xml";
        String filenameNota = "src/test/resources/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);

        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);

        FileClearer.clearFiles();
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @AfterEach
    void tearDown() {
        FileClearer.clearFiles();
    }

    @Test
    public void addStudent_integration() {
        addStudent();
    }

    @Test
    public void addAssignment_integration() {
        addStudent();
        addAssignment();
    }

    @Test
    public void addGrade_integration() {
        addStudent();
        addAssignment();
        addGrade();
    }

    public void addStudent() {
        Student student = new Student("123", "name", 100, "some@email.com");
        assertNull(service.addStudent(student));
    }

    public void addAssignment() {
        assertNull(service.addTema(new Tema("123", "abc", 1, 1)));
    }
    // 2022,3,30 (in file)
    public void addGrade() {
        service.addNota(new Nota("1","123","123",10.0, LocalDate.of(2022, 4, 5)),"asd");
        assertEquals(10.0, service.addNota(new Nota("1","123","123",10.0, LocalDate.of(2022, 4, 5)),"asd"));
    }
}
