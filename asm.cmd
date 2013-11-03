set name=Test
set ASM_HOME="%cd%\build\forge\mcp\jars\libraries\org\ow2\asm\asm-debug-all\4.1"
set ASM_JAR=asm-debug-all-4.1.jar
set cp=%ASM_HOME%\%ASM_JAR%
set c=org.objectweb.asm.util.ASMifier
java -cp %cp% %c% %1 1> %name%.java 2>&1