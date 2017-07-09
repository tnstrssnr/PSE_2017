#
# Skript, um die Javadoc-Kommentare aus dem Source-Code in die .tex-Datei einzufügen.
# TeXDoclet.jar muss heruntergeladen werden und der Pfad anschließend unten aktualisert werden.
#

mkdir texdoclet_output

javadoc -docletpath TeXDoclet.jar \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-noindex \
	-tree \
	-hyperref \
	-sourcepath ../03_Implementierung/GO-App/app/src/main/java \
	-subpackages /edu \
 	-sectionlevel section
