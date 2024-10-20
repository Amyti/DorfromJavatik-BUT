# Variables
SRC_DIR = ../src/fr/iutfbleau/projet
BUILD_DIR = build
CLASSPATH = $(BUILD_DIR)

# Fichiers sources
CONTROLLER_SRC = $(SRC_DIR)/controller/TileController.java
MAIN_SRC = $(SRC_DIR)/Main.java
MODEL_SRC = $(SRC_DIR)/model/Tile.java
VUE_SRC = $(SRC_DIR)/vue/Tuiles/*.java

# Cible par défaut
all: compile

# Compilation des fichiers
compile: $(BUILD_DIR)/Main.class

$(BUILD_DIR)/Main.class: $(CONTROLLER_SRC) $(MAIN_SRC) $(MODEL_SRC) $(VUE_SRC)
	javac -d $(BUILD_DIR) -classpath $(CLASSPATH) $(CONTROLLER_SRC)
	javac -d $(BUILD_DIR) -classpath $(CLASSPATH) $(MAIN_SRC)

# Lancer l'application
run:
	java -classpath $(CLASSPATH) fr.iutfbleau.projet.Main
