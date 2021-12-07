import json

class OrdenadorEncoder(json.JSONEncoder):

    def default(self, obj):
        return obj.__dict__


class Ordenador(object):
    def __init__(self, id_ordenador, aula, modelo, almacenamiento, ram) -> None :
        self.id_ordenador =id_ordenador
        self.aula = aula
        self.modelo = modelo
        self.almacenamiento = almacenamiento
        self.ram = ram

    # def toJSON(self):
    #     #return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)
    #     return json.dump(self)
    #     #return json.dumps(self, skipkeys=False, ensure_ascii=True, check_circular=True, allow_nan=True, cls=None, indent=None, separators=None, default=None, sort_keys=False, **kw)

    def __str__(self) :
        return json.dumps(self)

#http://daniel.blogmatico.com/convertir-un-objeto-python-a-un-objeto-json/