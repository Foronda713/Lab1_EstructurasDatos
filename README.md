# Lab 1: Generación y Manipulación de Matriz Gigante CSV (100k x 100k)

**Estudiante:** Miguel Ángel Foronda  
**Materia:** Estructuras de Datos  

---

## Objetivo del Proyecto

Demostrar la resolución óptima al problema de generación, almacenamiento y lectura de una matriz masiva de **10 mil millones de elementos** ($100.000 \times 100.000$ celdas, ~20 GB de datos), abordando los siguientes desafíos técnicos:

* **Consumo excesivo de RAM:** Evitar errores de *OutOfMemoryError* procesando la información mediante patrones de I/O en flujo (*streaming*) continuo usando memoria nativa fuera del Heap de la JVM (`ByteBuffer.allocateDirect`).
* **Escritura lenta a disco:** Maximizar el rendimiento de escritura volcando bloques binarios de 16 MB directamente al canal de almacenamiento con `FileChannel`.
* **Optimización en la manipulación y almacenamiento:** Eliminar la sobrecarga de los saltos de línea tradicionales (`\n`) y usar el delimitador estructurado `'1'` para marcar el cierre de filas en un archivo continuo.

---

## Archivos del Repositorio

* **`MatrizCSV.java`:** Código principal encargado de la generación y escritura eficiente de la matriz en disco usando *NIO FileChannel*.
* **`README.md`:** Documentación general del repositorio y guía de verificación.

---

## Estructura y Formato de Datos (`matriz_100k_x_100k.csv`)

El archivo se almacena como un flujo continuo UTF-8 para optimizar espacio y velocidad de lectura:
* **Datos de celdas:** `'0'`
* **Separador de columnas:** Coma (`,`)
* **Delimitador de fin de fila:** `'1'`

---

## Comandos de verificación en consola (Git Bash / Linux)

### 1. Ver un rango de filas (ej: de la 1 a la 3)
awk -v RS='1' 'NR>=1 && NR<=3 { print $0 "1" }' matriz_100k_x_100k.csv | sed 's/0/\x1b[1;32m0\x1b[0m/g; s/,/\x1b[1;34m,\x1b[0m/g; s/1/\x1b[1;37m1\x1b[0m/g'

* Muestra de la fila 1 a la 3 diferenciando con colores: ceros en verde, comas en azul y el 1 de fin de fila en blanco.

### 2. Ver una fila específica (ej: fila 500)
awk -v RS='1' 'NR==500 { print $0 "1" }' matriz_100k_x_100k.csv | sed 's/0/\x1b[1;32m0\x1b[0m/g; s/,/\x1b[1;34m,\x1b[0m/g; s/1/\x1b[1;37m1\x1b[0m/g'

* Busca el delimitador '1' número 500 para imprimir en pantalla únicamente esa fila con los mismos colores.

### 3. Contar la cantidad de unos ('1') en el archivo
tr -cd '1' < matriz_100k_x_100k.csv | wc -c

* Borra todo lo que no sea '1' y los cuenta. Debe retornar exactamente 100000 para confirmar que todas las filas fueron creadas correctamente.
