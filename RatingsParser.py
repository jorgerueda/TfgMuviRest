'''

    IMPLICIT SCHEME
    
    +----------------------------+
    | key               | types  |
    | ----------------- | ------ |
    | origen            | String |
    | id_usuario        | Number |
    | id_pelicula       | Number |
    | valoracion        | Number |
    | timestamp         | Number | 
    +----------------------------+

'''

from pymongo import MongoClient

client = MongoClient('localhost', 27017)

try:
    
    manf = open('ratings.dat', 'r')
    
    texto = manf.read()
    lineas = texto.split()
    for linea in lineas:
        palabras = linea.split("::")
        
        valoracion = {
            "origen": "dataset",
            "id_usuario": palabras[0],
            "id_pelicula": palabras[1],
            "valoracion": palabras[2],
            "timestamp": palabras[3]}
        
        try:
            valoracion_id = client.muviapp.valoraciones.insert_one(valoracion).inserted_id
        except:
            print "Error al insertar"
        
    manf.close()
    
except:
    print "No se pudo abrir el fichero ratings.dat"
    exit()
    