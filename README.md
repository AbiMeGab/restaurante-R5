# Restaurante - Reto 5 Persistencia de Datos

Aplicacion Android que permite gestionar restaurantes de la zona centro de la Ciudad de Mexico y los alimentos que ofrece cada uno. Los datos se almacenan de forma persistente usando SQLite.


## Tecnologias

- Java 11
- Android SDK 37 (minimo SDK 26)
- SQLite (persistencia local)
- Android Gradle Plugin 9.5.0
- Material Components 1.14.0
- AndroidX Lifecycle (ViewModel y LiveData) 2.7.0
- AndroidX AppCompat 1.8.0


## Arquitectura

El proyecto sigue el patron MVVM (Model-View-ViewModel) con principios SOLID.

```
com.example.restaurante/
    modelo/
        entidad/        Clases POJO (Restaurante, Comida)
        bd/             Contrato y ayudante de base de datos SQLite
        repositorio/    Operaciones CRUD para cada entidad
    vistamodelo/        ViewModels con LiveData
    vista/              Activities (7 pantallas)
    adaptador/          Adaptadores para ListView con ViewHolder
    util/               Utilidades de mapeo de tipos
```


## Base de datos

Dos tablas relacionadas con clave foranea y eliminacion en cascada:

- restaurant: id, nombre
- food: id, restaurant_id (FK), nombre, precio, descripcion, tipo (food, drink, complement)


## Pantallas

1. Lista de restaurantes con opcion de agregar
2. Formulario para registrar un nuevo restaurante
3. Editar o eliminar un restaurante (acceso por menu contextual al mantener presionado)
4. Lista de comida con pestanas (Comida, Bebidas, Complementos)
5. Formulario para registrar un nuevo platillo
6. Editar o eliminar un platillo (acceso por menu contextual al mantener presionado)
7. Detalle completo de un platillo


## Como ejecutar

1. Abrir Android Studio (se recomienda la version mas reciente compatible con AGP 9.5)
2. Seleccionar File, luego Open y navegar a la carpeta raiz del proyecto
3. Esperar a que Gradle sincronice las dependencias
4. Conectar un dispositivo fisico o iniciar un emulador con API 26 o superior
5. Presionar Run o usar el atajo Shift+F10
6. La aplicacion inicia en la pantalla de lista de restaurantes


## Funcionalidades principales

- Registro, edicion y eliminacion de restaurantes
- Registro, edicion y eliminacion de platillos asociados a cada restaurante
- Clasificacion de platillos por tipo: comida, bebida y complemento
- Persistencia de datos con SQLite (los datos se conservan al cerrar la aplicacion)
- Validacion de campos en formularios
- Confirmacion antes de eliminar registros
