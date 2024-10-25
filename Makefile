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


JAVAC_FLAGS = -d $(BUILD_DIR) -classpath $(BUILD_DIR)
MAIN_FLAGS = -classpath $(BUILD_DIR)


SRC_FILES = $(wildcard $(SRC_DIR)/$(PACKAGE_DIR)/**/*.java)


all: clean compile


$(BUILD_DIR):
	@mkdir -p $(BUILD_DIR)


clean:
	$(RM) $(BUILD_DIR)/*



compile-JlabelPersos : $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/vue/JlabelPerso.java


compile-hexagonutils: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/vue/Tuiles/HexagonUtils.java


compile-models: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/model/*.java


compile-tuiles: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/vue/Tuiles/*.java $(SRC_DIR)/$(PACKAGE_DIR)/controller/TileController.java


compile-jeu: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/vue/Jeu.java


compile-main: $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -classpath $(BUILD_DIR) $(SRC_DIR)/$(PACKAGE_DIR)/Main.java


compile: $(BUILD_DIR) compile-JlabelPersos compile-hexagonutils compile-models compile-tuiles compile-jeu compile-main


run: compile
	$(JAVA) $(MAIN_FLAGS) $(MAIN_CLASS)

.PHONY: all clean compile-JlabelPersos compile-hexagonutils compile-models compile-tuiles compile-jeu compile-main run

