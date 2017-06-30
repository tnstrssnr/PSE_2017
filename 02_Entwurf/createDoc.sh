#
# Skript, um die Javadoc-Kommentare aus dem Source-Code in die .tex-Datei einzufügen.
# TeXDoclet.jar muss heruntergeladen werden und der Pfad anschließend unten aktualisert werden.
#

mkdir texdoclet_output

rm texdoclet_output/Entwurf.aux

javadoc -docletpath TeXDoclet.jar \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-noindex \
	-tree \
	-hyperref \
	-texsetup texdoclet_include/setup.tex \
	-texintro texdoclet_include/intro.tex \
	-texfinish texdoclet_include/finish.tex \
	-texinit texdoclet_include/preamble.tex \
	-output texdoclet_output/Entwurf.tex \
	-sourcepath ../03_Implementierung/GO-Tomcat/src/main/java:../03_Implementierung/GO-App/app/src/main/java \
	-subpackages edu \
 	-sectionlevel section

cd texdoclet_output
pdflatex Entwurf.tex
pdflatex Entwurf.tex
cp Entwurf.pdf ../
