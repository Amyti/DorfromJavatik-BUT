### VARIABLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none -cp $(BUILD_DIR) -d $(BUILD_DIR)

JVM = java
JVMFLAGS = -cp "$(BUILD_DIR):res/mariadb.jar"
BUILD_DIR = build
SRC_DIR = src
MAIN_CLASS = fr.iutfbleau.projet.Main

### RÈGLES ESSENTIELLES ###

# Cible par défaut
all: $(BUILD_DIR)/$(MAIN_CLASS).class

# Compilation du fichier principal
$(BUILD_DIR)/$(MAIN_CLASS).class: $(SRC_DIR)/fr/iutfbleau/projet/Main.java $(BUILD_DIR)/fr/iutfbleau/projet/vue/MenuAvecSeriesBD.class
	$(JC) $(JCFLAGS) $(SRC_DIR)/fr/iutfbleau/projet/Main.java

# Compilation des classes du modèle
$(BUILD_DIR)/fr/iutfbleau/projet/model/Tile.class: $(SRC_DIR)/fr/iutfbleau/projet/model/Tile.java
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/model/Serie.class: $(SRC_DIR)/fr/iutfbleau/projet/model/Serie.java
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/model/SerieBD.class: $(SRC_DIR)/fr/iutfbleau/projet/model/SerieBD.java $(BUILD_DIR)/fr/iutfbleau/projet/model/Serie.class
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/model/ScoreManager.class: $(SRC_DIR)/fr/iutfbleau/projet/model/ScoreManager.java
	$(JC) $(JCFLAGS) $<

# Compilation des classes de la vue
$(BUILD_DIR)/fr/iutfbleau/projet/vue/MyButton.class: $(SRC_DIR)/fr/iutfbleau/projet/vue/MyButton.java
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/vue/BackgroundPanel.class: $(SRC_DIR)/fr/iutfbleau/projet/vue/BackgroundPanel.java
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/vue/ScoreTable.class: $(SRC_DIR)/fr/iutfbleau/projet/vue/ScoreTable.java $(BUILD_DIR)/fr/iutfbleau/projet/model/ScoreManager.class
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/vue/Tuiles/HexagonUtils.class: $(SRC_DIR)/fr/iutfbleau/projet/vue/Tuiles/HexagonUtils.java
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/vue/Tuiles/DessinerGrilleHexagonal.class: $(SRC_DIR)/fr/iutfbleau/projet/vue/Tuiles/DessinerGrilleHexagonal.java $(BUILD_DIR)/fr/iutfbleau/projet/model/Tile.class
	$(JC) $(JCFLAGS) $<

# Compilation des contrôleurs et classes principales de vue
$(BUILD_DIR)/fr/iutfbleau/projet/vue/TileView.class: $(SRC_DIR)/fr/iutfbleau/projet/vue/Tuiles/TileView.java $(BUILD_DIR)/fr/iutfbleau/projet/model/Tile.class $(BUILD_DIR)/fr/iutfbleau/projet/vue/Tuiles/HexagonUtils.class $(BUILD_DIR)/fr/iutfbleau/projet/vue/Tuiles/DessinerGrilleHexagonal.class
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/controller/TileController.class: $(SRC_DIR)/fr/iutfbleau/projet/controller/TileController.java $(BUILD_DIR)/fr/iutfbleau/projet/vue/TileView.class
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/vue/Jeu.class: $(SRC_DIR)/fr/iutfbleau/projet/vue/Jeu.java $(BUILD_DIR)/fr/iutfbleau/projet/vue/TileView.class $(BUILD_DIR)/fr/iutfbleau/projet/model/Tile.class
	$(JC) $(JCFLAGS) $<

$(BUILD_DIR)/fr/iutfbleau/projet/vue/MenuAvecSeriesBD.class: $(SRC_DIR)/fr/iutfbleau/projet/vue/MenuAvecSeriesBD.java $(BUILD_DIR)/fr/iutfbleau/projet/vue/ScoreTable.class $(BUILD_DIR)/fr/iutfbleau/projet/vue/MyButton.class $(BUILD_DIR)/fr/iutfbleau/projet/vue/BackgroundPanel.class $(BUILD_DIR)/fr/iutfbleau/projet/model/Serie.class $(BUILD_DIR)/fr/iutfbleau/projet/model/SerieBD.class $(BUILD_DIR)/fr/iutfbleau/projet/vue/Jeu.class
	$(JC) $(JCFLAGS) $<

### RÈGLES D'EXÉCUTION ET DE NETTOYAGE ###

# Exécution du programme principal
run: all
	$(JVM) $(JVMFLAGS) $(MAIN_CLASS)

# Nettoyage des fichiers compilés
clean:
	-rm -rf $(BUILD_DIR)/**/*.class

### BUTS FACTICES ###
.PHONY: all run clean
