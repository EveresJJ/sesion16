import dao.CityDAO;
import dao.StudentDAO;
import models.City;
import models.Student;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CityDAO cityDAO = new CityDAO();
        StudentDAO studentDAO = new StudentDAO();

        String option;
        do {
            displayMainMenu();
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    handleRegistration(scanner, studentDAO, cityDAO);
                    break;
                case "2":
                    handleListing(scanner, studentDAO, cityDAO);
                    break;
                case "3":
                    handleUpdate(scanner, studentDAO, cityDAO);
                    break;
                case "4":
                    handleDeletion(scanner, studentDAO, cityDAO);
                    break;
                case "5":
                    System.out.println("Cerrando el sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Por favor, selecciona una opción válida.");
            }
        } while (!option.equals("5"));

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Menú Principal ===");
        System.out.println("1. Registrar");
        System.out.println("2. Listar");
        System.out.println("3. Actualizar");
        System.out.println("4. Eliminar");
        System.out.println("5. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private static void handleRegistration(Scanner scanner, StudentDAO studentDAO, CityDAO cityDAO) {
        System.out.println("\n¿Deseas registrar un estudiante o una ciudad?");
        System.out.println("1. Estudiante");
        System.out.println("2. Ciudad");
        System.out.print("Elige una opción: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                Student newStudent = new Student();
                System.out.print("Introduce el CIF del estudiante: ");
                newStudent.setCif(scanner.nextLine());
                System.out.print("Introduce el nombre del estudiante: ");
                newStudent.setFirstName(scanner.nextLine());
                System.out.print("Introduce el apellido del estudiante: ");
                newStudent.setLastName(scanner.nextLine());
                System.out.print("Introduce el correo electrónico: ");
                newStudent.setEmail(scanner.nextLine());

                System.out.print("Introduce el nombre de la ciudad del estudiante: ");
                String cityName = scanner.nextLine();
                City city = cityDAO.getCities().stream()
                        .filter(c -> c.getName().equalsIgnoreCase(cityName))
                        .findFirst()
                        .orElse(null);

                if (city != null) {
                    newStudent.setCity(city);
                    studentDAO.save(newStudent);
                    System.out.println("✅ Estudiante registrado correctamente.");
                } else {
                    System.out.println("⚠️ No se encontró la ciudad " + cityName + ". Registro fallido.");
                }
                break;

            case "2":
                City newCity = new City();
                System.out.print("Introduce el nombre de la nueva ciudad: ");
                newCity.setName(scanner.nextLine());
                cityDAO.save(newCity);
                System.out.println("✅ Ciudad registrada exitosamente.");
                break;

            default:
                System.out.println("⚠️ Opción inválida. Regreso al menú principal.");
        }
    }

    private static void handleListing(Scanner scanner, StudentDAO studentDAO, CityDAO cityDAO) {
        System.out.println("\n¿Qué lista deseas ver?");
        System.out.println("1. Lista de Estudiantes");
        System.out.println("2. Lista de Ciudades");
        System.out.print("Elige una opción: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                List<Student> students = studentDAO.getStudents();
                System.out.println("\n=== Lista de Estudiantes ===");
                if (students.isEmpty()) {
                    System.out.println("No hay estudiantes registrados.");
                } else {
                    students.forEach(student -> System.out.println(student.getFirstName() + " " + student.getLastName() +
                            " (CIF: " + student.getCif() + ")\nCorreo: " + student.getEmail() +
                            "\nCiudad: " + (student.getCity() != null ? student.getCity().getName() : "No asignada")));
                }
                break;

            case "2":
                List<City> cities = cityDAO.getCities();
                System.out.println("\n=== Lista de Ciudades ===");
                if (cities.isEmpty()) {
                    System.out.println("No hay ciudades registradas.");
                } else {
                    cities.forEach(city -> System.out.println("Ciudad: " + city.getName() +
                            "\nEstado: " + (city.isState() ? "Activo" : "Inactivo")));
                }
                break;

            default:
                System.out.println("⚠️ Opción inválida.");
        }
    }

    private static void handleUpdate(Scanner scanner, StudentDAO studentDAO, CityDAO cityDAO) {
        System.out.println("\n¿Deseas actualizar un estudiante o una ciudad?");
        System.out.println("1. Estudiante");
        System.out.println("2. Ciudad");
        System.out.print("Elige una opción: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Introduce el ID del estudiante a actualizar: ");
                Long studentId = Long.parseLong(scanner.nextLine());
                Student student = studentDAO.getStudent(studentId);

                if (student != null) {
                    System.out.print("Nuevo nombre: ");
                    student.setFirstName(scanner.nextLine());
                    System.out.print("Nuevo apellido: ");
                    student.setLastName(scanner.nextLine());
                    System.out.print("Nuevo correo electrónico: ");
                    student.setEmail(scanner.nextLine());
                    System.out.print("Nueva ciudad: ");
                    String cityName = scanner.nextLine();

                    City city = cityDAO.getCities().stream()
                            .filter(c -> c.getName().equalsIgnoreCase(cityName))
                            .findFirst()
                            .orElse(null);
                    student.setCity(city);
                    studentDAO.update(student);
                    System.out.println("✅ Estudiante actualizado correctamente.");
                } else {
                    System.out.println("⚠️ No se encontró el estudiante.");
                }
                break;

            case "2":
                System.out.print("Introduce el ID de la ciudad a actualizar: ");
                Long cityId = Long.parseLong(scanner.nextLine());
                City city = cityDAO.getCity(cityId);

                if (city != null) {
                    System.out.println("Ciudad actual: " + city.getName() + " - Estado: " + (city.isState() ? "Activo" : "Inactivo"));
                    System.out.print("¿Deseas cambiar el estado? (S/N): ");
                    String changeState = scanner.nextLine();

                    if (changeState.equalsIgnoreCase("S")) {
                        city.setState(!city.isState());
                        cityDAO.update(city);
                        System.out.println("✅ Estado de la ciudad actualizado.");
                    }
                } else {
                    System.out.println("⚠️ No se encontró la ciudad.");
                }
                break;

            default:
                System.out.println("⚠️ Opción inválida.");
        }
    }

    private static void handleDeletion(Scanner scanner, StudentDAO studentDAO, CityDAO cityDAO) {
        System.out.println("\n¿Deseas eliminar un estudiante o una ciudad?");
        System.out.println("1. Estudiante");
        System.out.println("2. Ciudad");
        System.out.print("Elige una opción: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Introduce el ID del estudiante a eliminar: ");
                Long studentId = Long.parseLong(scanner.nextLine());
                Student student = studentDAO.getStudent(studentId);

                if (student != null) {
                    System.out.print("¿Estás seguro de eliminar a " + student.getFirstName() + "? (S/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("S")) {
                        studentDAO.delete(student);
                        System.out.println("✅ Estudiante eliminado.");
                    }
                } else {
                    System.out.println("⚠️ No se encontró el estudiante.");
                }
                break;

            case "2":
                System.out.print("Introduce el ID de la ciudad a eliminar: ");
                Long cityId = Long.parseLong(scanner.nextLine());
                City city = cityDAO.getCity(cityId);

                if (city != null) {
                    System.out.print("¿Estás seguro de eliminar la ciudad " + city.getName() + "? (S/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("S")) {
                        cityDAO.delete(city);
                        System.out.println("✅ Ciudad eliminada.");
                    }
                } else {
                    System.out.println("⚠️ No se encontró la ciudad.");
                }
                break;

            default:
                System.out.println("⚠️ Opción inválida.");
        }
    }
}
