import conexionOO
import json
import Persona
from flask import Flask, request, jsonify
from flask_restful import Resource, Api

#Comenta y/o descomenta cada bloque para probar cada apartado.
#La base de datos la puedes encontrar en la carpeta del proyecto, solo tienes que importarla
#y cambiar las credenciales por las tuyas.
#https://blog.nearsoftjobs.com/crear-un-api-y-una-aplicaci%C3%B3n-web-con-flask-6a76b8bf5383
#https://content.breatheco.de/es/lesson/building-apis-with-python-flask

#pip install Flask
#pip install flask_restful

################################### Probando conexión ####################################
conex = conexionOO.Conexion('root','','desafio_ordenadores')
# conex.cerrarConexion()


################################### Probando inserción ###################################
#Dentro del método insertar se realiza una apertura y un cerrado de la conexión.
# dni = input("DNI? ")
# nombre = input("Nombre? ")
# clave = input("Clave? ")
# tefno = input("Teléfono? ")
# resultado = conex.insertarPersona(dni, nombre, clave, tefno)
# if(resultado == -1):
#     print("Problema al insertar el registro.")
# else:
#     print("Registro insertado con éxito.")
    

################################ Probando select de todos #######################################
# Dentro del método seleccionar se realiza una apertura y un cerrado de la conexión.
# listaPersonas = conex.seleccionarTodos()
# if (len(listaPersonas) != 0):
#     for pe in listaPersonas:
#         print(pe)
#         #print("DNI: " + pe[0] + ", nombre: " + pe[1])
# else:
#     print("No se han extraído datos.")




################################# Buscando por DNI ###############################################
#Dentro del método seleccionar se realiza una apertura y un cerrado de la conexión.
# dni = input("DNI a buscar? ")
# listaPersonas = conex.buscarDNI(dni)
# if (len(listaPersonas) != 0):
#     for pe in listaPersonas:
#         print(pe)
#         print("DNI: " + pe[0] + ", nombre: " + pe[1])
# else:
#     print("No se han encontrado datos")
    
    
############################### Probando cambiar la clave de un dni ###################################
#Dentro del método cambiarClave se realiza una apertura y un cerrado de la conexión.
# dniCambiar = input("DNI a cambiar? ")
# nuevaClave = input("Nueva clave? ")
# resultado = conex.cambiarClave(dniCambiar, nuevaClave)
# if (resultado == 0):
#     print("Clave cambiada.")
# else:
#     print("No se han encontrado datos")
    

##################################### Probando borrar por dni #########################################
#Dentro del método birrarDNI se realiza una apertura y un cerrado de la conexión.
# dniBorrar = input("DNI a borrar? ")
# resultado = conex.borrarDNI(dniBorrar)
# if (resultado == 0):
#     print("Registro borrado.")
# else:
#     print("No se han borrado datos")

app = Flask(__name__)
api = Api(app)

#------------------------------------------------------------------------------
@app.route('/')
def hello():
    return 'GESTIÓN DE PROFESORES, AULAS Y ORDENADORES'

