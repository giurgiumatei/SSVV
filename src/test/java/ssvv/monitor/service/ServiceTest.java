package ssvv.monitor.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ssvv.monitor.domain.Student;
import ssvv.monitor.domain.Tema;
import ssvv.monitor.repository.NotaXMLRepo;
import ssvv.monitor.repository.StudentXMLRepo;
import ssvv.monitor.repository.TemaXMLRepo;
import ssvv.monitor.utils.FileClearer;
import ssvv.monitor.validation.NotaValidator;
import ssvv.monitor.validation.StudentValidator;
import ssvv.monitor.validation.TemaValidator;
import ssvv.monitor.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

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
    void addStudent_validStudentId_isAdded() {
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
    }

    //AddStudent
    @Test
    void addStudent_validStudentName_isAdded() {
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addStudent_validStudentGroup_isAdded() {
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addStudent_validStudentEmail_isAdded() {
        Student student = new Student("1", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addStudent_invalidStudentId_notAdded() {
        Student student = new Student("", "nume", 10, "email@domeniu.com");
        assertThrows(ValidationException.class, ()->service.addStudent(student));
    }

    @Test
    void addStudent_invalidStudentName_notAdded() {
        Student student = new Student("1", "", 10, "email@domeniu.com");
        assertThrows(ValidationException.class, ()->service.addStudent(student));
    }

    @Test
    void addStudent_invalidStudentGroup_notAdded() {
        Student student = new Student("1", "nume", -10, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void addStudent_invalidStudentEmail_notAdded() {
        Student student = new Student("1", "nume", 10, "");
        assertThrows(ValidationException.class, ()->service.addStudent(student));
    }

    @Test
    void addStudent_groupNumberIsZero_isAdded() {
        Student student = new Student("1", "nume", 0, "email@domeniu.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addStudent_groupNumberIsMinusOne_notAdded() {
        Student student = new Student("1", "nume", -1, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    //AddAssignment
    @Test
    public void addAssignment_validAssignment_isAdded(){
        Tema tema = new Tema("1","descriere",1,1);
        assertNull(service.addTema(tema));
    }

    @Test
    public void addAssignment_invalidDataAssignment_notAdded(){
        Tema tema = new Tema("","",1,1);
        assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

    @Test
    public void addAssignment_alreadyExistingAssignment_notAdded(){
        service.addTema(new Tema("123","abc",1,1));
        assertNotNull(service.addTema(new Tema("123","abc",1,1)));
        assertEquals(1, service.getAllTeme().spliterator().getExactSizeIfKnown());
    }

    @Test
    public void addAssignment_invalidID_throwsValidationException() {
        assertThrows(ValidationException.class,
                () -> service.addTema(new Tema(null, "descriere", 1, 1)));
        assertThrows(ValidationException.class,
                () -> service.addTema(new Tema("", "descriere", 1, 1)));
    }

    @Test
    public void addAssignment_invalidDescription_throwsValidationException() {
        assertThrows(ValidationException.class,
                () -> service.addTema(new Tema("nrTema", null, 1, 1)));
        assertThrows(ValidationException.class,
                () -> service.addTema(new Tema("nrTema", "", 1, 1)));
    }

    @Test
    public void addAssignment_invalidDeadline_throwsValidationException() {
        assertThrows(ValidationException.class,
                () -> service.addTema(new Tema("nrTema", "descriere", -2, 1)));
        assertThrows(ValidationException.class,
                () -> service.addTema(new Tema("nrTema", "descriere", 155, 1)));
    }

    @Test
    public void addAssignment_invalidPrimire_throwsValidationException() {
        assertThrows(ValidationException.class,
                () -> service.addTema(new Tema("nrTema", "descriere", 10, -1)));
        assertThrows(ValidationException.class,
                () -> service.addTema(new Tema("nrTema", "descriere", 10, 9999)));
    }
}