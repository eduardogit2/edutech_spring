package com.edutech.courses;

import com.edutech.courses.model.*;
import com.edutech.courses.service.*;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Profile("dev") 
@Component 
public class DataLoader implements CommandLineRunner {

    private final AlumnoService alumnoService;
    private final ProfesorService profesorService;
    private final CoordinadorService coordinadorService;
    private final CursoService cursoService;
    private final EvaluacionService evaluacionService;
    private final NotaService notaService;
    private final MaterialService materialService;
    private final InscripcionService inscripcionService;

    private final Faker faker = new Faker(new Locale("es-CL"));
    private final Random random = new Random();

    public DataLoader(AlumnoService alumnoService,
                    ProfesorService profesorService,
                    CoordinadorService coordinadorService,
                    CursoService cursoService,
                    EvaluacionService evaluacionService,
                    NotaService notaService,
                    MaterialService materialService,
                    InscripcionService inscripcionService) {
        this.alumnoService = alumnoService;
        this.profesorService = profesorService;
        this.coordinadorService = coordinadorService;
        this.cursoService = cursoService;
        this.evaluacionService = evaluacionService;
        this.notaService = notaService;
        this.materialService = materialService;
        this.inscripcionService = inscripcionService;
    }

    
    @Override
    public void run(String... args) {
        List<Coordinador> coordinadores = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Coordinador c = Coordinador.builder()
                    .run(generarRUT())
                    .nombre(faker.name().firstName())
                    .apellidoPaterno(faker.name().lastName())
                    .apellidoMaterno(faker.name().lastName())
                    .email(faker.internet().emailAddress())
                    .telefono(faker.numerify("+56 9 #### ####"))
                    .build();
            coordinadores.add(coordinadorService.crear(c)); 
        }
        System.out.println("Coordinadores creados: " + coordinadores.size());

