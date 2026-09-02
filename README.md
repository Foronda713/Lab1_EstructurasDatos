# Matriz Gigante CSV (100k x 100k)

Código en Java para generar y verificar una matriz de 10.000.000.000 de datos (~20 GB) usando I/O por streaming para no saturar la memoria RAM.

## Estructura del archivo (matriz_100k_x_100k.csv)

Para evitar problemas de rendimiento con saltos de línea tradicionales (\n), el archivo guarda la matriz como un flujo continuo:
* Celdas: 0
* Separador de columnas: ,
* Separador de filas: 1

Ejemplo del flujo: 0,0,0,1,0,0,0,1,...

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