echo "Compiling Stuff..."
javac -classpath "./;./lib/minim.jar;./lib/core.jar" -d bin src/ie/tudublin/*.java src/example/*.java src/c21394693/*.java src/c21447352/*.java


echo "Running Java!"
java -classpath "./bin;./lib/minim.jar;./lib/core.jar;./lib/jsminim.jar;./lib/mp3spi1.9.5.jar;./lib/tritonus_share.jar;./lib/tritonus_aos.jar;./lib/jl1.0.1.jar;./lib/sqlite-jdbc-3.23.1.jar;lib/jogl-all.jar;lib/gluegen-rt.jar;lib/gluegen-rt-natives-windows-amd64.jar;lib/gluegen-rt-natives-windows-i586.jar;lib/jogl-all-natives-windows-amd64.jar;lib/jogl-all-natives-windows-i586.jarlib/jogl-all.jar;lib/gluegen-rt.jar;lib/gluegen-rt-natives-windows-amd64.jar;lib/gluegen-rt-natives-windows-i586.jar;lib/jogl-all-natives-windows-amd64.jar;lib/jogl-all-natives-windows-i586.jar" c21447352.FireWaveform