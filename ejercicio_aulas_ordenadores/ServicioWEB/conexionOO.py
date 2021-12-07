import pymysql
import json
import Persona
from Persona import Persona, PersonaEncoder
#La librería se instala con el comando: pip install pymysql
class Conexion:
    
    def __init__(self, usuario, passw, bd):
        self._usuario = usuario
        self._passw = passw
        self._bd = bd
        try:
            #Abrimos y cerramos la bd para dos cosas: comprobar que los datos de conexión son correctos y
            #dar el tipo adecuado a la variable self._conexion.
            self._conexion = pymysql.connect(host='localhost',
                                    user=self._usuario,
                                    password=self._passw,
                                    db=self._bd)
            self._conexion.close()
            print("Datos de conexión correctos.")
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            print("Ocurrió un error con los datos de conexión: ", e)
        
         
    def conectar(self):
        """Devuelve la variable conexion o -1 si la conexión no ha sido correcta."""
        try:
            self._conexion = pymysql.connect(host='localhost',
                                    user=self._usuario,
                                    password=self._passw,
                                    db=self._bd)
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1

    def cerrarConexion(self):
        self._conexion.close()
        

    def insertarProfesor(self, id, nombre, clave, permisos):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "INSERT INTO profesor(id, nombre, clave, permisos) VALUES (%s, %s, %s, %s)"
            cursor.execute(consulta, (id, nombre, clave, permisos))
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1


    def insertarAula(self, id_aula, nombre_aula):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "INSERT INTO aula(id_aula, nombre_aula) VALUES (%s, %s)"
            cursor.execute(consulta, (id_aula, nombre_aula))
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1


    def insertarOrdenador(self, id_ordenador, aula, modelo, almacenamiento, ram):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "INSERT INTO ordenador(id_ordenador, aula, modelo, almacenamiento, ram) VALUES (%s, %s, %s, %s, %s)"
            cursor.execute(consulta, (id_ordenador, aula, modelo, almacenamiento, ram))
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1

#CONTINUAR POR AQUI___________________________________--------------------------__________________

    #https://stackoverflow.com/questions/3286525/return-sql-table-as-json-in-python
    def seleccionarTodosProfesor(self):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT id, nombre, clave, permisos FROM profesor")
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def seleccionarTodosAula(self):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT id_aula, nombre_aula FROM aula")
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def seleccionarTodosOrdenador(self):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT id_ordenador, aula, modelo, almacenamiento, ram FROM ordenador")
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    #https://www.programcreek.com/python/example/104689/sklearn.datasets.fetch_20newsgroups
    #https://stackoverflow.com/questions/11280382/object-is-not-json-serializable
    def buscarIdProfesor(self, id):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT id, nombre, clave, permisos FROM profesor WHERE id = %s", (id))
                
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                # print(r)
                # print("----")
                print(r)
                # objJson = r[0]
                # # Con fetchall traemos todas las filas
                # pers = cursor.fetchall()
                # # Recorrer e imprimir
                # for row in pers:
                #     p = Persona(row[0],row[1],row[2],row[3])
                # objJson = json.dumps(p, cls=PersonaEncoder, indent=4)
                # #print(objJson)
                
                self.cerrarConexion()
                
                
                if (r):
                    return r[0]
                else:
                    return []
                
                
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def buscarIdAula(self, id_aula):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT id_aula, nombre_aula FROM aula WHERE id_aula = %s", (id_aula))

                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                # print(r)
                # print("----")
                print(r)
                # objJson = r[0]
                # # Con fetchall traemos todas las filas
                # pers = cursor.fetchall()
                # # Recorrer e imprimir
                # for row in pers:
                #     p = Persona(row[0],row[1],row[2],row[3])
                # objJson = json.dumps(p, cls=PersonaEncoder, indent=4)
                # #print(objJson)

                self.cerrarConexion()


                if (r):
                    return r[0]
                else:
                    return []


        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def buscarIdOrdenador(self, id_ordenador):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT id_ordenador, aula, modelo, almacenamiento, ram FROM ordenador WHERE id_ordenador = %s", (id_ordenador))

                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                # print(r)
                # print("----")
                print(r)
                # objJson = r[0]
                # # Con fetchall traemos todas las filas
                # pers = cursor.fetchall()
                # # Recorrer e imprimir
                # for row in pers:
                #     p = Persona(row[0],row[1],row[2],row[3])
                # objJson = json.dumps(p, cls=PersonaEncoder, indent=4)
                # #print(objJson)

                self.cerrarConexion()


                if (r):
                    return r[0]
                else:
                    return []


        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


    def buscarOrdenadoresPorAula(self, aula):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT id_ordenador, aula, modelo, almacenamiento, ram FROM ordenador WHERE aula = %s", (aula))
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []
        


    def cambiarClave(self, idEditar, nueva):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "UPDATE profesor SET clave = %s WHERE id = %s;"
                cursor.execute(consulta, (nueva, idEditar))

            # No olvidemos hacer commit cuando hacemos un cambio a la BD
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1
        
        
    #https://pynative.com/python-mysql-delete-data/
    def borrarProfesor(self, idBorrar):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "DELETE FROM profesor WHERE id = %s;"
                cursor.execute(consulta, (idBorrar))

                # No olvidemos hacer commit cuando hacemos un cambio a la BD
                self._conexion.commit()
                self.cerrarConexion()
                return cursor.rowcount #Registros afectados en el borrado.
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1


    def borrarAula(self, idBorrar):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "DELETE FROM aula WHERE id_aula = %s;"
                cursor.execute(consulta, (idBorrar))

                # No olvidemos hacer commit cuando hacemos un cambio a la BD
                self._conexion.commit()
                self.cerrarConexion()
                return cursor.rowcount #Registros afectados en el borrado.
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1


    def borrarOrdenador(self, idBorrar):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "DELETE FROM ordenador WHERE id_ordenador = %s;"
                cursor.execute(consulta, (idBorrar))

                # No olvidemos hacer commit cuando hacemos un cambio a la BD
                self._conexion.commit()
                self.cerrarConexion()
                return cursor.rowcount #Registros afectados en el borrado.
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1
        
    #https://pynative.com/python-mysql-update-data/
    def modificarProfesor(self, id, nombre, permisos):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "UPDATE profesor SET id = %s, nombre = %s, permisos = %s WHERE id = %s;"
            cursor.execute(consulta, (id, nombre, permisos, id))
            self._conexion.commit()
            self.cerrarConexion()
            return cursor.rowcount
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1


    def modificarAula(self, id_aula, nombre_aula):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "UPDATE aula SET id_aula = %s, nombre_aula = %s WHERE id_aula = %s;"
            cursor.execute(consulta, (id_aula, nombre_aula, id_aula))
            self._conexion.commit()
            self.cerrarConexion()
            return cursor.rowcount
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1


    def modificarOrdenador(self, id_ordenador, aula, modelo, almacenamiento, ram):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "UPDATE ordenador SET id_ordenador = %s, aula = %s, modelo = %s, almacenamiento = %s, ram = %s WHERE id_ordenador = %s;"
            cursor.execute(consulta, (id_ordenador, aula, modelo, almacenamiento, ram, id_ordenador))
            self._conexion.commit()
            self.cerrarConexion()
            return cursor.rowcount
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1