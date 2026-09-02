import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MatrizCSV {

    public static void main(String[] args) {
        String nombreArchivo = "matriz_100k_x_100k.csv";
        int filas = 100000;
        int columnas = 100000;

        System.out.println("Iniciando generación de matriz...");
        long tiempoInicio = System.currentTimeMillis();

        // Búfer directo de 16 MB en memoria RAM nativa
        int tamanoBufferBytes = 16 * 1024 * 1024;
        ByteBuffer buffer = ByteBuffer.allocateDirect(tamanoBufferBytes);

        try (FileOutputStream fos = new FileOutputStream(nombreArchivo);
             FileChannel canal = fos.getChannel()) {

            // Representación en bytes directos
            byte valorCero = '0';
            byte delimitadorFila = '1';
            byte coma = ',';

            for (int i = 0; i < filas; i++) {
                
                // 1. Escribimos los elementos de la fila actual separados por comas
                for (int j = 0; j < columnas; j++) {
                    
                    if (buffer.remaining() < 4) {
                        buffer.flip();
                        canal.write(buffer);
                        buffer.clear();
                    }

                    buffer.put(valorCero);
                    
                    // Si no es el final de la fila, agregamos coma de separación
                    if (j < columnas - 1) {
                        buffer.put(coma);
                    }
                }

                // 2. Al terminar la fila i, escribimos el marcador '1' para separar la fila
                if (buffer.remaining() < 4) {
                    buffer.flip();
                    canal.write(buffer);
                    buffer.clear();
                }

                buffer.put(coma);
                buffer.put(delimitadorFila);
                
                // Agregamos coma tras el 1 para continuar con la siguiente fila (si no es la última)
                if (i < filas - 1) {
                    buffer.put(coma);
                }

                // Monitoreo en consola cada 5,000 filas procesadas
                if ((i + 1) % 5000 == 0) {
                    double progreso = ((i + 1) / (double) filas) * 100;
                    long transcurridoSeg = (System.currentTimeMillis() - tiempoInicio) / 1000;
                    System.out.printf("Progreso: %.1f%% (%d/%d filas) - Tiempo transcurrido: %d seg%n",
                            progreso, (i + 1), filas, transcurridoSeg);
                }
            }

            // Vaciar el remanente de memoria al disco
            if (buffer.position() > 0) {
                buffer.flip();
                canal.write(buffer);
            }

            System.out.println("\n¡Matriz generada con éxito!");

        } catch (IOException e) {
            System.err.println("Error en disco: " + e.getMessage());
            e.printStackTrace();
        }

        long tiempoFin = System.currentTimeMillis();
        double minutosTotales = (tiempoFin - tiempoInicio) / 1000.0 / 60.0;
        System.out.printf("Tiempo total final: %.2f minutos.%n", minutosTotales);
    }
}
