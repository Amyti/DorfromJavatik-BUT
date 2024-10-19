### VARIABLES ###
JC = javac
JCFLAGS = -d $(build) -classpath $(build) -encoding UTF-8 -implicit:none

JVM = java
JVMFLAGS = -classpath $(build)

src = src ## c pour le repertoire src
build = build ## c pour le repertoire build 
main = fr/iutfbleau/projet ## c pour le repertoire projet

### ESSENTIAL RULES ###

$(build)/$(main)/Main.class: $(src)/$(main)/Main.java $(build)/$(main)/vue/TileView.class $(build)/$(main)/model/Tile.class
	$(JC) $(JCFLAGS) $(src)/$(main)/Main.java

$(build)/$(main)/vue/TileView.class: $(src)/$(main)/vue/TileView.java
	$(JC) $(JCFLAGS) $(src)/$(main)/vue/TileView.java


$(build)/$(main)/model/Tile.class: $(src)/$(main)/model/Tile.java
	$(JC) $(JCFLAGS) $(src)/$(main)/model/Tile.java

### OPTIONAL RULES ###

run: $(build)/$(main)/Main.class
	$(JVM) $(JVMFLAGS) fr.iutfbleau.projet.Main

## clean:
## -rm -rf $(build)/* pas pour l'instant

### PHONY TARGETS ###
.PHONY: run ##clean

