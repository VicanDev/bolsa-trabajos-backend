package com.bolsa.trabajos.service;

import com.bolsa.trabajos.model.Categoria;
import com.bolsa.trabajos.model.Empleador;
import com.bolsa.trabajos.model.Oferta;
import com.bolsa.trabajos.model.Postulacion;
import com.bolsa.trabajos.model.Postulante;
import com.bolsa.trabajos.model.Usuario;
import com.bolsa.trabajos.repository.CategoriaRepository;
import com.bolsa.trabajos.repository.EmpleadorRepository;
import com.bolsa.trabajos.repository.OfertaRepository;
import com.bolsa.trabajos.repository.PostulacionRepository;
import com.bolsa.trabajos.repository.PostulanteRepository;
import com.bolsa.trabajos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Genera una carga masiva de datos de prueba (simulación) para poblar la base de datos
 * con un volumen realista, siguiendo el mismo esquema que datos_prueba.sql.
 */
@Service
public class DatosPruebaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpleadorRepository empleadorRepository;

    @Autowired
    private PostulanteRepository postulanteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Random random = new Random();

    private static final String[] NOMBRES_CATEGORIAS = {
            "Tecnología y Sistemas", "Atención al Cliente", "Logística y Almacén", "Ventas y Marketing",
            "Recursos Humanos", "Contabilidad y Finanzas", "Salud", "Educación",
            "Construcción e Ingeniería", "Diseño y Marketing Digital", "Legal", "Turismo y Hotelería",
            "Producción y Manufactura", "Administración", "Gastronomía"
    };

    private static final String[] NOMBRES_EMPRESAS = {
            "Tech Solutions SAC", "Contact Center Perú SAC", "Logística Express Perú", "Grupo Comercial Lima SAC",
            "Consultora RRHH Andina", "Estudio Contable Sur", "Clínica San Rafael", "Instituto Educativo San Martín",
            "Constructora Andina SAC", "Marketing Digital Perú SAC", "Estudio Legal Asociados", "Hotel Costa del Sol",
            "Manufacturas Industriales SAC", "Grupo Administrativo Lima", "Restaurantes Unidos SAC"
    };

    private static final String[][] PUESTOS_POR_CATEGORIA = {
            {"Desarrollador Backend Java", "Soporte Técnico de Sistemas"},
            {"Ejecutivo de Atención al Cliente", "Supervisor de Call Center"},
            {"Asistente de Almacén", "Coordinador de Logística"},
            {"Ejecutivo de Ventas", "Especialista en Marketing"},
            {"Analista de Recursos Humanos", "Reclutador de Personal"},
            {"Asistente Contable", "Analista Financiero"},
            {"Enfermero Asistencial", "Técnico en Farmacia"},
            {"Docente de Primaria", "Coordinador Académico"},
            {"Ingeniero Civil Junior", "Maestro de Obra"},
            {"Diseñador Gráfico", "Community Manager"},
            {"Asistente Legal", "Analista de Contratos"},
            {"Recepcionista de Hotel", "Guía Turístico"},
            {"Operario de Producción", "Supervisor de Planta"},
            {"Asistente Administrativo", "Secretaria Ejecutiva"},
            {"Cocinero", "Mesero"}
    };

    private static final String[] UBICACIONES = {
            "Lima, Perú", "Callao, Perú", "Miraflores, Lima", "San Isidro, Lima",
            "Arequipa, Perú", "Trujillo, Perú", "Cusco, Perú", "Lima, Perú (Remoto)"
    };

    private static final String[] DURACIONES = {
            "Plazo Indeterminado", "3 meses", "6 meses", "1 año", "Tiempo Completo", "Medio Tiempo"
    };

    private static final String[] DISPONIBILIDADES = {
            "Tiempo Completo", "Medio Tiempo", "Prácticas", "Freelance"
    };

    private static final String[] HABILIDADES = {
            "Java, Spring Boot, MySQL", "Angular, TypeScript, HTML/CSS", "Atención al cliente, Office, Inglés Avanzado",
            "Excel avanzado, control de inventarios", "Ventas, negociación, CRM", "Contabilidad, tributación, SAP",
            "Comunicación, trabajo en equipo, liderazgo", "Diseño gráfico, Photoshop, Illustrator",
            "Redacción legal, análisis de contratos", "Atención al público, idiomas, protocolo"
    };

    private static final String[] NOMBRES_PERSONAS = {
            "Carlos", "María", "José", "Ana", "Luis", "Rosa", "Pedro", "Carmen", "Jorge", "Lucía",
            "Miguel", "Patricia", "Fernando", "Sofía", "Diego", "Valeria", "Ricardo", "Daniela", "Andrés", "Gabriela"
    };

    private static final String[] APELLIDOS_PERSONAS = {
            "García", "Rodríguez", "Pérez", "López", "Gómez", "Torres", "Flores", "Ramírez", "Vargas", "Castro",
            "Rojas", "Chávez", "Mendoza", "Quispe", "Reyes", "Salazar", "Herrera", "Cruz", "Medina", "Aguilar"
    };

    private static final String[] ESTADOS_POSTULACION = {"PENDIENTE", "ACEPTADO", "RECHAZADO"};

    private static final int NUM_EMPLEADORES = 15;
    private static final int NUM_POSTULANTES = 35;
    private static final int POSTULANTES_POR_OFERTA = 5;

    @Transactional
    public Map<String, Object> reiniciarBaseDatos() {
        postulacionRepository.deleteAllInBatch();
        ofertaRepository.deleteAllInBatch();
        postulanteRepository.deleteAllInBatch();
        empleadorRepository.deleteAllInBatch();
        usuarioRepository.deleteAllInBatch();
        categoriaRepository.deleteAllInBatch();

        List<Categoria> categorias = crearCategorias();
        List<Empleador> empleadores = crearEmpleadores();
        List<Postulante> postulantes = crearPostulantes();
        List<Oferta> ofertas = crearOfertas(empleadores, categorias);
        List<Postulacion> postulaciones = crearPostulaciones(ofertas, postulantes);

        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("mensaje", "Base de datos reiniciada con datos de simulación");
        resumen.put("categorias", categorias.size());
        resumen.put("usuarios", NUM_EMPLEADORES + NUM_POSTULANTES);
        resumen.put("empleadores", empleadores.size());
        resumen.put("postulantes", postulantes.size());
        resumen.put("ofertas", ofertas.size());
        resumen.put("postulaciones", postulaciones.size());
        resumen.put("passwordUsuarios", "123456");
        return resumen;
    }

    private List<Categoria> crearCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        for (String nombre : NOMBRES_CATEGORIAS) {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categorias.add(categoriaRepository.save(categoria));
        }
        return categorias;
    }

    private List<Empleador> crearEmpleadores() {
        String hashPassword = passwordEncoder.encode("123456");
        List<Empleador> empleadores = new ArrayList<>();

        for (int i = 0; i < NUM_EMPLEADORES; i++) {
            String nombreEmpresa = NOMBRES_EMPRESAS[i];

            Usuario usuario = new Usuario();
            usuario.setNombre(nombreEmpresa);
            usuario.setCorreo("empresa" + (i + 1) + "@simulacion.pe");
            usuario.setPassword(hashPassword);
            usuario.setRol("EMPLEADOR");
            usuario = usuarioRepository.save(usuario);

            Empleador empleador = new Empleador();
            empleador.setUsuario(usuario);
            empleador.setRazonSocial(nombreEmpresa);
            empleador.setRuc(String.format("%011d", 20000000000L + i));
            empleador.setDescripcion("Empresa dedicada al rubro de " + NOMBRES_CATEGORIAS[i % NOMBRES_CATEGORIAS.length].toLowerCase() + ".");
            empleadores.add(empleadorRepository.save(empleador));
        }
        return empleadores;
    }

    private List<Postulante> crearPostulantes() {
        String hashPassword = passwordEncoder.encode("123456");
        List<Postulante> postulantes = new ArrayList<>();

        for (int i = 0; i < NUM_POSTULANTES; i++) {
            String nombre = NOMBRES_PERSONAS[i % NOMBRES_PERSONAS.length];
            String apellido = APELLIDOS_PERSONAS[(i * 7) % APELLIDOS_PERSONAS.length];
            String nombreCompleto = nombre + " " + apellido;

            Usuario usuario = new Usuario();
            usuario.setNombre(nombreCompleto);
            usuario.setCorreo("postulante" + (i + 1) + "@simulacion.pe");
            usuario.setPassword(hashPassword);
            usuario.setRol("POSTULANTE");
            usuario = usuarioRepository.save(usuario);

            Postulante postulante = new Postulante();
            postulante.setUsuario(usuario);
            postulante.setCvUrl("https://linkedin.com/in/" + nombre.toLowerCase() + apellido.toLowerCase() + (i + 1));
            postulante.setHabilidades(HABILIDADES[i % HABILIDADES.length]);
            postulante.setDisponibilidad(DISPONIBILIDADES[i % DISPONIBILIDADES.length]);
            postulantes.add(postulanteRepository.save(postulante));
        }
        return postulantes;
    }

    private List<Oferta> crearOfertas(List<Empleador> empleadores, List<Categoria> categorias) {
        List<Oferta> ofertas = new ArrayList<>();
        int indiceOferta = 0;

        for (int c = 0; c < categorias.size(); c++) {
            for (String puesto : PUESTOS_POR_CATEGORIA[c]) {
                Empleador empleador = empleadores.get(indiceOferta % empleadores.size());
                Categoria categoria = categorias.get(c);

                Oferta oferta = new Oferta();
                oferta.setEmpleador(empleador);
                oferta.setCategoria(categoria);
                oferta.setTitulo(puesto);
                oferta.setDescripcion("Buscamos un(a) " + puesto.toLowerCase() + " para unirse al equipo de "
                        + empleador.getRazonSocial() + ".");
                oferta.setUbicacion(UBICACIONES[indiceOferta % UBICACIONES.length]);
                oferta.setDuracion(DURACIONES[indiceOferta % DURACIONES.length]);
                oferta.setRequisitos("Experiencia previa en el área, disponibilidad inmediata y ganas de aprender.");
                ofertas.add(ofertaRepository.save(oferta));
                indiceOferta++;
            }
        }
        return ofertas;
    }

    private List<Postulacion> crearPostulaciones(List<Oferta> ofertas, List<Postulante> postulantes) {
        List<Postulacion> postulaciones = new ArrayList<>();

        for (Oferta oferta : ofertas) {
            List<Postulante> candidatos = new ArrayList<>(postulantes);
            Collections.shuffle(candidatos, random);

            for (int i = 0; i < POSTULANTES_POR_OFERTA; i++) {
                Postulacion postulacion = new Postulacion();
                postulacion.setOferta(oferta);
                postulacion.setPostulante(candidatos.get(i));
                postulacion.setEstado(ESTADOS_POSTULACION[random.nextInt(ESTADOS_POSTULACION.length)]);
                postulaciones.add(postulacionRepository.save(postulacion));
            }
        }
        return postulaciones;
    }
}
