#
# this example demonstrates the use of different .tex inlude files
#

mkdir texdoclet_output

rm texdoclet_output/TeXDoclet.aux

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
	-sourcepath ../03_Implementierung/GO/app/src/main/java \
	-subpackages edu \
 	-sectionlevel section

cd texdoclet_output
pdflatex Entwurf.tex
pdflatex Entwurf.tex
cp Entwurf.pdf ../
