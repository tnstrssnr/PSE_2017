mkdir texdoclet_output

rm texdoclet_output/TeXDoclet.aux

javadoc -docletpath TeXDoclet.jar \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-tree \
	-hyperref \
	-imagespath ".." \
	-output texdoclet_output/TeXDoclet.tex \
	-sourcepath ../03_Implementierung/GO-Tomcat/src/main/java \
	-subpackages edu/kit/pse17 \
 	-shortinherited \
 	-classdeclrframe trBL \
 	-methoddeclrframe single

cd texdoclet_output
pdflatex TeXDoclet.tex
pdflatex TeXDoclet.tex
mkdir ../javadoc
cp TeXDoclet.pdf ../javadoc
