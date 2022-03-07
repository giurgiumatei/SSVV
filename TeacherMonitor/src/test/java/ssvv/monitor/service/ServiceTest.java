package ssvv.monitor.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ssvv.monitor.domain.Student;
import ssvv.monitor.repository.NotaXMLRepo;
import ssvv.monitor.repository.StudentXMLRepo;
import ssvv.monitor.repository.TemaXMLRepo;
import ssvv.monitor.utils.FileClearer;
import ssvv.monitor.validation.NotaValidator;
import ssvv.monitor.validation.StudentValidator;
import ssvv.monitor.validation.TemaValidator;
import ssvv.monitor.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceTest {

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
    void addStudent_validStudentGroup_isAdded() {
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addStudent_invalidStudentGroup_notAdded() {
        Student student = new Student("1", "nume", -10, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }
}