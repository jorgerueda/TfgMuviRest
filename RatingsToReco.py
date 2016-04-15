
try:
    file_csv = open('dataset.dat', 'w')
    
    for line in open('ratings.dat', 'r'):
        rating = line.rstrip()
        tam = len(rating)-12
        small_rating = rating[:tam]
        rating_change = small_rating.replace('::', ',')
        file_csv.write(rating_change + '.0\n')
    
    file_csv.close()
    
except:
    print "error inesperado"
    exit()