#------------------------------------------------------------------------------
#https://blog.miguelgrinberg.com/post/running-your-flask-application-over-https
#pip install pyopenssl  --> para montarlo sobre https.
@app.route("/listaprofesores", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getProfesores(): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    listaProfesores = conex.seleccionarTodosProfesor()
    print(listaProfesores)
    if (len(listaProfesores) != 0):
        resp = jsonify(listaProfesores)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp


@app.route("/listaaulas", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getAulas(): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    listaAulas = conex.seleccionarTodosAula()
    print(listaAulas)
    if (len(listaAulas) != 0):
        resp = jsonify(listaAulas)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp


@app.route("/listaordenadores", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getOrdenadores(): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    listaOrdenador = conex.seleccionarTodosOrdenador()
    print(listaOrdenador)
    if (len(listaOrdenador) != 0):
        resp = jsonify(listaOrdenador)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp




#------------------------------------------------------------------------------
@app.route("/listaprofesores/<id>", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getProfesor(id): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    print(id)
    listaProfesores = conex.buscarIdProfesor(id)
    print(jsonify(listaProfesores))
    if (len(listaProfesores) != 0):
        resp = jsonify(listaProfesores)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp


@app.route("/listaaulas/<id_aula>", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getAula(id_aula): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    print(id)
    listaAulas = conex.buscarIdAula(id_aula)
    print(jsonify(listaAulas))
    if (len(listaAulas) != 0):
        resp = jsonify(listaAulas)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp


@app.route("/listaordenadores/<id_ordenador>", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getOrdenador(id_ordenador): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    print(id)
    listaOrdenadores = conex.buscarIdOrdenador(id_ordenador)
    print(jsonify(listaOrdenadores))
    if (len(listaOrdenadores) != 0):
        resp = jsonify(listaOrdenadores)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp


@app.route("/listaordenadores/<aula>/ordenadores", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getOrdenadoresAula(aula): #aquí declaramos una función que se llamará cuando se realice una request a esa url
    print(id)
    listaOrdenadoresAula = conex.buscarOrdenadoresPorAula(aula)
    print(jsonify(listaOrdenadoresAula))
    if (len(listaOrdenadoresAula) != 0):
        resp = jsonify(listaOrdenadoresAula)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp


#------------------------------------------------------------------------------

@app.route("/registrarprofesor", methods=["POST"])
def addProfesor():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['id'])
    print(data['nombre'])
    print(data['permisos'])
    if (conex.insertarProfesor(data['id'],data['nombre'],data['clave'],data['permisos'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Clave duplicada.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp


@app.route("/registraraula", methods=["POST"])
def addAula():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['id_aula'])
    print(data['nombre_aula'])
    if (conex.insertarAula(data['id_aula'],data['nombre_aula'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Clave duplicada.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp


@app.route("/registrarordenador", methods=["POST"])
def addOrdenador():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['id_ordenador'])
    print(data['aula'])
    print(data['modelo'])
    print(data['almacenamiento'])
    print(data['ram'])
    if (conex.insertarOrdenador(data['id_ordenador'],data['aula'],data['modelo'],data['almacenamiento'],data['ram'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Clave duplicada.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp

#------------------------------------------------------------------------------    

@app.route("/borrarprofesor/<id>", methods=["DELETE"])
def delProfesor(id):

    if (conex.borrarProfesor(id)>0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'id ' + str(id) + ' no encontrado.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(respuesta)
    print(resp)
    return resp


@app.route("/borraraula/<id_aula>", methods=["DELETE"])
def delAula(id_aula):

    if (conex.borrarAula(id_aula)>0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'id_aula ' + str(id_aula) + ' no encontrado.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(respuesta)
    print(resp)
    return resp


@app.route("/borrarordenador/<id_ordenador>", methods=["DELETE"])
def delOrdenador(id_ordenador):

    if (conex.borrarOrdenador(id_ordenador)>0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'id_ordenador ' + str(id_ordenador) + ' no encontrado.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(respuesta)
    print(resp)
    return resp

#------------------------------------------------------------------------------
@app.route("/modificarprofesor", methods=["PUT"])
def modProfesor():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['id'])
    print(data['nombre'])
    print(data['permisos'])
    if (conex.modificarProfesor(data['id'],data['nombre'],data['permisos']) > 0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error al modificar.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    
    print(resp)
    return resp


@app.route("/modificaraula", methods=["PUT"])
def modAula():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['id_aula'])
    print(data['nombre_aula'])
    if (conex.modificarAula(data['id_aula'],data['nombre_aula']) > 0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error al modificar.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp


@app.route("/modificarordenador", methods=["PUT"])
def modOrdenador():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['id_ordenador'])
    print(data['aula'])
    print(data['modelo'])
    print(data['almacenamiento'])
    print(data['ram'])
    if (conex.modificarOrdenador(data['id_ordenador'],data['aula'],data['modelo'],data['almacenamiento'],data['ram']) > 0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error al modificar.'}
        resp = jsonify(respuesta)
        resp.status_code = 400

    print(resp)
    return resp

# Para montarlo en http normaleras.
if __name__ == '__main__':
    #app.run(debug=True)
    app.run(debug=True, host= '0.0.0.0') #Esto sería para poder usar el móvil. No arrancaría el servicio en localhost sino en esa ip. También 0.0.0.0

# Esto es para montarlo en https.
# if __name__ == "__main__":
#     app.run(ssl_context='adhoc')
