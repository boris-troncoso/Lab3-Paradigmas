Laboratorio 3 - Paradigmas de Programacion
Pokemon TCG en Java (POO)

Estudiante: Boris Troncoso Conopan
RUT: 19.559.856-8
Profesor: Gonzalo Martinez
USACH


para ejecutar:
  - abrir una consola en la carpeta del proyecto
  - javac --release 11 -encoding UTF-8 -d out src\*.java
  - java -cp out Main
  - con la opcion 8 del menu se carga una partida de demostracion
    con cartas y mazos ya listos para probar todo


Lenguaje: Java 11


Archivos (todas las clases llevan el sufijo _BorisTroncoso_195598568):
  Carta - clase abstracta base de las cartas
  CartaPokemon - carta pokemon con sus ataques y habilidad
  CartaEnergia - carta de energia
  CartaEntrenador - carta de entrenador (partidario u objeto)
  Ataque - ataques y habilidades con su costo
  Efecto - interfaz de los efectos
  EfectoSinEfecto, EfectoEstado, EfectoDanoPorMoneda,
  EfectoRobarCartas, EfectoCurar, EfectoCambiarActivo - efectos concretos
  CartaEnJuego - pokemon ya puesto en la mesa
  Mazo - mazo de 60 cartas y barajado
  Jugador - estado del jugador
  Juego - estado de la partida y reglas del juego
  RecomendadorIA - recomendacion de jugada usando la api de gemini (rf opcional)
  Menu - menu de consola
  Main - punto de entrada

notas finales
  el barajado y las monedas usan el generador pseudoaleatorio del
    enunciado, asi que con la misma semilla la partida siempre se
    repite igual

  los pokemon EX entregan dos premios al ser derrotados como pide
    el enunciado