        List<Profesor> profesores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Profesor p = Profesor.builder()
                    .run(generarRUT())
                    .nombre(faker.name().firstName())
                    .apellidoPaterno(faker.name().lastName())
                    .apellidoMaterno(faker.name().lastName())
                    .email(faker.internet().emailAddress())
                    .telefono(faker.numerify("+56 9 #### ####"))
                    .especialidad(faker.educator().course()) 
                    .gradoAcademico(faker.educator().secondarySchool()) 
                    .build();
            profesores.add(profesorService.crear(p)); 
        }
        System.out.println("Profesores creados: " + profesores.size());
        List<Alumno> alumnos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Alumno a = Alumno.builder()
                    .run(generarRUT())
                    .nombre(faker.name().firstName())
                    .apellidoPaterno(faker.name().lastName())
                    .apellidoMaterno(faker.name().lastName())
                    .email(faker.internet().emailAddress())
                    .telefono(faker.numerify("+56 9 #### ####"))
                    .direccion(faker.address().fullAddress()) 
                    .fechaNacimiento(LocalDate.now().minusYears(18 + random.nextInt(10))) 
                    .fechaRegistro(LocalDate.now()) 
                    .build();
            alumnos.add(alumnoService.crear(a));
        }
        System.out.println("Alumnos creados: " + alumnos.size());

        List<String> niveles = Arrays.asList("Básico", "Intermedio", "Avanzado");
        List<String> modalidades = Arrays.asList("Presencial", "Online", "Híbrido");

        List<Curso> cursos = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Curso curso = Curso.builder()
                    .codigo("CUR-" + faker.number().digits(4)) 
                    .titulo(faker.educator().course()) 
                    .descripcion(faker.lorem().sentence(10)) 
                    .materia(faker.job().field()) 
                    .nivel(niveles.get(random.nextInt(niveles.size()))) 
                    .creditos(5 + random.nextInt(5)) 
                    .modalidad(modalidades.get(random.nextInt(modalidades.size()))) 
                    .cuposMaximos(20 + random.nextInt(20))
                    .fechaInicio(LocalDate.now().plusDays(3 + random.nextInt(30))) 
                    .fechaFin(LocalDate.now().plusDays(90 + random.nextInt(60))) 
                    .activo(true) 
                    .profesor(profesores.get(random.nextInt(profesores.size()))) 
                    .coordinador(coordinadores.get(random.nextInt(coordinadores.size())))
                    .build();
            cursos.add(cursoService.crear(curso)); 
        }
        System.out.println("Cursos creados: " + cursos.size());

        List<Evaluacion> evaluaciones = new ArrayList<>();
        for (Curso curso : cursos) {
            for (int i = 1; i <= 2; i++) { 
                Evaluacion ev = Evaluacion.builder()
                        .nombre("Evaluación " + i + " - " + curso.getTitulo())
                        .descripcion(faker.lorem().sentence(6))
                        .fecha(curso.getFechaInicio().plusDays(i * 15 + random.nextInt(10))) 
                        .peso(20.0 + random.nextDouble() * 30.0) 
                        .curso(curso) 
                        .build();
                evaluaciones.add(evaluacionService.crear(ev)); 
            }
        }
        System.out.println("Evaluaciones creadas: " + evaluaciones.size());

        for (Curso curso : cursos) {
            Material mCurso = Material.builder()
                    .tipo("PDF")
                    .url("https://edutech.cl/material/" + faker.internet().uuid() + ".pdf") 
                    .descripcion("Material de lectura para el curso " + curso.getTitulo())
                    .curso(curso)
                    .evaluacion(null) 
                    .build();
            materialService.crear(mCurso);
            System.out.println("Material de curso creado: " + mCurso.getUrl());
        }

        for (Evaluacion ev : evaluaciones) {
            Material mEval = Material.builder()
                    .tipo("Video")
                    .url("https://edutech.cl/video/" + faker.internet().uuid() + ".mp4") 
                    .descripcion("Recurso de video para la " + ev.getNombre())
                    .evaluacion(ev)
                    .curso(ev.getCurso()) 
                    .build();
            materialService.crear(mEval);
            System.out.println("Material de evaluación creado: " + mEval.getUrl());
        }
        System.out.println("Materiales creados.");

        for (Alumno alumno : alumnos) {
            Set<Curso> cursosInscritos = new HashSet<>();
            while (cursosInscritos.size() < (2 + random.nextInt(2))) {
                cursosInscritos.add(cursos.get(random.nextInt(cursos.size())));
            }

            for (Curso curso : cursosInscritos) {
                Inscripcion insc = Inscripcion.builder()
                        .alumno(alumno)
                        .curso(curso)
                        .fechaInscripcion(LocalDate.now().minusDays(random.nextInt(30))) 
                        .estado(Inscripcion.EstadoInscripcion.MATRICULADO) 
                        .build();
                inscripcionService.crear(insc);
                System.out.println("Inscripción creada para alumno " + alumno.getNombre() + " en curso " + curso.getTitulo());

                List<Evaluacion> evaluacionesDelCurso = evaluacionService.porCurso(curso.getId());
                if (!evaluacionesDelCurso.isEmpty()) {
                    for (Evaluacion ev : evaluacionesDelCurso) {
                        Nota nota = Nota.builder()
                                .alumno(alumno)
                                .evaluacion(ev)
                                .calificacion(1.0 + (random.nextDouble() * 6.0)) 
                                .build();
                        notaService.registrar(nota); 
                        System.out.println("Nota registrada para " + alumno.getNombre() + " en " + ev.getNombre() + ": " + nota.getCalificacion());
                    }
                }
            }
        }
        System.out.println("Inscripciones y notas creadas.");
    }

    private String generarRUT() {
        int cuerpo = 1000000 + random.nextInt(9000000); 
        char dv = calcularDV(cuerpo); 
        return String.format("%,d-%s", cuerpo, dv).replace(",", "."); 
    }

    private char calcularDV(int rut) {
        int suma = 0;
        int factor = 2; 
        while (rut > 0) {
            suma += (rut % 10) * factor; 
            rut /= 10; 
            factor = (factor == 7) ? 2 : factor + 1; 
        }
        int dv = 11 - (suma % 11); 
        if (dv == 11) return '0'; 
        if (dv == 10) return 'K'; 
        return (char) ('0' + dv);
    }
}
