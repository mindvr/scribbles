```shell
# build
javac -d . com/example/Jep418.java com/example/ExampleInetAddressResolverProvider.java 
jar cvf Jep418.jar com/example/*.class
jar uvf Jep418.jar META-INF/

#run
java -cp Jep418.jar com.example.Jep418
```