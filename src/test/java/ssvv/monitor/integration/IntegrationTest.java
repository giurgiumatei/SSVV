package ssvv.monitor.integration;

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
import ssvv.monitor.validation.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void addStudent_validStudent_isAdded() {
        Student student = new Student("123", "test", 100, "test@test.com");
        assertNull(service.addStudent(student));
    }

    @Test
    public void addAssignment_validAssignment_isAdded() {
        assertNull(service.addTema(new Tema("123", "test", 1, 1)));
    }

    @Test
    public void addGrade_invalidGrade_throwsValidationException() {
        assertThrows(ValidationException.class, () -> service.addNota(
                new Nota("1", "", "", 10.0, LocalDate.now()), "test")
        );
    }

    @Test
    public void testAll() {
        service.addStudent(new Student("123", "test", 100, "test@test.com"));
        service.addTema(new Tema("123", "test", 1, 1));
        System.out.println(service.addNota(
                new Nota("1", "123", "123", 10.0, LocalDate.now()), "test"));
    }
}
