# Nom du projet
PROJECT_NAME = Dorfromantik

# Dossiers
SRC_DIR = src
BUILD_DIR = build
PACKAGE_DIR = fr/iutfbleau/projet
MAIN_CLASS = fr.iutfbleau.projet.Main

# Commandes
JAVAC = javac
JAVA = java
JAR = jar
RM = rm -rf

# Options de compilation
JAVAC_FLAGS = -d $(BUILD_DIR) -classpath $(BUILD_DIR)
MAIN_FLAGS = -classpath $(BUILD_DIR)

# Liste des sources
SRC_FILES = $(wildcard $(SRC_DIR)/$(PACKAGE_DIR)/**/*.java)

# Cibles par défaut
all: clean compile

# Création du dossier build
$(BUILD_DIR):
	@mkdir -p $(BUILD_DIR)

# Nettoyage du dossier build avant la compilation
clean:
	$(RM) $(BUILD_DIR)/*

# Compilation des fichiers HexagonUtils
compile-hexagonutils: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/vue/Tuiles/HexagonUtils.java

# Compilation des modèles
compile-models: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/model/*.java

# Compilation de TileController et des fichiers dans Tuiles
compile-tuiles: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/vue/Tuiles/*.java $(SRC_DIR)/$(PACKAGE_DIR)/controller/TileController.java

# Compilation de Jeu.java
compile-jeu: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/vue/Jeu.java

# Compilation de Main.java
compile-main: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/Main.java

# Compilation complète
compile: $(BUILD_DIR) compile-hexagonutils compile-models compile-tuiles compile-jeu compile-main

# Exécution du projet
run: compile
	$(JAVA) $(MAIN_FLAGS) $(MAIN_CLASS)

.PHONY: all clean compile-hexagonutils compile-models compile-tuiles compile-jeu compile-main run

