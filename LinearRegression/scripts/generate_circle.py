for x in range(-10,10):
   for y in range(-10,10):
      c = 0
      if (x*x+y*y < 3.1*3.1):
         c = 1
      print "%d,%d,%d" % (x,y,c)
