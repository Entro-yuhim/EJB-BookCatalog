import urllib2

response = urllib2.urlopen('https://openlibrary.org/api/books?bibkeys=ISBN:0385472579,LCCN:62019420&format=json')
print(response.read())