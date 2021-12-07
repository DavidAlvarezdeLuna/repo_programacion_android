import json

class ProfesorEncoder(json.JSONEncoder):

    def default(self, obj):
        return obj.__dict__


class Profesor(object):
    def __init__(self, id, nombre, clave, permisos) -> None :
        self.id =id
        self.nombre = nombre
        self.clave = clave
        self.permisos = permisos

    # def toJSON(self):
    #     #return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)
    #     return json.dump(self)
    #     #return json.dumps(self, skipkeys=False, ensure_ascii=True, check_circular=True, allow_nan=True, cls=None, indent=None, separators=None, default=None, sort_keys=False, **kw)

    def __str__(self) :
        return json.dumps(self)

#http://daniel.blogmatico.com/convertir-un-objeto-python-a-un-objeto-json/